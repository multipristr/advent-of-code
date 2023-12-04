package src.advent2015.day4;

import src.PuzzleSolver;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public String getExampleInput1() {
        return "pqrstuv";
    }

    @Override
    public String getExampleOutput1() {
        return "1048970";
    }

    @Override
    public String getExampleOutput2() {
        return "5714438";
    }

    @Override
    public Stream<String> getInput1() {
        return Stream.of("ckczppom");
    }

    @Override
    public String solvePartOne(Stream<String> lines) {
        String secretKey = lines.collect(Collectors.joining());
        byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return IntStream.iterate(1, n -> n + 1)
                .parallel()
                .filter(i -> {
                    try {
                        MessageDigest md5 = MessageDigest.getInstance("MD5");
                        md5.update(secretKeyBytes);
                        String number = Integer.toString(i);
                        md5.update(number.getBytes(StandardCharsets.UTF_8));
                        String hash = String.format("%032x", new BigInteger(1, md5.digest()));
                        if (hash.startsWith("00000")) {
                            return true;
                        }
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                    return false;
                })
                .findAny().getAsInt() + "";
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        String secretKey = lines.collect(Collectors.joining());
        byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return IntStream.iterate(1, n -> n + 1)
                .parallel()
                .filter(i -> {
                    try {
                        MessageDigest md5 = MessageDigest.getInstance("MD5");
                        md5.update(secretKeyBytes);
                        String number = Integer.toString(i);
                        md5.update(number.getBytes(StandardCharsets.UTF_8));
                        String hash = String.format("%032x", new BigInteger(1, md5.digest()));
                        if (hash.startsWith("000000")) {
                            return true;
                        }
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                    return false;
                })
                .findAny().getAsInt() + "";
    }
}
