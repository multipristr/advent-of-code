package src.advent2024.day14;

import src.PuzzleSolver;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    private static final Pattern ROBOT_PATTERN = Pattern.compile(
            "p=(?<positionX>-?\\d+),(?<positionY>-?\\d+) v=(?<velocityX>-?\\d+),(?<velocityY>-?\\d+)"
    );

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("p=0,4 v=3,-3\n" +
                "p=6,3 v=-1,-3\n" +
                "p=10,3 v=-1,2\n" +
                "p=2,0 v=2,-1\n" +
                "p=0,0 v=1,3\n" +
                "p=3,0 v=-2,-2\n" +
                "p=7,6 v=-1,-3\n" +
                "p=3,0 v=-1,-2\n" +
                "p=9,3 v=2,3\n" +
                "p=7,3 v=-1,2\n" +
                "p=2,4 v=2,-3\n" +
                "p=9,5 v=-3,-3");
    }

    @Override
    public List<Comparable<?>> getExampleOutput1() {
        return List.of(21L);
    }

    @Override
    public List<Comparable<?>> getExampleOutput2() {
        return List.of(17L);
    }

    @Override
    public Comparable<?> solvePartOne(Stream<String> lines) {
        return calculateSafetyFactor(lines, 101, 103, 100);
    }

    private long calculateSafetyFactor(Stream<String> lines, long width, long height, long seconds) {
        AtomicLong quadrant1Robots = new AtomicLong();
        AtomicLong quadrant2Robots = new AtomicLong();
        AtomicLong quadrant3Robots = new AtomicLong();
        AtomicLong quadrant4Robots = new AtomicLong();
        long widthMiddle = width >> 1;
        long heightMiddle = height >> 1;

        lines.forEach(line -> {
            Matcher matcher = ROBOT_PATTERN.matcher(line);
            if (!matcher.find()) {
                throw new IllegalArgumentException(line + " not in format " + ROBOT_PATTERN);
            }
            long positionX = Long.parseLong(matcher.group("positionX"));
            long positionY = Long.parseLong(matcher.group("positionY"));
            long velocityX = Long.parseLong(matcher.group("velocityX"));
            long velocityY = Long.parseLong(matcher.group("velocityY"));

            long x = Math.floorMod(positionX + seconds * velocityX, width);
            long y = Math.floorMod(positionY + seconds * velocityY, height);
            if (x < widthMiddle) {
                if (y < heightMiddle) {
                    quadrant1Robots.incrementAndGet();
                } else if (y > heightMiddle) {
                    quadrant3Robots.incrementAndGet();
                }
            } else if (x > widthMiddle) {
                if (y < heightMiddle) {
                    quadrant2Robots.incrementAndGet();
                } else if (y > heightMiddle) {
                    quadrant4Robots.incrementAndGet();
                }
            }
        });

        return quadrant1Robots.get() * quadrant2Robots.get() * quadrant3Robots.get() * quadrant4Robots.get();
    }

    @Override
    public Comparable<?> solvePartTwo(Stream<String> lines) throws IOException {
        Robot[] robots = lines.map(line -> {
                    Matcher matcher = ROBOT_PATTERN.matcher(line);
                    if (!matcher.find()) {
                        throw new IllegalArgumentException(line + " not in format " + ROBOT_PATTERN);
                    }
                    return new Robot(matcher.group("positionX"), matcher.group("positionY"), matcher.group("velocityX"), matcher.group("velocityY"));
                })
                .toArray(Robot[]::new);
        long secondsToTree;

        /*Path arrangementOutput = Paths.get(".")
                .resolve(getClass().getPackageName().replaceAll("\\.", "/"))
                .resolve("robotsArrangement-" + System.nanoTime() + ".txt");
        try (BufferedWriter writer = Files.newBufferedWriter(arrangementOutput)) {
            secondsToTree = calculateSecondsToTree(robots, writer, 101, 103);
        }*/
        ByteArrayOutputStream arrangementOutput = new ByteArrayOutputStream();
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(arrangementOutput)))) {
            secondsToTree = calculateSecondsToTree(robots, writer, 101, 103);
        }
        System.out.print(arrangementOutput);

        return secondsToTree;
    }

    private long calculateSecondsToTree(Robot[] robots, BufferedWriter writer, int width, int height) throws IOException {
        int widthMiddle = width >> 1;
        int heightMiddle = height >> 1;

        int quadrant1Robots = 0;
        int quadrant2Robots = 0;
        int quadrant3Robots = 0;
        int quadrant4Robots = 0;
        for (Robot robot : robots) {
            robot.move();
            int y = robot.getPositionY(height);
            int x = robot.getPositionX(width);
            if (x < widthMiddle) {
                if (y < heightMiddle) {
                    ++quadrant1Robots;
                } else if (y > heightMiddle) {
                    ++quadrant3Robots;
                }
            } else if (x > widthMiddle) {
                if (y < heightMiddle) {
                    ++quadrant2Robots;
                } else if (y > heightMiddle) {
                    ++quadrant4Robots;
                }
            }
        }
        int lastSafetyValue = quadrant1Robots * quadrant2Robots * quadrant3Robots * quadrant4Robots;

        for (long second = 2; second < Long.MAX_VALUE; second++) {
            int[][] tiles = new int[height][width];
            quadrant1Robots = 0;
            quadrant2Robots = 0;
            quadrant3Robots = 0;
            quadrant4Robots = 0;
            for (Robot robot : robots) {
                robot.move();
                int y = robot.getPositionY(height);
                int x = robot.getPositionX(width);
                ++tiles[y][x];
                if (x < widthMiddle) {
                    if (y < heightMiddle) {
                        ++quadrant1Robots;
                    } else if (y > heightMiddle) {
                        ++quadrant3Robots;
                    }
                } else if (x > widthMiddle) {
                    if (y < heightMiddle) {
                        ++quadrant2Robots;
                    } else if (y > heightMiddle) {
                        ++quadrant4Robots;
                    }
                }
            }
            int safetyValue = quadrant1Robots * quadrant2Robots * quadrant3Robots * quadrant4Robots;
            if (safetyValue < lastSafetyValue * 0.5) {
                StringBuilder stringBuilder = new StringBuilder("After ")
                        .append(second)
                        .append(" seconds:")
                        .append(System.lineSeparator());
                for (var tile : tiles) {
                    for (var robotAmount : tile) {
                        stringBuilder.append(robotAmount > 0 ? robotAmount : ".");
                    }
                    stringBuilder.append(System.lineSeparator());
                }
                writer.write(stringBuilder.toString());
                writer.newLine();
                return second;
            }
            lastSafetyValue = safetyValue;
        }
        return -1;
    }

    private static final class Robot {
        private final int velocityX;
        private final int velocityY;
        private int positionY;
        private int positionX;

        private Robot(String positionX, String positionY, String velocityX, String velocityY) {
            this.positionX = Integer.parseInt(positionX);
            this.positionY = Integer.parseInt(positionY);
            this.velocityX = Integer.parseInt(velocityX);
            this.velocityY = Integer.parseInt(velocityY);
        }

        public int getPositionX(int width) {
            return Math.floorMod(positionX, width);
        }

        public int getPositionY(int height) {
            return Math.floorMod(positionY, height);
        }

        public void move() {
            move(1);
        }

        public void move(int seconds) {
            positionX += velocityX * seconds;
            positionY += velocityY * seconds;
        }
    }
}
