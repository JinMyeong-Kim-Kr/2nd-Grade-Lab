import java.util.*;

class LabTest {
	static Scanner in;
	public static void main(String[] args) {
		in = new Scanner(System.in);

		try {
			while(true) {
				System.err.println ("Enter an infix expression ");

				Vector<String> infix = new Vector<String>();
				while(true) {
					String token = in.next();
					if(token.equals("#"))
						break;
					infix.add(token);
				}

				double t = Expression.Eval(infix);
				System.out.println("Evaluation : " + String.format("%.1f", t));

				System.err.println ("Continue (y/n) ");
				String token = in.next();
				if(token.equals("n"))
					break;
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}

