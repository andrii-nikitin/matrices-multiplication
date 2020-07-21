package ua.anikitin.matrices.multiply;

import ua.anikitin.matrices.matrix.Matrix2DSquare;

public class ArithmeticalUtil {

    public static int addBinaryNumbers(int arg0, int arg1) {
        return (arg0 + arg1) % 2;
    }

    public static int multiplyBinaryNumbers(int arg0, int arg1) {
        return (arg0 * arg1) % 2;
    }

    public static int multiplyRowToColumn(Matrix2DSquare arg0, int row, Matrix2DSquare arg1, int column) {
        int result = 0;
        int size = arg0.getSize();
        for (int i = 0; i < size; i++) {
            int el0 = arg0.getXY(row, i);
            int el1 = arg1.getXY(i, column);
            int multiplication = ArithmeticalUtil.multiplyBinaryNumbers(el0, el1) ;
            result = ArithmeticalUtil.addBinaryNumbers(result, multiplication);
        }
        return result;
    }
}
