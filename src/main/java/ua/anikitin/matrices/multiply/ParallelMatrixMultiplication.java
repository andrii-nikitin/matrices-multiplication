package ua.anikitin.matrices.multiply;

import ua.anikitin.matrices.matrix.Matrix2DSquare;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelMatrixMultiplication implements MatrixMultiplicationStrategy {
    private final ExecutorService executor;

    public ParallelMatrixMultiplication(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public Matrix2DSquare multiply(Matrix2DSquare arg0, Matrix2DSquare arg1) {
        int size = arg0.getSize();
        Matrix2DSquare result = new Matrix2DSquare(size);
        List<Future<Integer[]>> waitList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Future<Integer[]> resultFuture = submitDivision(arg0, arg1, i, size);
            waitList.add(resultFuture);
        }
        applyAll(size, result, waitList);
        return result;
    }

    private void applyAll(int size, Matrix2DSquare result, List<Future<Integer[]>> waitList) {
        waitList.forEach(future -> {
            try {
                Integer[] jobResult = future.get();
                for (int j = 0; j < size; j++) {
                    result.setXY(jobResult[0], j, jobResult[j + 1]);
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("concurrent execution failed", e);
            }
        });
    }

    private Future<Integer[]> submitDivision(Matrix2DSquare arg0, Matrix2DSquare arg1, int i, int size) {
        return executor.submit(() -> calculateRow(arg0, arg1, i, size));
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
