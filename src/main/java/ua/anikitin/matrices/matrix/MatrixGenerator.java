package ua.anikitin.matrices.matrix;

public class MatrixGenerator {

    public Matrix2DSquare generateRandomMatrix(int size) {
        int[] matrixData = new int[size * size];
        for (int i = 0; i < size * size; i++) {
            matrixData[i] = createRandomBinaryValue();
        }
        return new Matrix2DSquare(size, matrixData);
    }

    private int createRandomBinaryValue() {
        return (Math.random() < 0.5 ? 0 : 1);
    }
}
