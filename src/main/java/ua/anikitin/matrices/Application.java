package ua.anikitin.matrices;

import ua.anikitin.matrices.matrix.Matrix2DSquare;
import ua.anikitin.matrices.matrix.MatrixGenerator;
import ua.anikitin.matrices.multiply.MatrixMultiplicationStrategy;
import ua.anikitin.matrices.multiply.ParallelMatrixMultiplication;
import ua.anikitin.matrices.multiply.SequentialMatrixMultiplication;

public class Application {

    public static void main(String... args) {
        UserInterface userInterface = new UserInterface(System.in, System.out);
        int matrixSize = userInterface.readInt();
        MatrixGenerator generator = new MatrixGenerator();

        Matrix2DSquare matrix1 = generator.generateRandomMatrix(matrixSize);
        userInterface.printMatrix(matrix1, "1");

        Matrix2DSquare matrix2 = generator.generateRandomMatrix(matrixSize);
        userInterface.printMatrix(matrix2, "2");

        runWithTimeMeasurement(() -> runSequentially(matrix1, matrix2, userInterface),
                "sequential division", userInterface);

        runWithTimeMeasurement(() -> runInParallel(matrix1, matrix2, userInterface),
                "parallel division", userInterface);
    }

    private static void runInParallel(Matrix2DSquare matrix1, Matrix2DSquare matrix2,
                                        UserInterface userInterface) {
        Matrix2DSquare result = Matrix2DSquare.multiply(matrix1, matrix2, new ParallelMatrixMultiplication());
        userInterface.printMatrix(result, "parallel division result");

    }

    private static void runSequentially(Matrix2DSquare matrix1, Matrix2DSquare matrix2,
                                        UserInterface userInterface) {
        Matrix2DSquare result = Matrix2DSquare.multiply(matrix1, matrix2, new SequentialMatrixMultiplication());
        userInterface.printMatrix(result, "sequential division result");

    }

    private static void runWithTimeMeasurement(Runnable function, String operationName, UserInterface userInterface) {
        long startTime = System.currentTimeMillis();
        function.run();
        long endTime = System.currentTimeMillis();
        userInterface.operationStatus(operationName, endTime - startTime);
    }
}
