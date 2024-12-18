package src.advent2024.day15;

import src.PuzzleSolver;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of(
                "########\n" +
                        "#..O.O.#\n" +
                        "##@.O..#\n" +
                        "#...O..#\n" +
                        "#.#.O..#\n" +
                        "#...O..#\n" +
                        "#......#\n" +
                        "########\n" +
                        "\n" +
                        "<^^>>>vv<v>>v<<",
                "##########\n" +
                        "#..O..O.O#\n" +
                        "#......O.#\n" +
                        "#.OO..O.O#\n" +
                        "#..O@..O.#\n" +
                        "#O#..O...#\n" +
                        "#O..O..O.#\n" +
                        "#.OO.O.OO#\n" +
                        "#....O...#\n" +
                        "##########\n" +
                        "\n" +
                        "<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^\n" +
                        "vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v\n" +
                        "><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<\n" +
                        "<<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^\n" +
                        "^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><\n" +
                        "^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^\n" +
                        ">^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^\n" +
                        "<><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>\n" +
                        "^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>\n" +
                        "v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^"
        );
    }

    @Override
    public List<Comparable<?>> getExampleOutput1() {
        return List.of(2028L, 10092L);
    }

    @Override
    public List<String> getExampleInput2() {
        return List.of(
                "##########\n" +
                        "#..O..O.O#\n" +
                        "#......O.#\n" +
                        "#.OO..O.O#\n" +
                        "#..O@..O.#\n" +
                        "#O#..O...#\n" +
                        "#O..O..O.#\n" +
                        "#.OO.O.OO#\n" +
                        "#....O...#\n" +
                        "##########\n" +
                        "\n" +
                        "<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^\n" +
                        "vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v\n" +
                        "><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<\n" +
                        "<<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^\n" +
                        "^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><\n" +
                        "^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^\n" +
                        ">^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^\n" +
                        "<><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>\n" +
                        "^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>\n" +
                        "v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^",
                "#######\n" +
                        "#.....#\n" +
                        "#.OO@.#\n" +
                        "#.....#\n" +
                        "#######\n" +
                        "\n" +
                        "<<",
                "#######\n" +
                        "#.....#\n" +
                        "#.O#..#\n" +
                        "#..O@.#\n" +
                        "#.....#\n" +
                        "#######\n" +
                        "\n" +
                        "<v<<^",
                "#######\n" +
                        "#.....#\n" +
                        "#.O.O@#\n" +
                        "#..O..#\n" +
                        "#..O..#\n" +
                        "#.....#\n" +
                        "#######\n" +
                        "\n" +
                        "<v<<>vv<^^",
                "#######\n" +
                        "#.....#\n" +
                        "#.#O..#\n" +
                        "#..O@.#\n" +
                        "#.....#\n" +
                        "#######\n" +
                        "\n" +
                        "<v<^",
                "######\n" +
                        "#....#\n" +
                        "#.O..#\n" +
                        "#.OO@#\n" +
                        "#.O..#\n" +
                        "#....#\n" +
                        "######\n" +
                        "\n" +
                        "<vv<<^",
                "#######\n" +
                        "#.....#\n" +
                        "#..O..#\n" +
                        "#@O.O.#\n" +
                        "#.#.O.#\n" +
                        "#.....#\n" +
                        "#######\n" +
                        "\n" +
                        ">>^^>>>>>>vv<^^<<v",
                "########\n" +
                        "#......#\n" +
                        "#..O...#\n" +
                        "#.O....#\n" +
                        "#..O...#\n" +
                        "#@O....#\n" +
                        "#......#\n" +
                        "########\n" +
                        "\n" +
                        ">>^<^>^^>>>>v<<^<<<vvvvv>>",
                "########\n" +
                        "#......#\n" +
                        "#..O...#\n" +
                        "#.O....#\n" +
                        "#..O...#\n" +
                        "#@O....#\n" +
                        "#......#\n" +
                        "########\n" +
                        "\n" +
                        ">>^<^>^^>>>>v<<^<<<vvvvv>>^",
                "#######\n" +
                        "#...#.#\n" +
                        "#.....#\n" +
                        "#.....#\n" +
                        "#.....#\n" +
                        "#.....#\n" +
                        "#.OOO@#\n" +
                        "#.OOO.#\n" +
                        "#..O..#\n" +
                        "#.....#\n" +
                        "#.....#\n" +
                        "#######\n" +
                        "\n" +
                        "v<vv<<^^^^^",
                "######\n" +
                        "#....#\n" +
                        "#..#.#\n" +
                        "#....#\n" +
                        "#.O..#\n" +
                        "#.OO@#\n" +
                        "#.O..#\n" +
                        "#....#\n" +
                        "######\n" +
                        "\n" +
                        "<vv<<^^^",
                "#######\n" +
                        "#.....#\n" +
                        "#.....#\n" +
                        "#.@O..#\n" +
                        "#..#O.#\n" +
                        "#...O.#\n" +
                        "#..O..#\n" +
                        "#.....#\n" +
                        "#######\n" +
                        "\n" +
                        ">><vvv>v>^^^",
                "########\n" +
                        "#......#\n" +
                        "#OO....#\n" +
                        "#.O....#\n" +
                        "#.O....#\n" +
                        "##O....#\n" +
                        "#O..O@.#\n" +
                        "#......#\n" +
                        "########\n" +
                        "\n" +
                        "<^^<<>^^^<v"
        );
    }

    @Override
    public List<Comparable<?>> getExampleOutput2() {
        return List.of(9021L, 406L, 509L, 822L, 511L, 816L, 1226L, 1420L, 1020L, 2339L, 1216L, 1430L, 2827L);
    }

    @Override
    public Comparable<?> solvePartOne(Stream<String> lines) {
        String[] inputParts = lines.collect(Collectors.joining("\n")).split("\\R{2}");
        char[][] warehouseMap = inputParts[0].lines().map(String::toCharArray).toArray(char[][]::new);
        int robotPosition = inputParts[0].indexOf("@");
        int robotY = robotPosition / (warehouseMap[0].length + 1);
        int robotX = robotPosition % (warehouseMap[0].length + 1);

        for (char move : inputParts[1].toCharArray()) {
            byte yDirection = 0;
            byte xDirection = 0;
            if (move == '^') {
                yDirection = -1;
            } else if (move == 'v') {
                yDirection = 1;
            } else if (move == '<') {
                xDirection = -1;
            } else if (move == '>') {
                xDirection = 1;
            } else {
                continue;
            }
            if (isSpaceInDirection(warehouseMap, robotY, robotX, yDirection, xDirection)) {
                move(warehouseMap, robotY, robotX, yDirection, xDirection);
                robotY += yDirection;
                robotX += xDirection;
            }
        }

        long sumOfGpsCoordinates = 0;
        for (int y = 0; y < warehouseMap.length; y++) {
            char[] row = warehouseMap[y];
            for (int x = 0; x < row.length; x++) {
                if (row[x] == 'O') {
                    sumOfGpsCoordinates += calculateGpsCoordinate(y, x);
                }
            }
        }
        return sumOfGpsCoordinates;
    }

    private boolean isSpaceInDirection(char[][] warehouseMap, int y, int x, byte yDirection, byte xDirection) {
        y += yDirection;
        x += xDirection;
        while (y < warehouseMap.length && y >= 0 && x >= 0 && x < warehouseMap[y].length) {
            char item = warehouseMap[y][x];
            if (item == '.') {
                return true;
            } else if (item == '#') {
                return false;
            }
            y += yDirection;
            x += xDirection;
        }
        return false;
    }

    private void move(char[][] warehouseMap, int y, int x, byte yDirection, byte xDirection) {
        char moving = '.';
        while (y < warehouseMap.length && y >= 0 && x >= 0 && x < warehouseMap[y].length) {
            char newMoving = warehouseMap[y][x];
            warehouseMap[y][x] = moving;
            if (newMoving == '.') {
                return;
            }
            moving = newMoving;

            y += yDirection;
            x += xDirection;
        }
    }

    private long calculateGpsCoordinate(int y, int x) {
        return 100L * y + x;
    }

    @Override
    public Comparable<?> solvePartTwo(Stream<String> lines) {
        String[] inputParts = lines.collect(Collectors.joining("\n")).split("\\R{2}");

        String changedInput = inputParts[0].replaceAll("#", "##")
                .replaceAll("O", "[]")
                .replaceAll("\\.", "..")
                .replaceAll("@", "@.");
        char[][] warehouseMap = changedInput.lines().map(String::toCharArray).toArray(char[][]::new);
        int robotPosition = changedInput.indexOf("@");
        int robotY = robotPosition / (warehouseMap[0].length + 1);
        int robotX = robotPosition % (warehouseMap[0].length + 1);

        for (char move : inputParts[1].toCharArray()) {
            byte yDirection = 0;
            byte xDirection = 0;
            if (move == '^') {
                yDirection = -1;
            } else if (move == 'v') {
                yDirection = 1;
            } else if (move == '<') {
                xDirection = -1;
            } else if (move == '>') {
                xDirection = 1;
            } else {
                continue;
            }
            if (yDirection == 0) {
                if (isSpaceInDirection(warehouseMap, robotY, robotX, yDirection, xDirection)) {
                    move(warehouseMap, robotY, robotX, yDirection, xDirection);
                    robotX += xDirection;
                }
            } else {
                if (isVerticalSpaceForWideBox(warehouseMap, robotY, robotX, yDirection)) {
                    moveWideBoxes(warehouseMap, robotY, robotX, yDirection);
                    robotY += yDirection;
                }
            }
        }

        long sumOfGpsCoordinates = 0;
        for (int y = 0; y < warehouseMap.length; y++) {
            char[] row = warehouseMap[y];
            for (int x = 0; x < row.length; x++) {
                if (row[x] == '[') {
                    sumOfGpsCoordinates += calculateGpsCoordinate(y, x);
                }
            }
        }
        return sumOfGpsCoordinates;
    }

    private boolean isVerticalSpaceForWideBox(char[][] warehouseMap, int y, int x, byte yDirection) {
        y += yDirection;
        while (y < warehouseMap.length && y >= 0) {
            char item = warehouseMap[y][x];
            if (item == '.') {
                return true;
            } else if (item == '#') {
                return false;
            } else if (item == '[') {
                if (!isVerticalSpaceForWideBox(warehouseMap, y, x + 1, yDirection)) {
                    return false;
                }
            } else if (item == ']') {
                if (!isVerticalSpaceForWideBox(warehouseMap, y, x - 1, yDirection)) {
                    return false;
                }
            }
            y += yDirection;
        }
        return false;
    }

    private void moveWideBoxes(char[][] warehouseMap, int y, int x, byte yDirection) {
        char moving = warehouseMap[y][x];
        warehouseMap[y][x] = '.';
        y += yDirection;
        while (y < warehouseMap.length && y >= 0) {
            char newMoving = warehouseMap[y][x];
            warehouseMap[y][x] = moving;
            if (newMoving == '.') {
                return;
            } else if (newMoving == '[') {
                moveWideBoxes(warehouseMap, y, x + 1, yDirection);
            } else if (newMoving == ']') {
                moveWideBoxes(warehouseMap, y, x - 1, yDirection);
            }
            moving = newMoving;

            y += yDirection;
        }
    }

}
