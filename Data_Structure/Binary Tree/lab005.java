import java.util.*;

/**
 */

class SimpleTree  {
	int[] tree; // tree

	int largestNum;	// the largest node number in the binary tree
	int capacity;	// size of the tree tree


	SimpleTree(int theCapacity) { // SimpleTree constructor. 
		capacity = theCapacity;
		tree = new int[capacity];	// tree[0] is not used
		largestNum = 0;
	}

	public String toString() { // Show all the element in sequence
		String str = new String();
		str = "T[] : - ";
		// print all the nodes in the tree
		for(int i = 1; i <= largestNum; i++)
			str += tree[i] + "  ";
		return str;
	}

	void  Init(int[] es, int n) {	
		// initialize the tree by using the input
		largestNum = n;
		tree = Arrays.copyOf(es, 1024);
	}

	void  PreOrder(int node) {
		System.out.print(tree[node] + " "); //root 출력
		//일단.. node의 subtree 왼쪽은 node/2 오른쪽은 node/2 +1
		if(node > largestNum){ 
			return;
		}
		if (tree[2*node] != 0)
			PreOrder(2*node);
		if (tree[2*node + 1] != 0)
			PreOrder(2*node+1);
	}

	void  InOrder(int node) {
		if(node > largestNum){
			return;
		}
		if (tree[2*node] != 0){
			InOrder(2*node);
		}
		System.out.print(tree[node] + " ");
		if (tree[2*node + 1] != 0){
			InOrder(2*node+1);}
	}

	void  PostOrder(int node){
		if(node > largestNum)
			return;
		if (tree[2*node] != 0)
			PostOrder(2*node);
		if (tree[2*node + 1] != 0)
			PostOrder(2*node+1);
		System.out.print(tree[node] + " ");
	}
}


