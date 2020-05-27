import org.opencv.core.*;
import org.opencv.ml.SVMSGD;

import java.util.Arrays;

import static java.lang.Math.round;

public class Matrix {

    public enum InverseMethod {
        LU(Core.DECOMP_LU), //Gaussian elimination w/ optimal pivot
        SVD(Core.DECOMP_SVD), //Singular value decomposition
        EIGEN(Core.DECOMP_EIG), //Eigenvalue decomposition, matrix must be symmetrical
        CHOLESKY(Core.DECOMP_CHOLESKY); //Cholesky factorization, matrix must be symmetrical and positively defined.

        private final int method;
        InverseMethod(int method) {
            this.method = method;
        }
    }

    private final double[][] vals;
    public Matrix(double[]... vals) {
        this.vals = vals.clone();
    }

    private Matrix(Matrix matrix) {
        vals = new double[matrix.getNumCols()][matrix.getNumRows()];
        for (int i = 0; i < matrix.getNumRows(); i++) {
            System.arraycopy(matrix.vals[i], 0, vals[i], 0, vals[i].length);
        }
    }

    public void set(int row, int col, double value) {
        vals[row][col] = value;
    }

    public double get(int row, int col) {
        return vals[row][col];
    }

    public int getNumRows() {
        return vals.length;
    }

    public int getNumCols() {
        if(this.getNumRows() == 0) {
            return 0;
        }
        return vals[0].length;
    }

    public boolean isSquare() {
        return this.getNumRows() == this.getNumCols();
    }

    public boolean isSymmetric() {
        return this.clone().transpose().equals(this);
    }

    public boolean isPositiveDefinite() {
        return false;
    }

    public Matrix transpose() {
        matToVals(valsToMat(CvType.CV_64F).t());
        return this;
    }

    public double trace() {
        Mat mat = valsToMat(CvType.CV_64F);
        Scalar trace = Core.trace(mat);
        mat.release();
        return trace.val[0];
    }

    public InverseMethod getOptimalInverseMethod() {
        if(this.isPositiveDefinite()) {
            return InverseMethod.CHOLESKY;
        }
        else if(this.isSymmetric()) {
            return InverseMethod.EIGEN;
        }
        else if(this.isSquare()) {
            return InverseMethod.LU;
        }
        else {
            return InverseMethod.SVD;
        }
    }

    //todo better error handling, this is bad and doesn't work
    public Matrix invert(InverseMethod inverseMethod) {
        try {
            matToVals(valsToMat(CvType.CV_64F).inv(inverseMethod.method));
        }
        catch (CvException exception) {
            switch (inverseMethod) {
                case LU:
                    if(!this.isSquare()) {
                        throw new RuntimeException("LU decomposition method cannot be used on non-square matrices, use SVD instead to get a pseudo-inverse.");
                    }
                    else {
                        throw new RuntimeException("Unknown error while taking inverse of matrix with LU decomposition method.");
                    }
                case EIGEN:
                    if(!this.isSquare()) {
                        throw new RuntimeException("Eigen decomposition method cannot be used on non-square matrices, use SVD instead to get a pseudo-inverse.");
                    }
                    else if(!this.isSymmetric()) {
                        throw new RuntimeException("Eigen decomposition method cannot be used on non-symmetric matrices.");
                    }
                    else {
                        throw new RuntimeException("Unknown error while taking inverse of matrix with Eigen decomposition method.");
                    }
                case CHOLESKY:
                    if(!this.isSquare()) {
                        throw new RuntimeException("Cholesky decomposition method cannot be used on non-square matrices, use SVD instead to get a pseudo-inverse.");
                    }
                    else if(!this.isSymmetric()) {
                        throw new RuntimeException("Cholesky decomposition method cannot be used on non-symmetric matrices.");
                    }
                    else if(!this.isPositiveDefinite()) {
                        throw new RuntimeException("Cholesky decomposition method cannot be used on non-positive-definite matrices.");
                    }
                    else {
                        throw new RuntimeException("Unknown error while taking inverse of matrix with Cholesky decomposition method.");
                    }
            }
        }
        return this;
    }

    public Matrix invert() {
        return invert(InverseMethod.LU);
    }

    public void multiply() {

    }

    public void determinant() {

    }

    public void scalarMultiply() {

    }

    public void scalarDivide() {

    }

    public void mask() {

    }

    public void rref() {

    }

    public void ref() {

    }

    public void rank() {

    }

    public void nullity() {

    }

    public void dimensionality() {

    }

    public void isZeroMatrix() {

    }

    public void isIdentityMatrix() {

    }

    public static void identityMatrix() {

    }

    public static void zeroMatrix() {

    }

    public static void onesMatrix() {

    }

    private Mat valsToMat(int type) {
        Mat mat = new Mat(this.getNumRows(), this.getNumCols(), type);
        for (int i = 0; i < vals.length; i++) {
            mat.put(i,0, vals[i]);
        }
        return mat;
    }

    private void matToVals(Mat mat) {
        for (int i = 0; i < mat.height(); i++) {
            mat.get(i,0, vals[i]);
            FakeNumpy.floatingPointFix(vals[i]);
        }
        mat.release();
    }



    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for(double[] row : vals) {
            output.append(Arrays.toString(row));
            output.append('\n');
        }
        output.deleteCharAt(output.length() - 1);
        return output.toString();
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        for (int row = 0; row < this.getNumRows(); row++) {
            for (int col = 0; col < this.getNumCols(); col++) {
                long bits = Double.doubleToLongBits(this.get(row, col));
                int hash  = (int) (bits ^ (bits >>> 32));
                hashCode = 37*hashCode + hash;
            }
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Matrix) {
            Matrix matrix = (Matrix) obj;
            if(this.getNumRows() != matrix.getNumRows() || this.getNumCols() != matrix.getNumCols()) {
                return false;
            }

            boolean equal = true;
            for (int row = 0; row < this.getNumRows(); row++) {
                for (int col = 0; col < this.getNumCols(); col++) {
                    equal &= matrix.get(row, col) == this.get(row, col);
                }
            }
            return equal;
        }
        return false;
    }

    @Override
    public Matrix clone() {
        return new Matrix(this);
    }
}
