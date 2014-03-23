package SChemP; 

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * A matrix of floating point numbers with a set of 
 * linear algebra methods.
 * 
 * @author Matthew W. Coan
 * Started On: 10/01/2001
 * Last Modified: 11/28/2001
 * 
 * @author Scott Kolodziej
 * Removed main block, added isAllIntegers(), sumColumn() and serialVersionUID.
 * Last Modified: 12/13/07
 */
public class Matrix implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = -1489017731098869076L;
	private double _mat[][] = null;
   private int _rows;
   private int _cols;

   /**
    * Construct a matrix from a two dimensional array
    * of double.
    * @param array the array of numbers.
    * @param rows the number of rows of the array.
    * @param cols the number of columns of the array.
    */
   public Matrix(double array[][], int rows, int cols) {
      _mat = array;
      _rows = rows;
      _cols = cols;
   }
   
   /**
    * Construct a new matrix.
    * @param rows the number of rows in the new matrix.
    * @param cols the number of columns in the new matrix.
    * @param startValue the starting value for each entry in
    *                   the new matrix.
    */
   public Matrix(int rows, int cols, double startValue) {
      super();
      _rows = rows;
      _cols = cols;
      _mat = new double[rows][cols];
      int i,j;
      for(i = 0; i < _rows; i++)
         for(j = 0; j < _cols; j++)
            _mat[i][j] = startValue;
   }

   /**
    * Construct a new matrix from a preexisting one.
    * @param mat the matrix to copy.
    */
   public Matrix(Matrix mat) {
      super();
      _rows = mat._rows;
      _cols = mat._cols;
      _mat = new double[_rows][_cols];
      int i,j;
      for(i = 0; i < _rows; i++)
         for(j = 0; j < _cols; j++)
            _mat[i][j] = mat._mat[i][j];
   }   
   
   /**
    * Construct a new matrix.
    * @param rows the number of rows in the new matrix.
    * @param cols the number of colums in the new matrix.
    */
   public Matrix(int rows, int cols) {
      this(rows, cols, 0.0);
   }
      
   /**
    * Return the number of rows.
    * @return the number of rows.
    */
   public int rows() { return _rows; }
   
   /**
    * Return the number of colums.
    * @return the number of colums.
    */
   public int cols() { return _cols; }
   
   /**
    * Set the value of an entry in the matrix.
    * @param i which row.
    * @param j which colum.
    * @param value the value to set.
    */
   public void set(int i, int j, double value) {
      _mat[i][j] = value;
   }
   
   /**
    * Return the value at an i,j entry in the matrix.
    * @param i the row.
    * @param j the column.
    * @return the value at that i,j entry.
    */
   public double get(int i, int j) {
      return _mat[i][j];
   }
   
   /**
    * Test to see if another matrix is equal 
    * to this matrix.
    * @param A the other matrix.
    * @return true if they are equal. False, otherwise.
    */
   public boolean equal(Matrix A) {
      int i, j;
      
      if(_cols != A._cols || _rows != A._rows)
         return false;
      
      for(i = 0; i < _rows; i++) {
         for(j = 0; j < _cols; j++) {
            if(_mat[i][j] != A._mat[i][j])
               return false;
         }
      }
      
      return true;
   }
   
   /**
    * Multiply this matrix by another and return the product 
    * as a new matrix.
    * @param A a matrix.
    * @return null if the dimensions are wrong.  Otherwise, a 
    *              new matrix that is the product of this 
    *              matrix and A is returned.
    */
   public Matrix mul(Matrix A) {
      if(_cols != A._rows)
         return null;
      Matrix ret = new Matrix(_rows, A._cols, 0.0);
      double prod;
      int i,j,k;
      for(i = 0; i < _rows; i++) {
         prod = 0.0;
         for(j = 0; j < A._cols; j++) {
            prod = 0.0;
            for(k = 0; k < A._rows; k++)
               prod += _mat[i][k] * A._mat[k][j];
            ret.set(i, j, prod);
         }
      }
      return ret;
   }
   
   /**
    * Add a matrix to this matrix and return the result.
    * @param A the matrix to add with.
    * @return null if the two matrixes can not be added 
    *         together.  Otherwise, the result of the addition
    *         operation is returned.
    */
   public Matrix add(Matrix A) {
      if(_rows != A._rows || _cols != A._cols)
         return null;
      int i, j;
      Matrix ret = new Matrix(_rows, _cols, 0.0);
      for(i = 0; i < _rows; i++)
         for(j = 0; j < _cols; j++)
            ret.set(i, j, _mat[i][j] + A._mat[i][j]);
      return ret;
   }

   /**
    * Subtract a matrix to this matrix and return the result.
    * @param A the matrix to subtract.
    * @return null if the two matrixes can not be subtracted.
    *         Otherwise, the result of the subtraction
    *         operation is returned.
    */
   public Matrix sub(Matrix A) {
      if(_rows != A._rows || _cols != A._cols)
         return null;
      int i, j;
      Matrix ret = new Matrix(_rows, _cols, 0.0);
      for(i = 0; i < _rows; i++)
         for(j = 0; j < _cols; j++)
            ret.set(i, j, _mat[i][j] + A._mat[i][j]);
      return ret;
   }
   
   /**
    * Scaler multipy.
    * @param k a scaler value to multiply the matrix by.
    * @return return the result matrix of the scaler 
    *         multiplication.
    */
   public Matrix scalerMul(double k) {
      Matrix ret = new Matrix(_rows, _cols, 0.0);
      int i,j;
      for(i = 0; i < _rows; i++)
         for(j = 0; j < _cols; j++)
            ret._mat[i][j] = k * _mat[i][j];
      return ret;
   }
   
   /**
    * Compute the trace of a matrix.
    * @return the value of this matrixes trace.
    * @exception an Exception is raised if the 
    *            matrix is not square.
    */
   public double trace() throws Exception {
      if(_cols != _rows)
         throw new Exception("Matrix must be square to compute the trace!");
      double total = 0.0;
      int i,j;
      for(i = 0; i < _rows; i++)
         for(j = 0; j < _cols; j++) {
            if(i == j)
               total += _mat[i][j];
         }
      return total;
   }
   
   /**
    * Compute the transpose of this matrix.
    * @return the transpose of this matrix.
    */
   public Matrix transpose() {
      Matrix ret = new Matrix(_cols, _rows, 0.0);
      int i,j;
      for(i = 0; i < _rows; i++) 
         for(j = 0; j < _cols; j++)
            ret._mat[j][i] = _mat[i][j];
      return ret;
   }

   private double _det2x2() {
      return ((_mat[0][0] * _mat[1][1]) - (_mat[0][1] * _mat[1][0]));
   }
   
   
   /**
    * Remove a row from the matrix.
    * @param k the index of a row to remove.
    */
   public void removeRow(int k) {
      double mat[][] = new double[_rows - 1][_cols];
      int i,j,ii = 0;
      for(i = 0; i < _rows; i++) {
         for(j = 0; j < _cols; j++) {
            if(i != k) {
               mat[ii][j] =_mat[i][j];
               if((j+1) == _cols)
                  ii++;
            }
         }
      }
      _mat = mat;
      _rows--;
   }
   
   /**
    * Remove a column from the matrix.
    * @param k the index of a column to remove.
    */
   public void removeCol(int k) {
      double mat[][] = new double[_rows][_cols - 1];
      int i,j,ii;
      for(i = 0; i < _rows; i++) {
         ii = 0;
         for(j = 0; j < _cols; j++) {
            if(j != k) {
               mat[i][ii] =_mat[i][j];
               ii++;
            }
         }
      }
      _mat = mat;
      _cols--;
   }
   
   /**
    * Compute the determanent of this matrix.
    * @return the determanent of this matirx.
    */
   public double det()
   throws Exception {
      if(_rows != _cols)
         throw new Exception("Matrix must be square to compute the determanent!");
      if(_rows == 2 && _cols == 2)
         return _det2x2();
      else if(_rows == 1 && _cols == 1)
         return _mat[0][0];
      else {
         double total = 0.0;
         Matrix temp;
         //int i2,j2;
         for(int ii = 0; ii < _cols; ii++) {
            temp = new Matrix(this);
            temp.removeRow(0);
            temp.removeCol(ii);
            total += (_mat[0][ii] * Math.pow(-1.0, (ii+1)+1) * temp.det());
         }
         return total;
      }
   }
   
   /**
    * Compute the adjoint of this matrix.
    * @return the adjoint of this matrix.
    */
   public Matrix adj() 
   throws Exception {
      Matrix temp, ret = new Matrix(_rows, _cols);
      int i,j;
      for(i = 0; i < _rows; i++) {
         for(j = 0; j < _cols; j++) {
            temp = new Matrix(this);
            temp.removeRow(i);
            temp.removeCol(j);
            ret.set(i, j, Math.pow(-1.0, (i+1)+(j+1)) * temp.det());
         }
      }
      return ret.transpose();
   }
   
   /**
    * Compute the inverse of a matrix.
    * @return the inverse of this matrix or null if there is no inverse
    *         for this matrix.
    */
   public Matrix inverse() 
   throws Exception {
      double d = det();
      if(d == 0)
         return null;
      return adj().scalerMul(1 / d);
   }
   
   /**
    * Swap two rows of this matrix.
    * @param i1 the first row.
    * @param i2 the second row.
    */
   public void swapRows(int i1, int i2) {
      double temp;
      for(int j = 0; j < _cols; j++) {
         temp = _mat[i1][j];
         _mat[i1][j] = _mat[i2][j];
         _mat[i2][j] = temp;
      }
   }
   
   /**
    * Multiply one row by a scaler and add it to another row.
    * @param srcRow the row to multiply by the scaler.
    * @param destRow the row to add the multipl too.
    */
   public void addMulOfRowToRow(int srcRow, int destRow, double scaler) {
      double row[] = new double[_cols];
      for(int i = 0; i < _cols; i++)
         row[i] = _mat[srcRow][i] * scaler;
      for(int i = 0; i < _cols; i++)
         _mat[destRow][i] += row[i];
   }
   
   /**
    * Multiply a row by a scaler.
    * @param i the row index.
    * @param s the scaler.
    */
   public void mulRowByScaler(int i, double s) {
      for(int j = 0; j < _cols; j++)
         _mat[i][j] *= s;
   }
   
   /**
    * Row echelon reduce a matrix.
    * @return the row reduced matrix for this matrix.
    */
   public Matrix rref() {
      Matrix ret = new Matrix(this);
      int i,j,k;
      //double temp;
      boolean found;
      for(i = 0, j = 0; i < _rows && j < _cols; i++, j++) {
         if(ret._mat[i][j] == 1.0) {
            for(k = 0; k < _rows; k++) {
               if(k != i && ret._mat[k][j] != 0.0) {
                  ret.addMulOfRowToRow(i, k, -ret._mat[k][j]);
               }
            }
         }
         else if(ret._mat[i][j] == 0.0) {
            found = false;
            for(k = i; k < _rows; k++) {
               if(ret._mat[k][j] != 0.0 && i != k) {
                  found = true;
                  break;
               }
            }
            if(found) {
               ret.swapRows(i, k);
               i--;
               j--;
            }
            else {
               i--;
            }
         }
         else {
            ret.mulRowByScaler(i, 1.0 / ret._mat[i][j]);
            for(k = 0; k < _rows; k++) {
               if(k != i && ret._mat[k][j] != 0.0) {
                  ret.addMulOfRowToRow(i, k, -ret._mat[k][j]);
               }
            }
         }
      }
      return ret;
   }
      
   /**
    * Return the identity matrix for a given dimension.
    * @param dim the dimension for identity matrix.
    */
   public static Matrix identity(int dim) {
      Matrix ret = new Matrix(dim, dim);
      for(int i = 0, j = 0; j < dim; j++, i++)
         ret._mat[i][j] = 1.0;
      return ret;
   }
   
   /**
    * Return a boolean, true if all elements in the matrix are integral, false otherwise.
    * @param allIntegers A boolean, true if all elements are integral, false otherwise.
    */
   public boolean isAllPositiveIntegers()
   {
	   for(int r = 0; r < _rows; r++)
	   {
		   for(int c = 0; c < _cols; c++)
		   {
			   if(Math.abs(_mat[r][c] - Math.round(_mat[r][c])) > .001 || _mat[r][c] < 0)
			   {
				   return false;
			   }
		   }
	   }
	   return true;
   }
   
   /**
    * Return the sum of the specified column.
    * @param sum the sum of the column.
    */
   public double sumColumn(int col)
   {
	   double sum = 0;
	   
	   for(int r = 0; r < _rows; r++)
	   {
		   sum += _mat[r][col];
	   }
	   
	   return sum;
   }
   
   /**
    * Print the matrix.
    * @param out a print stream to write to.
    */
   public void print(PrintWriter out) 
   throws IOException {
      String temp;
      int i, j, k, size = 0;
     
      for(i = 0; i < _rows; i++) {
         for(j = 0; j < _cols; j++) {
            temp = _mat[i][j] + "";
            if(temp.compareTo("-0.0") == 0)
               temp = "0.0";
            if(temp.length() > size)
               size = temp.length();
         }
      }
      
      for(i = 0; i < _rows; i++) {
         for(j = 0; j < _cols; j++) {
            temp = _mat[i][j] + "";
            if(temp.compareTo("-0.0") == 0)
               temp = "0.0";
            for(k = 0; k < ((size - temp.length()) + 1); k++)
               out.print(" ");
            out.print(temp);
         }
         out.println();
         out.flush();
      }
   }
}