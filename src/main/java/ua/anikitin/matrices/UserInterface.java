package ua.anikitin.matrices;

import ua.anikitin.matrices.matrix.Matrix2DSquare;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;

public class UserInterface {
    public static final String UNEXPECTED_INPUT = "Unexpected input: %d. Expected value between %d and %d";
    public static final String INPUT_PROMPT = "Type matrix size (between %d and %d):";
    private static final int MIN_INPUT = 1;
    private static final int MAX_INPUT = 10_000;
    private static final String MATRIX_MSG = "Matrix %s:";
    private static final String OPERATION_STATUS = "Operation %s took %d ms";

    private final Scanner scanner;
    private final PrintStream out;

    public UserInterface(InputStream in, PrintStream out) {
        this.scanner = new Scanner(in);
        this.out = out;
    }

    public int readInt() {
        out.println(String.format(INPUT_PROMPT, MIN_INPUT, MAX_INPUT));
        int result = scanner.nextInt();
        if (result > MAX_INPUT || result < MIN_INPUT) {
            throw new RuntimeException(String.format(UNEXPECTED_INPUT, result, MIN_INPUT, MAX_INPUT));
        }
        return result;
    }

    public void printMatrix(Matrix2DSquare matrix, String name) {
        out.println(String.format(MATRIX_MSG, name));
        out.println(matrix.print());
    }

    public void operationStatus(String operationName, long timeExceeded) {
        out.println(String.format(OPERATION_STATUS, operationName, timeExceeded));
    }
}
