package ua.anikitin.matrices.multiply;

import ua.anikitin.matrices.matrix.Matrix2DSquare;

public interface MatrixMultiplicationStrategy {
    Matrix2DSquare multiply(Matrix2DSquare arg0, Matrix2DSquare arg1);
}
