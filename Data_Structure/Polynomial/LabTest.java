import java.util.*;

class LabTest {
	static Scanner in;
	public static void main(String[] args) {
		in = new Scanner(System.in);
		while(true) {
			Polynomial a = new Polynomial(2);
			Polynomial b = new Polynomial(2);

			// initialize the Polynomial a
			InitPolynomial(a, "a");
			System.out.printf("A(0.2) = %.4f\n", a.Evaluate(0.2));

			// initialize the Polynomial a
			InitPolynomial(b, "b");
			System.out.printf("B(1.5) = %.4f\n", b.Evaluate(1.5));
	
			Polynomial c;
			System.out.println("c = a + b");
			c = a.Add(b);
			System.out.printf("C(2.5) = %.4f\n", c.Evaluate(2.5));

			if(a.Equals(b))
				System.out.println("A and B ARE the same!");
			else
				System.out.println("A and B are NOT the same!");

			System.out.println("Continue (y/n) ");
			String ans = in.next();
			if(ans.equals("n"))
				break;
		}
	}

	static void InitPolynomial(Polynomial p, String msg) {
		System.err.println("Init Polynomial " + msg);
		float coef;
		int exp;
		while (true) {
			System.err.println("Enter a term (coef, exp)");
			coef = in.nextFloat();
			exp = in.nextInt();
			if(exp == -1) {
				return;
			}
			p.NewTerm(coef, exp);
		}
	}
}

