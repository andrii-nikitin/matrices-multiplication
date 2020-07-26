package ua.anikitin.matrices.multiply;

import ua.anikitin.matrices.matrix.Matrix2DSquare;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ParallelMatrixMultiplication implements MatrixMultiplicationStrategy {
    private final ExecutorService executor;

    public ParallelMatrixMultiplication(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public Matrix2DSquare multiply(Matrix2DSquare arg0, Matrix2DSquare arg1) {
        int size = arg0.getSize();
        Matrix2DSquare result = new Matrix2DSquare(size);
        CompletableFuture<Void>[] waitList = new CompletableFuture[size];
        for (int i = 0; i < size; i++) {
            final int rowNum = i;
            CompletableFuture<Void> resultFuture = CompletableFuture.supplyAsync(()
                    -> calculateRow(arg0, arg1, rowNum, size, result), executor);
            waitList[i] = resultFuture;
        }
        try {
            CompletableFuture.allOf(waitList).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Concurrent execution failed", e);
        }
        return result;
    }

    private Void calculateRow(Matrix2DSquare arg0, Matrix2DSquare arg1, int rowNum, int size, Matrix2DSquare result) {
        for (int columnNum = 0; columnNum < size; columnNum++) {
            int resultIJ = ArithmeticalUtil.multiplyRowToColumn(arg0, rowNum, arg1, columnNum);
            result.setXY(rowNum, columnNum, resultIJ);
        }
        return null;
    }
}
