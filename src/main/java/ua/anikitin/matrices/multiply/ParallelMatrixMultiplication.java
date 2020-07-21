package ua.anikitin.matrices.multiply;

import ua.anikitin.matrices.matrix.Matrix2DSquare;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;

public class ParallelMatrixMultiplication implements MatrixMultiplicationStrategy {
    private final CompletionService<Integer[]> completionService;

    public ParallelMatrixMultiplication(ExecutorService executor) {
        this.completionService = new ExecutorCompletionService<>(executor);
    }

    @Override
    public Matrix2DSquare multiply(Matrix2DSquare arg0, Matrix2DSquare arg1) {
        if (arg0.getSize() != arg1.getSize()) {
            throw new RuntimeException("matrices should be same size to perform multiplication!");
        }
        int size = arg0.getSize();
        Matrix2DSquare result = new Matrix2DSquare(size);
        for (int i = 0; i < size; i++) {
            submitDivision(arg0, arg1, i, size);
            waitForTheExecution(size, result);
        }
        System.out.println("done");
        return result;
    }

    private void waitForTheExecution(int size, Matrix2DSquare result) {
        int received = 0;
        while (received < size) {
            try {
                Integer[] jobResult = completionService.take().get();
                for (int j = 0; j < size; j++) {
                    result.setXY(jobResult[0], j, jobResult[j]);
                }
                received++;
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("concurrent execution failed", e);
            }
        }
    }

    private void submitDivision(Matrix2DSquare arg0, Matrix2DSquare arg1, int i, int size) {
        completionService.submit(() -> calculateRow(arg0, arg1, i, size));
    }

    private Integer[] calculateRow(Matrix2DSquare arg0, Matrix2DSquare arg1, int i, int size) {
        Integer[] result = new Integer[size + 1];
        result[0] = i;
        for (int j = 0; j < size; j++){
            result[j + 1] = ArithmeticalUtil.multiplyRowToColumn(arg0, i, arg1, j);
        }
        return result;
    }
}
