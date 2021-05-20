import java.util.*;

import java.lang.*;

class SparseMatrix {
	MatrixTerm[] smArray;
	int rows, cols;
	int capacity;   // size of smArray
	int terms;  // number of nonzero terms

	class MatrixTerm {
		int row;
		int col;
		int value;
	};

	// constructor
	SparseMatrix(int r, int c, int t) {
		capacity = 128;
		smArray = new MatrixTerm[capacity];
		terms = t;
		rows = r;
		cols = c;
	}

	/**
	*  Add a new term to the end of smArray
	*/
	void NewTerm(int row, int col, int value) {   
		// Add a new term to the end of smArray

		if(terms == capacity) {
			// double capacity of smArray
			try {
				ChangeSize1D (capacity * 2);
			} catch (Exception e) {
				System.out.println("not enough capacity");
			}
		}
		smArray[terms] = new MatrixTerm();
		smArray[terms].row = row - 1;
		smArray[terms].col = col - 1;
		smArray[terms++].value = value;
	}

	int StoreSum (int sum, int r, int c) {
		//If sum!= 0, then it along with its row and column 
		 //position are stored as the last term in *this
		if (sum != 0) { 
			if (terms == capacity) {
				try {
					ChangeSize1D (capacity * 2);
				} catch (Exception e) {
					System.out.println("not enough capacity");
				}
			}
			smArray[terms] = new MatrixTerm();
			smArray[terms].row = r - 1;
			smArray[terms].col = c - 1;
			smArray[terms++].value = sum;
			return 0;
		}
		return 0;
	}


	void ChangeSize1D (final int newSize) throws Exception {
		//Change the size of smArray to newSize
		if(newSize < terms) 
			throw new Exception("New size must be >= number of terms");

		capacity = newSize;
		MatrixTerm[] temp = new MatrixTerm[capacity]; // new array
		System.arraycopy(smArray, 0, temp, 0, terms);
		smArray = temp;
	}

	SparseMatrix FastTranspose() {  
		// Return the transpose of *this in O(terms + cols) time.
		SparseMatrix b = new SparseMatrix(cols, rows, terms);
		int i = 0;
		if (terms > 0){
			int[] rowSize = new int[cols];
			int[] rowStart = new int[cols];

			for(i = 0; i < terms; i++) { // 0, 1
				rowSize[smArray[i].col]++;
			}
			rowStart[0] = 0;
			for(i = 1; i < cols; i++){
				rowStart[i] = rowStart[i - 1] + rowSize[i - 1];
			}
			for(i = 0; i < terms; i++){
				int j = rowStart[smArray[i].col];
				b.smArray[j] = new MatrixTerm();
				b.smArray[j].row = smArray[i].col;
				b.smArray[j].col = smArray[i].row;
				b.smArray[j].value = smArray[i].value;
				rowStart[smArray[i].col]++;
			}
		}
		return b;
	}


	public String toString() {
		String a = new String();
		// show the content of smArray
		System.out.println("======");
		int term = 0;
		int currow = -1;
		loop:
		for(int j = 0; j < rows ; j++){ //열의 순환
			currow += 1;
		   for(int k = 0; k < cols; k++){  // 행의 순환
			  if(smArray[term].col == k && smArray[term].row == j){ 
				 a += Integer.toString(smArray[term].value) + " "; // j열의 value 값을 삽입
				 term += 1;
				 if(terms == term){
					for(int t = 0; t < cols - k - 1; t++){
					a += "0 ";
					}
					break loop;
				 }
			  }
			  else{
				 a += "0 "; // j열의 value 값을 삽입
			  }
		   }
		   a += "\n";
		}
		a += "\n";
		for(int r = 0; r < rows - currow - 1; r++){
			for(int c = 0;c < cols; c++){
				a += "0 ";
			}
			a += "\n";
		}
		return a; 
	 }
}


