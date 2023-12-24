package src.advent2023.day20;

import src.PuzzleSolver;

import java.util.*;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("broadcaster -> a, b, c\n" +
                        "%a -> b\n" +
                        "%b -> c\n" +
                        "%c -> inv\n" +
                        "&inv -> a"
                ,
                "broadcaster -> a\n" +
                        "%a -> inv, con\n" +
                        "&inv -> b\n" +
                        "%b -> con\n" +
                        "&con -> output");
    }

    @Override
    public List<String> getExampleOutput1() {
        return List.of("32000000", "11687500");
    }

    @Override
    public List<String> getExampleOutput2() {
        return List.of();
    }

    @Override
    public String solvePartOne(Stream<String> lines) {
        Map<String, Module> modules = new LinkedHashMap<>();
        Map<String, List<Module>> inputModules = new HashMap<>();
        lines.map(line -> line.split(" -> "))
                .forEach(parts -> {
                    Module inputModule;
                    if (parts[0].startsWith("%")) {
                        inputModule = new FlipFlopModule(parts[1]);
                        modules.put(parts[0].substring(1), inputModule);
                    } else if (parts[0].startsWith("&")) {
                        inputModule = new ConjunctionModule(parts[1]);
                        modules.put(parts[0].substring(1), inputModule);
                    } else {
                        inputModule = new UntypedModule(parts[1]);
                        modules.put(parts[0], inputModule);
                    }
                    inputModule.getDestinationModules()
                            .stream()
                            .forEach(destinationModule -> inputModules.computeIfAbsent(destinationModule, k -> new ArrayList<>()).add(inputModule));
                });

        Map<Pulses, Long> pulses = new EnumMap<>(Pulses.class);
        pulses.put(Pulses.LOW, 1_000L);
        Deque<String> open = new ArrayDeque<>();
        Set<String> closed = new HashSet<>();
        for (long i = 0; i < 1000; ++i) {
            open.addFirst("broadcaster");
            closed.add("broadcaster");
            while (!open.isEmpty()) {
                var moduleName = open.poll();
                closed.remove(moduleName);
                //System.out.println(moduleName);
                var module = modules.get(moduleName);
                var inModules = inputModules.get(moduleName);
                var output = module.getOutput(inModules);
                output.ifPresent(pulse -> {
                    for (var destinationModule : module.getDestinationModules()) {
                        var destination = modules.get(destinationModule);
                        if (destination != null) {
                            destination.setLastPulse(pulse);
                        }
                        pulses.merge(pulse, 1L, Long::sum);
                        //System.out.println(moduleName + " " + pulse + " " + destinationModule);
                        if (closed.add(destinationModule)) {
                            open.addLast(destinationModule);
                        }
                    }
                });
            }
        }
        return (pulses.get(Pulses.LOW) * pulses.get(Pulses.HIGH)) + "";
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        return "";
    }

    private enum Pulses {
        LOW, HIGH;
    }

    private static abstract class Module {
        private final List<String> destinationModules;
        private Pulses lastPulse = Pulses.LOW;

        Module(String commaSeparatedDestinationModules) {
            this.destinationModules = Arrays.asList(commaSeparatedDestinationModules.split(", "));
        }

        Pulses getLastPulse() {
            return lastPulse;
        }

        void setLastPulse(Pulses pulse) {
            lastPulse = pulse;
        }

        List<String> getDestinationModules() {
            return destinationModules;
        }

        abstract Optional<Pulses> getOutput(List<Module> inputModules);

        @Override
        public String toString() {
            return destinationModules.toString();
        }
    }

    private static class UntypedModule extends Module {
        UntypedModule(String commaSeparatedDestinationModules) {
            super(commaSeparatedDestinationModules);
        }

        @Override
        Optional<Pulses> getOutput(List<Module> inputModules) {
            return Optional.of(getLastPulse());
        }
    }

    private static class FlipFlopModule extends Module {
        private boolean on = false;

        FlipFlopModule(String commaSeparatedDestinationModules) {
            super(commaSeparatedDestinationModules);
        }

        @Override
        void setLastPulse(Pulses pulse) {
            if (pulse == Pulses.LOW) {
                on = !on;
            }
            super.setLastPulse(pulse);
        }

        @Override
        Optional<Pulses> getOutput(List<Module> inputModules) {
            if (on) {
                return Optional.of(Pulses.HIGH);
            } else {
                return getLastPulse() == Pulses.LOW ? Optional.of(Pulses.LOW) : Optional.empty();
            }
        }
    }

    private static class ConjunctionModule extends Module {
        ConjunctionModule(String commaSeparatedDestinationModules) {
            super(commaSeparatedDestinationModules);
        }

        @Override
        Optional<Pulses> getOutput(List<Module> inputModules) {
            return inputModules.stream()
                    .map(Module::getLastPulse)
                    .allMatch(pulse -> pulse == Pulses.HIGH) ? Optional.of(Pulses.LOW) : Optional.of(Pulses.HIGH);
        }
    }
}
