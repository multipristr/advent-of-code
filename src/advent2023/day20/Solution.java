package src.advent2023.day20;

import src.PuzzleSolver;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
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
        Map<String, Module> modules = new HashMap<>();
        Map<String, List<String>> inputModules = new HashMap<>();
        lines.map(line -> line.split(" -> "))
                .forEach(parts -> {
                    Module inputModule;
                    String inputModuleName;
                    if (parts[0].startsWith("%")) {
                        inputModule = new FlipFlopModule(parts[1]);
                        inputModuleName = parts[0].substring(1);
                        modules.put(inputModuleName, inputModule);
                    } else if (parts[0].startsWith("&")) {
                        inputModule = new ConjunctionModule(parts[1]);
                        inputModuleName = parts[0].substring(1);
                        modules.put(inputModuleName, inputModule);
                    } else {
                        inputModule = new UntypedModule(parts[1]);
                        inputModuleName = parts[0];
                        modules.put(inputModuleName, inputModule);
                    }
                    inputModule.getDestinationModules()
                            .forEach(destinationModule -> inputModules.computeIfAbsent(destinationModule, k -> new ArrayList<>()).add(inputModuleName));
                });
        modules.forEach((k, v) -> v.setInputModules(inputModules.get(k)));

        Map<Pulses, Long> pulses = new EnumMap<>(Pulses.class);
        pulses.put(Pulses.LOW, 1_000L);
        Deque<String> open = new ArrayDeque<>();
        Set<String> closed = new HashSet<>();
        for (long i = 0; i < 4; ++i) {
            open.addFirst("broadcaster");
            closed.add("broadcaster");
            while (!open.isEmpty()) {
                var moduleName = open.poll();
                closed.remove(moduleName);
                var module = modules.get(moduleName);
                var output = module.getOutput();
                output.ifPresent(pulse -> {
                    for (var destinationModule : module.getDestinationModules()) {
                        pulses.merge(pulse, 1L, Long::sum);
                        System.out.println(moduleName + " " + pulse + " " + destinationModule);
                        var destination = modules.get(destinationModule);
                        if (destination != null) {
                            destination.setLastPulse(moduleName, pulse);
                            if (closed.add(destinationModule)) {
                                open.addLast(destinationModule);
                            }
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

        List<String> getDestinationModules() {
            return destinationModules;
        }

        void setLastPulse(String fromModule, Pulses pulse) {
            lastPulse = pulse;
        }

        void setInputModules(Collection<String> inputModules) {
        }

        abstract Optional<Pulses> getOutput();

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
        Optional<Pulses> getOutput() {
            return Optional.of(getLastPulse());
        }
    }

    private static class FlipFlopModule extends Module {
        private boolean on = false;

        FlipFlopModule(String commaSeparatedDestinationModules) {
            super(commaSeparatedDestinationModules);
        }

        @Override
        void setLastPulse(String fromModule, Pulses pulse) {
            if (pulse == Pulses.LOW) {
                on = !on;
            }
            super.setLastPulse(fromModule, pulse);
        }

        @Override
        Optional<Pulses> getOutput() {
            if (on) {
                return Optional.of(Pulses.HIGH);
            } else {
                return getLastPulse() == Pulses.LOW ? Optional.of(Pulses.LOW) : Optional.empty();
            }
        }
    }

    private static class ConjunctionModule extends Module {
        private Map<String, Pulses> inputModules;

        ConjunctionModule(String commaSeparatedDestinationModules) {
            super(commaSeparatedDestinationModules);
        }

        @Override
        void setInputModules(Collection<String> inputModules) {
            this.inputModules = inputModules
                    .stream()
                    .collect(Collectors.toMap(Function.identity(), v -> Pulses.LOW));
        }

        @Override
        void setLastPulse(String fromModule, Pulses pulse) {
            inputModules.put(fromModule, pulse);
            super.setLastPulse(fromModule, pulse);
        }

        @Override
        Optional<Pulses> getOutput() {
            return inputModules.values().stream()
                    .allMatch(pulse -> pulse == Pulses.HIGH) ? Optional.of(Pulses.LOW) : Optional.of(Pulses.HIGH);
        }
    }
}
