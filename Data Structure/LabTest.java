import java.util.*;

interface KeyValue {
	public int GetKey();  
	public char GetValue();  
}

// Item Class
// this is the key-value pair
// We are going to put this pair into each node of the BinaryST
class Item implements KeyValue {
	private int key;
	private char val;
	Item(int k, char v) {key = k; val = v;};	
	Item(int k) {key = k; };	
	Item() {key = -1;};	
	public int GetKey() { return key;}
	public char GetValue() { return val;}
};



@SuppressWarnings("unchecked")
class LabTest {
	static Scanner in;
	public static void main(String[] args) {
		in = new Scanner(System.in);
		BST<Item> bst = new BST<Item>();	
		while(true) {
			System.err.print("BST > ");
			String command;
			command = in.next();
			if(command.equals("quit"))
				break;	// stop the program
        	else if(command.equals("ins")) {
				int key = in.nextInt();
				char val = in.next().charAt(0);
				if(bst.Insert(new Item(key, val)) == false)
					System.out.println("Key Updated");
			} else if(command.equals("del")) {
				int key = in.nextInt();;
				if(bst.Delete(new Item(key)) == false)
					System.out.println("Cannot Delete, Non Existing Key");
			} else if(command.equals("get")) {
				int key = in.nextInt();;
				Item p = bst.Get(new Item(key));
				if(p == null)
					System.out.println("Non Existing Key");
				else
					System.out.println("Item " + p.GetKey() + " " + p.GetValue());
			}
	
			// show the current binary search tree
			bst.Show();
		}
	}
}

