package ua.anikitin.matrices.multiply;

import ua.anikitin.matrices.matrix.Matrix2DSquare;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ParallelMatrixMultiplicationV2 implements MatrixMultiplicationStrategy {
    private final ExecutorService executor;

    public ParallelMatrixMultiplicationV2(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public Matrix2DSquare multiply(Matrix2DSquare arg0, Matrix2DSquare arg1) {
        int size = arg0.getSize();
        Matrix2DSquare result = new Matrix2DSquare(size);
        CompletableFuture<Integer[]>[] waitList = new CompletableFuture[size];
        AtomicBoolean isOccupied = new AtomicBoolean(false);
        for (int i = 0; i < size; i++) {
            CompletableFuture<Integer[]> resultFuture = submitDivision(arg0, arg1, i, size, result, isOccupied);
            waitList[i] = resultFuture;
        }
        try {
            CompletableFuture.allOf(waitList).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("concurrent execution failed", e);
        }
        return result;
    }

    private CompletableFuture<Integer[]> submitDivision(Matrix2DSquare arg0, Matrix2DSquare arg1, int i, int size,
                                                        Matrix2DSquare result, AtomicBoolean isOccupied) {
        CompletableFuture<Integer[]> future = CompletableFuture.supplyAsync(() -> calculateRow(arg0, arg1, i, size), executor);
        future.thenAccept(jobResult -> {
            while (!isOccupied.getAndSet(true)) {
                //waiting until other thread releases the matrix - to ensure Matrix2DSquare modification is atomic
            }
            for (int j = 0; j < size; j++) {
                result.setXY(jobResult[0], j, jobResult[j + 1]);
            }
            isOccupied.set(false);
        });
        return future;
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
