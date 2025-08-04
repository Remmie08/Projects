package HillCypher;

public class matrixCreate {
    private int[][] data;
    private int rows;
    private int cols;
    private boolean trans;

    public matrixCreate(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new int[rows][cols];
    }
    
    public matrixCreate(int[][] data) {
        this.data = data;
        this.rows = data.length;
        this.cols = data[0].length;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int get(int row, int col) {
        return data[row][col];
    }

    public void set(int row, int col, int value) {
        data[row][col] = value;
    }
    
    public matrixCreate add(matrixCreate other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Matrices must have the same dimensions");
        }
        matrixCreate result = new matrixCreate(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, this.get(i, j) + other.get(i, j));
            }
        }
        return result;
    }
    
    public matrixCreate multiply(matrixCreate other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Number of columns in the first matrix must be equal to the number of rows in the second matrix");
        }
        matrixCreate result = new matrixCreate(this.rows, other.getCols());
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.getCols(); j++) {
                for (int k = 0; k < this.cols; k++) {
                    result.set(i, j, result.get(i, j) + this.get(i, k) * other.get(k, j));
                }
            }
        }
        return result;
    }
    public matrixCreate transpose() {
    	matrixCreate result = new matrixCreate(this.cols, this.rows);
    	for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result.set(j, i, this.get(i, j));
            }
        }
    	trans = true;
    	return result;
    }
    
    public int determinate() {
    	if (rows != 3 || cols != 3) {
    	    throw new IllegalArgumentException("Determinant only implemented for 3x3 matrices.");
    	}
    	int det = (this.get(0, 0) * this.get(1, 1) * this.get(2, 2))
	    		   + (this.get(0, 1) * this.get(1, 2) * this.get(2, 0))
	    		   + (this.get(0, 2) * this.get(1, 0) * this.get(2, 1))
	    		   - (this.get(2, 0) * this.get(1, 1) * this.get(0, 2))
	    		   - (this.get(2, 1) * this.get(1, 2) * this.get(0, 0))
	    		   - (this.get(2, 2) * this.get(1, 0) * this.get(0, 1));
    	
    	return det;
    }
    
    private int modInverse(int a, int m) {
        a = ((a % m) + m) % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1)
                return x;
        }
        throw new ArithmeticException("No modular inverse exists");
    }
    
    public matrixCreate inverseMod26() {
    	if (this.rows != 3 || this.cols != 3) {
    	    throw new IllegalArgumentException("Inverse only implemented for 3x3 matrices.");
    	}
    	
    	 int det = this.determinate();
    	 int detMod = ((int)det % 26 + 26) % 26;
    	 int invDet = modInverse(detMod, 26);
    	
    	matrixCreate result = new matrixCreate(this.rows, this.cols);	
    	
    	int a11 = (this.get(1, 1) * this.get(2, 2)) - (this.get(2, 1) * this.get(1, 2));
    	int a12 = -((this.get(1, 0) * this.get(2, 2)) - (this.get(2, 0) * this.get(1, 2)));
    	int a13 = (this.get(1, 0) * this.get(2, 1)) - (this.get(2, 0) * this.get(1, 1));
    	
		int a21 = -((this.get(0, 1) * this.get(2, 2)) - (this.get(2, 1) * this.get(0, 2)));
		int a22 = (this.get(0, 0) * this.get(2, 2)) - (this.get(2, 0) * this.get(0, 2));
		int a23 = -((this.get(0, 0) * this.get(2, 1)) - (this.get(2, 0) * this.get(0, 1)));
		
		int a31 = (this.get(0, 1) * this.get(1, 2)) - (this.get(1, 1) * this.get(0, 2));
		int a32 = -((this.get(0, 0) * this.get(1, 2)) - (this.get(1, 0) * this.get(0, 2)));
    	int a33 = (this.get(0, 0) * this.get(1, 1)) - (this.get(1, 0) * this.get(0, 1));
    	
    	result.set(0, 0, ((a11 * invDet) % 26 + 26) % 26);
    	result.set(0, 1, ((a12 * invDet) % 26 + 26) % 26);
    	result.set(0, 2, ((a13 * invDet) % 26 + 26) % 26);
    	
    	result.set(1, 0, ((a21 * invDet) % 26 + 26) % 26);
    	result.set(1, 1, ((a22 * invDet) % 26 + 26) % 26);
    	result.set(1, 2, ((a23 * invDet) % 26 + 26) % 26);
    	
    	result.set(2, 0, ((a31 * invDet) % 26 + 26) % 26);
    	result.set(2, 1, ((a32 * invDet) % 26 + 26) % 26);
    	result.set(2, 2, ((a33 * invDet) % 26 + 26) % 26);
    	
    	return result;
    }
    
	public matrixCreate mod26() {
		
		matrixCreate result = new matrixCreate(this.rows, this.cols);
		for (int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.cols; j++) {
				result.set(i, j, (this.get(i, j) % 26 + 26) % 26);
			}
		}
		return result;
	}
	
    public void print() {
       for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
    	matrixCreate matrix1 = new matrixCreate(3,3);
    	
//    	matrixCreate matrix = new matrixCreate(2, 3);
//        matrix.set(0, 0, 1.0);
//        matrix.set(0, 1, 2.0);
//        matrix.set(0, 2, 3.0);
//        matrix.set(1, 0, 4.0);
//        matrix.set(1, 1, 5.0);
//        matrix.set(1, 2, 6.0);
//        
//        System.out.println("Matrix 1:");
//        matrix.print();
       
    }

	
}