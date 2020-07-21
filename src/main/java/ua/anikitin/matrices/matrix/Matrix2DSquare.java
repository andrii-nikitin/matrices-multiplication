package ua.anikitin.matrices.matrix;

import ua.anikitin.matrices.multiply.MatrixMultiplicationStrategy;

public class Matrix2DSquare {

    private final int[] data;
    private final int size;

    public Matrix2DSquare(int size, int[] data) {
        this.data = data;
        this.size = size;
    }

    public Matrix2DSquare(int size) {
        this.size = size;
        this.data = new int[size * size];
    }

    public static Matrix2DSquare multiply(Matrix2DSquare arg0, Matrix2DSquare arg1,
                                          MatrixMultiplicationStrategy strategy) {
        return strategy.multiply(arg0, arg1);
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
}
