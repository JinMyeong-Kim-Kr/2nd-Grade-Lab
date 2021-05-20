import java.util.*;

class LabTest {
	static Scanner in;
	public static void main(String[] args) {
		in = new Scanner(System.in);

		SimpleTree atree = new SimpleTree(256);	 

		while(true) {
			try {
				System.err.print("Tree > ");
	
				String cmd = in.next();

				if(cmd.equals("init")) {
					int [] intarr = new int[1024];
					int i = 1;
					int item;
					while(true) {
						item = in.nextInt();
						if(item == -1)
							break;
						intarr[i++] = item;
					}
					atree.Init(intarr, i - 1);
				} else if(cmd.equals("pre")) {
					int index = in.nextInt();
					atree.PreOrder(index);
				} else if(cmd.equals("in")) {
					int index = in.nextInt();
					atree.InOrder(index);
				} else if(cmd.equals("post")) {
					int index = in.nextInt();
					atree.PostOrder(index);
				} else if(cmd.equals("quit"))
					break;
			} catch (Exception e) {
				System.out.println(e);
			}
			System.out.println("");
			System.out.println(atree);
		}
	}
}

