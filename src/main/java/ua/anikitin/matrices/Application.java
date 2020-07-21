package ua.anikitin.matrices;

import ua.anikitin.matrices.matrix.Matrix2DSquare;
import ua.anikitin.matrices.matrix.MatrixGenerator;
import ua.anikitin.matrices.multiply.ParallelMatrixMultiplication;
import ua.anikitin.matrices.multiply.SequentialMatrixMultiplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {

    private static final int THREAD_POOL_CAPACITY = 10;
    private static final ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_CAPACITY);


    public static void main(String... args) {
        UserInterface userInterface = new UserInterface(System.in, System.out);
        int matrixSize = userInterface.readInt();
        MatrixGenerator generator = new MatrixGenerator();

        Matrix2DSquare matrix1 = generator.generateRandomMatrix(matrixSize);
        userInterface.printMatrix(matrix1, "1");

        Matrix2DSquare matrix2 = generator.generateRandomMatrix(matrixSize);
        userInterface.printMatrix(matrix2, "2");

        runSequentially(matrix1, matrix2, userInterface);
        runInParallel(matrix1, matrix2, userInterface);
    }

    private static void runInParallel(Matrix2DSquare matrix1, Matrix2DSquare matrix2,
                                      UserInterface userInterface) {
        long startTime = System.currentTimeMillis();
        Matrix2DSquare result = Matrix2DSquare.multiply(matrix1, matrix2, new ParallelMatrixMultiplication(executor));
        userInterface.printMatrix(result, "parallel division result");
        long endTime = System.currentTimeMillis();
        userInterface.operationStatus("parallel division", endTime - startTime);

    }

    private static void runSequentially(Matrix2DSquare matrix1, Matrix2DSquare matrix2,
                                        UserInterface userInterface) {
        long startTime = System.currentTimeMillis();
        Matrix2DSquare result = Matrix2DSquare.multiply(matrix1, matrix2, new SequentialMatrixMultiplication());
        userInterface.printMatrix(result, "parallel division result");
        long endTime = System.currentTimeMillis();
        userInterface.operationStatus("sequential division", endTime - startTime);
    }
}
