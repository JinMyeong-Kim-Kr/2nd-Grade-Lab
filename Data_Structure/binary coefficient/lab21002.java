import java.util.*;

/**
 * Lab 002 
 * @version 1.0
 * @author Sanghwan Lee
 * @id 20201234
 */

class LabTest {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		while(true) {
			System.err.println("Enter n m : ");
			int n = in.nextInt();
			int m = in.nextInt();
			if(n == 0) 
				break;

			System.out.println("Combination(" +  
				n + ", " + m + ") : " + 
				Combi(n, m));
		}
		in.close();
	}
	static int Combi(int n, int m) {
		if( (m == 0) || (m == n) ){ /* m이 0 또는 m == n이면 조합의 수는 1*/
			return 1;
		}
		else{		
			return Combi(n-1, m) + Combi(n-1, m-1);
		}
	}
}

