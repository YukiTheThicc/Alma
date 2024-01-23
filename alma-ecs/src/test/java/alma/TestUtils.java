package alma;

import java.lang.System;

public class TestUtils {

    private static final int CHARACTERS_PER_LINE = 160;
    private static final String LIT_ITERATION = "ITER.";
    private static final String LIT_EXPECTED = "EXPECTED";
    private static final String LIT_ACTUAL = "ACTUAL";
    private static final int COL_SIZE_ITERATION = 12;
    private static int COL_SIZE_EXPECTED = 74;
    private static int COL_SIZE_ACTUAL = 74;
    private static final int SPACING_HEADER_ITERATION = (COL_SIZE_ITERATION - LIT_ITERATION.length()) / 2;
    private static int SPACING_HEADER_EXPECTED = (COL_SIZE_EXPECTED - LIT_EXPECTED.length()) / 2;
    private static int SPACING_HEADER_ACTUAL = (COL_SIZE_ACTUAL - LIT_ACTUAL.length()) / 2;

    public static void printTestHeader(String test) {
        COL_SIZE_EXPECTED = 74;
        COL_SIZE_ACTUAL = 74;
        SPACING_HEADER_EXPECTED = (COL_SIZE_EXPECTED - LIT_EXPECTED.length()) / 2;
        SPACING_HEADER_ACTUAL = (COL_SIZE_ACTUAL - LIT_ACTUAL.length()) / 2;
        String sb =
                "\n STARTING TEST: " + test + "\n" +
                        "_".repeat(CHARACTERS_PER_LINE) + "\n" +
                        "|" + " ".repeat(SPACING_HEADER_ITERATION - 1) + LIT_ITERATION + " ".repeat(SPACING_HEADER_ITERATION) +
                        "|" + " ".repeat(SPACING_HEADER_EXPECTED - 1) + LIT_EXPECTED + " ".repeat(SPACING_HEADER_EXPECTED) +
                        "|" + " ".repeat(SPACING_HEADER_ACTUAL - 1) + LIT_ACTUAL + " ".repeat(SPACING_HEADER_ACTUAL) + "|";
        System.out.println(sb);
    }

    public static void printTestHeader(String test, int[] headerLengths) {
        if (headerLengths != null && headerLengths.length == 2) {
            COL_SIZE_EXPECTED = headerLengths[0];
            COL_SIZE_ACTUAL = headerLengths[1];
            SPACING_HEADER_EXPECTED = (COL_SIZE_EXPECTED - LIT_EXPECTED.length()) / 2;
            SPACING_HEADER_ACTUAL = (COL_SIZE_ACTUAL - LIT_ACTUAL.length()) / 2;
        }
        String sb =
                "\n STARTING TEST: " + test + "\n" +
                        "_".repeat(CHARACTERS_PER_LINE) + "\n" +
                        "|" + " ".repeat(SPACING_HEADER_ITERATION - 1) + LIT_ITERATION + " ".repeat(SPACING_HEADER_ITERATION) +
                        "|" + " ".repeat(SPACING_HEADER_EXPECTED - 1) + LIT_EXPECTED + " ".repeat(SPACING_HEADER_EXPECTED) +
                        "|" + " ".repeat(SPACING_HEADER_ACTUAL - 1) + LIT_ACTUAL + " ".repeat(SPACING_HEADER_ACTUAL) + "|";
        System.out.println(sb);
    }

    public static void printTestIteration(int iteration, Object expected, Object actual) {
        int iterSpacing = COL_SIZE_ITERATION - String.valueOf(iteration).length() > 0 ? (COL_SIZE_ITERATION - String.valueOf(iteration).length()) / 2 : 2;
        int expectedSpacing = COL_SIZE_EXPECTED - String.valueOf(expected).length() > 0 ? (COL_SIZE_EXPECTED - String.valueOf(expected).length()) / 2 : 2;
        int actualSpacing = COL_SIZE_ACTUAL - String.valueOf(actual).length() > 0 ? (COL_SIZE_ACTUAL - String.valueOf(actual).length()) / 2 : 2;
        String sb = "|" + " ".repeat(iterSpacing - 1 - (String.valueOf(iteration).length() + 1) % 2) + iteration + " ".repeat(iterSpacing) +
                "|" + " ".repeat(expectedSpacing - (String.valueOf(expected).length() + 1) % 2) + expected + " ".repeat(expectedSpacing) +
                "|" + " ".repeat(actualSpacing - (String.valueOf(actual).length() + 1) % 2) + actual + " ".repeat(actualSpacing) + "|";
        System.out.println(sb);
    }
}
