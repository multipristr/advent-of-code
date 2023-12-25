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

        Map<Pulse, Long> pulseCounts = new EnumMap<>(Pulse.class);
        pulseCounts.put(Pulse.LOW, 1_000L);
        Deque<String> waitingForProcessing = new ArrayDeque<>();
        Set<String> alreadyInQueue = new HashSet<>();
        for (long i = 0; i < 1_000L; ++i) {
            waitingForProcessing.addFirst("broadcaster");
            alreadyInQueue.add("broadcaster");
            while (!waitingForProcessing.isEmpty()) {
                var moduleName = waitingForProcessing.pollFirst();
                alreadyInQueue.remove(moduleName);
                var module = modules.get(moduleName);
                var output = module.getOutput();
                output.ifPresent(pulse -> {
                    for (var destinationModuleName : module.getDestinationModules()) {
                        pulseCounts.merge(pulse, 1L, Long::sum);
                        //System.out.println(moduleName + " " + pulse + " " + destinationModule);
                        var destination = modules.get(destinationModuleName);
                        if (destination != null) {
                            if (alreadyInQueue.add(destinationModuleName)) {
                                destination.setLastPulse(moduleName, pulse);
                                waitingForProcessing.addLast(destinationModuleName);
                            }
                        }
                    }
                });
            }
            //System.out.println("-");
        }
        return (pulseCounts.get(Pulse.LOW) * pulseCounts.get(Pulse.HIGH)) + "";
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        return "";
    }

    private enum Pulse {
        LOW, HIGH;
    }

    private static abstract class Module {
        private final List<String> destinationModules;
        private Pulse lastPulse = Pulse.LOW;

        Module(String commaSeparatedDestinationModules) {
            this.destinationModules = Arrays.asList(commaSeparatedDestinationModules.split(", "));
        }

        Pulse getLastPulse() {
            return lastPulse;
        }

        List<String> getDestinationModules() {
            return destinationModules;
        }

        void setLastPulse(String fromModule, Pulse pulse) {
            lastPulse = pulse;
        }

        void setInputModules(Collection<String> inputModules) {
        }

        abstract Optional<Pulse> getOutput();

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
        Optional<Pulse> getOutput() {
            return Optional.of(getLastPulse());
        }
    }

    private static class FlipFlopModule extends Module {
        private boolean on = false;

        FlipFlopModule(String commaSeparatedDestinationModules) {
            super(commaSeparatedDestinationModules);
        }

        @Override
        void setLastPulse(String fromModule, Pulse pulse) {
            if (pulse == Pulse.LOW) {
                on = !on;
            }
            super.setLastPulse(fromModule, pulse);
        }

        @Override
        Optional<Pulse> getOutput() {
            if (getLastPulse() == Pulse.LOW) {
                return on ? Optional.of(Pulse.HIGH) : Optional.of(Pulse.LOW);
            } else {
                return Optional.empty();
            }
        }
    }

    private static class ConjunctionModule extends Module {
        private Map<String, Pulse> inputModules;

        ConjunctionModule(String commaSeparatedDestinationModules) {
            super(commaSeparatedDestinationModules);
        }

        @Override
        void setInputModules(Collection<String> inputModules) {
            this.inputModules = inputModules
                    .stream()
                    .collect(Collectors.toMap(Function.identity(), v -> Pulse.LOW));
        }

        @Override
        void setLastPulse(String fromModule, Pulse pulse) {
            inputModules.put(fromModule, pulse);
            super.setLastPulse(fromModule, pulse);
        }

        @Override
        Optional<Pulse> getOutput() {
            return inputModules.values().stream()
                    .allMatch(pulse -> pulse == Pulse.HIGH) ? Optional.of(Pulse.LOW) : Optional.of(Pulse.HIGH);
        }
    }
}
