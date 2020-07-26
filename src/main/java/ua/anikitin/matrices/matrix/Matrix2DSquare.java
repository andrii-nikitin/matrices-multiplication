package ua.anikitin.matrices.matrix;

import ua.anikitin.matrices.multiply.MatrixMultiplicationStrategy;
import ua.anikitin.matrices.multiply.SequentialMatrixMultiplication;

public class Matrix2DSquare {

    private final int[] data;
    private final int size;

    private MatrixMultiplicationStrategy multiplicationStrategy;

    public Matrix2DSquare(int size) {
        this.size = size;
        this.data = new int[size * size];
        multiplicationStrategy = new SequentialMatrixMultiplication();
    }

    public Matrix2DSquare(int size, int[] data) {
        this.data = data;
        this.size = size;
        multiplicationStrategy = new SequentialMatrixMultiplication();
    }

    public Matrix2DSquare multiply(Matrix2DSquare arg0) {
        if (arg0.getSize() != this.getSize()) {
            throw new RuntimeException("matrices should be the same size to perform multiplication!");
        }

        return multiplicationStrategy.multiply(this, arg0);
    }

    public void setXY(int x, int y, int value) {
        data[x + y * size] = value;
    }

    public int getXY(int x, int y) {
        return data[x + y * size];
    }

    public int getSize() {
        return size;
    }

    public String print() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            result.append("|\t");
            for (int j = 0; j < size; j++) {
                result.append(this.getXY(i, j)).append('\t');
            }

            result.append('|').append('\n');
        }
        return result.toString();
    }

    public MatrixMultiplicationStrategy getMultiplicationStrategy() {
        return multiplicationStrategy;
    }

    public void setMultiplicationStrategy(MatrixMultiplicationStrategy multiplicationStrategy) {
        this.multiplicationStrategy = multiplicationStrategy;
    }
}
