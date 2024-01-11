package src.advent2023.day20;

import src.PuzzleSolver;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    static long lcm(LongStream oneNodeDistances) {
        return oneNodeDistances.reduce(1L, (x, y) -> (x * y) / gcd(x, y));
    }

    static long gcd(long a, long b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
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
        for (long i = 0; i < 1_000L; ++i) {
            waitingForProcessing.addFirst(new Signal("button", "broadcaster", Pulse.LOW));
            while (!waitingForProcessing.isEmpty()) {
                var signal = waitingForProcessing.pollFirst();
                var module = modules.get(signal.getToModule());
                var output = module.getOutput(signal.getPulse(), signal.getFromModule());
                output.ifPresent(pulse -> {
                    for (var destinationModuleName : module.getDestinationModules()) {
                        pulseCounts.merge(pulse, 1L, Long::sum);
                        var destinationModule = modules.get(destinationModuleName);
                        if (destinationModule != null) {
                            waitingForProcessing.addLast(new Signal(signal.getToModule(), destinationModuleName, pulse));
                        }
                    }
                });
            }
        }
        return (pulseCounts.get(Pulse.LOW) * pulseCounts.get(Pulse.HIGH)) + "";
    }

    @Override
    public List<String> getExampleInput2() {
        return List.of("broadcaster -> rx");
    }

    @Override
    public List<String> getExampleOutput2() {
        return List.of("1");
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
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

        String rxInput = inputModules.get("rx").get(0);
        Map<String, Long> rxInputs = new HashMap<>();

        long buttonPresses = 0;
        Deque<Signal> waitingForProcessing = new ArrayDeque<>();
        while (true) {
            ++buttonPresses;
            waitingForProcessing.addFirst(new Signal("button", "broadcaster", Pulse.LOW));
            while (!waitingForProcessing.isEmpty()) {
                var signal = waitingForProcessing.pollFirst();
                var module = modules.get(signal.getToModule());
                var output = module.getOutput(signal.getPulse(), signal.getFromModule());
                if (output.isPresent()) {
                    Pulse pulse = output.get();
                    for (var destinationModuleName : module.getDestinationModules()) {
                        if ("rx".equals(destinationModuleName) && pulse == Pulse.LOW) {
                            return buttonPresses + "";
                        }
                        if (pulse == Pulse.HIGH && rxInput.equals(destinationModuleName)) {
                            if (rxInputs.putIfAbsent(signal.getFromModule(), buttonPresses) != null) {
                                return "" + lcm(rxInputs.values().parallelStream().mapToLong(v -> v));
                            }
                        }
                        var destinationModule = modules.get(destinationModuleName);
                        if (destinationModule != null) {
                            waitingForProcessing.addLast(new Signal(signal.getToModule(), destinationModuleName, pulse));
                        }
                    }
                }
            }
        }
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
            return Optional.of(lastPulse);
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
