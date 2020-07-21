package ua.anikitin.matrices.multiply;

import ua.anikitin.matrices.matrix.Matrix2DSquare;

public class SequentialMatrixMultiplication implements MatrixMultiplicationStrategy {
    @Override
    public Matrix2DSquare multiply(Matrix2DSquare arg0, Matrix2DSquare arg1) {
        if (arg0.getSize() != arg1.getSize()) {
            throw new RuntimeException("matrices should be same size to perform multiplication!");
        }
        int size = arg0.getSize();
        Matrix2DSquare result = new Matrix2DSquare(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int resultIJ = ArithmeticalUtil.multiplyRowToColumn(arg0, i, arg1, j);
                result.setXY(i, j, resultIJ);
            }
        }
        return result;
    }
}
