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
                    } else if (parts[0].startsWith("&")) {
                        inputModule = new ConjunctionModule(parts[1]);
                        inputModuleName = parts[0].substring(1);
                    } else {
                        inputModule = new UntypedModule(parts[1]);
                        inputModuleName = parts[0];
                    }
                    modules.put(inputModuleName, inputModule);
                    inputModule.getDestinationModules()
                            .forEach(destinationModule -> inputModules.computeIfAbsent(destinationModule, k -> new ArrayList<>()).add(inputModuleName));
                });
        modules.forEach((k, v) -> v.setInputModules(inputModules.get(k)));

        Map<Pulse, Long> pulseCounts = new EnumMap<>(Pulse.class);
        pulseCounts.put(Pulse.LOW, 1_000L);
        Deque<Signal> waitingForProcessing = new ArrayDeque<>();
        Set<String> alreadyInQueue = new HashSet<>();
        for (long i = 0; i < 1_000L; ++i) {
            waitingForProcessing.addFirst(new Signal("button", "broadcaster", Pulse.LOW));
            alreadyInQueue.add("broadcaster");
            while (!waitingForProcessing.isEmpty()) {
                var signal = waitingForProcessing.pollFirst();
                alreadyInQueue.remove(signal.getToModule());
                var module = modules.get(signal.getToModule());
                var output = module.getOutput(signal.getPulse(), signal.getFromModule());
                output.ifPresent(pulse -> {
                    for (var destinationModuleName : module.getDestinationModules()) {
                        pulseCounts.merge(pulse, 1L, Long::sum);
                        //System.out.println(signal.getToModule() + " -" + pulse + "-> " + destinationModuleName);
                        var destinationModule = modules.get(destinationModuleName);
                        if (destinationModule != null) {
                            if (alreadyInQueue.add(destinationModuleName)) {
                                waitingForProcessing.addLast(new Signal(signal.getToModule(), destinationModuleName, pulse));
                            }
                        }
                    }
                });
            }
            //System.out.println();
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

    private static class Signal {
        private final String fromModule;
        private final String toModule;
        private final Pulse pulse;

        Signal(String fromModule, String toModule, Pulse pulse) {
            this.fromModule = fromModule;
            this.toModule = toModule;
            this.pulse = pulse;
        }

        String getToModule() {
            return toModule;
        }

        String getFromModule() {
            return fromModule;
        }

        Pulse getPulse() {
            return pulse;
        }

        @Override
        public String toString() {
            return fromModule + " -" + pulse + "-> " + toModule;
        }
    }

    private static abstract class Module {
        private final List<String> destinationModules;

        Module(String commaSeparatedDestinationModules) {
            this.destinationModules = Arrays.asList(commaSeparatedDestinationModules.split(", "));
        }

        List<String> getDestinationModules() {
            return destinationModules;
        }

        void setInputModules(Collection<String> inputModules) {
        }

        abstract Optional<Pulse> getOutput(Pulse lastPulse, String fromModule);

        @Override
        public String toString() {
            return "Module{" +
                    "destinationModules=" + destinationModules +
                    '}';
        }
    }

    private static class UntypedModule extends Module {
        UntypedModule(String commaSeparatedDestinationModules) {
            super(commaSeparatedDestinationModules);
        }

        @Override
        Optional<Pulse> getOutput(Pulse lastPulse, String fromModule) {
            return Optional.empty();
        }
    }

    private static class FlipFlopModule extends Module {
        private boolean on = false;

        FlipFlopModule(String commaSeparatedDestinationModules) {
            super(commaSeparatedDestinationModules);
        }

        @Override
        Optional<Pulse> getOutput(Pulse lastPulse, String fromModule) {
            if (lastPulse == Pulse.LOW) {
                on = !on;
                return on ? Optional.of(Pulse.HIGH) : Optional.of(Pulse.LOW);
            } else {
                return Optional.empty();
            }
        }

        @Override
        public String toString() {
            return "FlipFlopModule{" +
                    "on=" + on +
                    "} " + super.toString();
        }
    }

    private static class ConjunctionModule extends Module {
        private Map<String, Pulse> inputModules;

        ConjunctionModule(String commaSeparatedDestinationModules) {
            super(commaSeparatedDestinationModules);
        }

        @Override
        void setInputModules(Collection<String> inputModules) {
            this.inputModules = inputModules.stream()
                    .collect(Collectors.toMap(Function.identity(), v -> Pulse.LOW));
        }

        @Override
        Optional<Pulse> getOutput(Pulse lastPulse, String fromModule) {
            if (inputModules.put(fromModule, lastPulse) == null) {
                throw new IllegalArgumentException("Pulse " + lastPulse + " from not an input " + fromModule + " to " + this);
            }
            return inputModules.values().stream()
                    .allMatch(pulse -> pulse == Pulse.HIGH) ? Optional.of(Pulse.LOW) : Optional.of(Pulse.HIGH);
        }

        @Override
        public String toString() {
            return "ConjunctionModule{" +
                    "inputModules=" + inputModules +
                    "} " + super.toString();
        }
    }
}
