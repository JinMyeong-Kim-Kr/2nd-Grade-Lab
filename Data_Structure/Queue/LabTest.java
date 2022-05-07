import java.util.*;

class LabTest {
	static Scanner in;
	public static void main(String[] args) {
		in = new Scanner(System.in);

		MyQueue<Integer> q = new MyQueue<>(7);

		System.out.println("");
		while(true) {
			try {
				System.err.print("Queueop > ");
	
				String cmd = in.next();
				if(cmd.equals("push")) {
					int item = in.nextInt();
					System.out.println(cmd + " " + item);
					q.Push(item);
				} else if(cmd.equals("pop")) {
					System.out.println(cmd);
					q.Pop();
				} else if(cmd.equals("quit"))
					break;
			} catch (Exception e) {
				System.out.println(e);
			}
			System.out.println(q);
			System.out.println("");
		}
	}
}


