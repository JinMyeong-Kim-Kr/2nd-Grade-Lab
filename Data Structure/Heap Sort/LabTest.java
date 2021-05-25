import java.util.*;

class LabTest {
	static Scanner in;

	public static void main(String[] args) {
		in = new Scanner(System.in);

		// instantiate a HeapSort class
		HeapSort hsort = new HeapSort(1024);	

		while(true) {
			System.err.print("HeapSort > ");

			// read a command from the user
			String command;
			command = in.next();

			if(command.equals("quit"))
				break;	// stop the program
        	else if(command.equals("init")) {
				int [] intarr = new int[1024];
				int i = 1;
				int item;
				while(true) {
					item = in.nextInt();
					if(item == -1)
						break;
					intarr[i++] = item;
				}
				hsort.Init(intarr, i - 1);
			} else if(command.equals("sort")) {
				hsort.Sort();
			}
	
			System.out.println(hsort);
		}
	}
}

