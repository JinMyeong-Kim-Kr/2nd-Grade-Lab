import java.util.*;

class LabTest {
	static Scanner in;
	public static void main(String[] args) {
		in = new Scanner(System.in);

		Graph graph = new Graph();	// Graph

		while(true) {
			try {
				System.err.print("Graph > ");
	
				String cmd = in.next();
				if(cmd.equals("quit"))
					break;
				if(cmd.equals("init")) {
					int nn;
					nn = in.nextInt();

					graph.Init(nn);
				} else if(cmd.equals("edge")) {
					int v1, v2, cost;
					v1 = in.nextInt();
					v2 = in.nextInt();
					cost = in.nextInt();
					graph.Edge(v1, v2, cost);
				} else if(cmd.equals("bf")) {
					int src;
					src = in.nextInt();
					graph.BellmanFord(src);
					System.out.print(graph);
					
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
//		System.err.println("");
	}
}




