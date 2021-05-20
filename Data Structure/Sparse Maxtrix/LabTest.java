import java.util.*;
import java.lang.*;

public class LabTest {
	static Scanner in;
	public static void main(String[] args) {
		in = new Scanner(System.in);
		try {
		SparseMatrix a;
		SparseMatrix b;
		SparseMatrix c;
		while(true) {
			// form the SparseMatrix a
			a = InitSparseMatrix("a");
			System.out.println("a");
			System.out.println(a);

			System.out.println("a Transpose");
			b = a.FastTranspose();
			System.out.println(b);

			System.err.println("Continue (y/n) ");
			String ans = in.next();
			if(ans.equals("n"))
				break;
		}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	static SparseMatrix InitSparseMatrix(String msg) {
		SparseMatrix sm;
		System.err.println("Init SparseMatrix " + msg);

		System.err.println("Enter the number of rows and columns : ");
		int rows = in.nextInt();
		int cols = in.nextInt();
		sm = new SparseMatrix(rows, cols, 0);

		int row, col, value;
		while (true) {
			System.err.println("Enter a term (row, col, value)");
			row = in.nextInt();
			if(row == -1)
				return sm;
			col = in.nextInt();
			value = in.nextInt();
			sm.NewTerm(row, col, value);
		}
	}
}



