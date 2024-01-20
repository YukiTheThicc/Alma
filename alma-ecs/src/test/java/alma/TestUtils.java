package alma;

import java.lang.System;

public class TestUtils {

    public static void printTestInfo(String test, Object expected, Object actual) {
        String toPrint = "\n/_________________________________________\\" +
                "\n STARTING TEST: " + test +
                "\n\\_________________________________________/";
        toPrint += "\n\t -> Expected: \t\t" + expected;
        toPrint += "\n\t -> Actual: \t\t" + actual + "\n";
        System.out.println(toPrint);
    }

    public static void printTestHeader(String test) {
        String toPrint =
                "\n _________________________________________ " +
                "\n/                                         \\" +
                "\n STARTING TEST: " + test +
                "\n\\_________________________________________/";
        System.out.println(toPrint);
    }

    public static void printTestIteration(int iteration, Object expected, Object actual) {
        String toPrint = "Iteration [" + iteration + "]" +
                "\n\t -> Expected: \t\t" + expected +
                "\n\t -> Actual: \t\t" + actual + "\n";
        System.out.println(toPrint);
    }
}
