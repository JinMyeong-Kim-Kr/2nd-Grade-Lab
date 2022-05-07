import java.text.BreakIterator;
import java.util.*;

import javax.swing.tree.TreeNode;


// Name :
// Student ID :

@SuppressWarnings("unchecked")
class BST <T extends KeyValue> {

	class TreeNode <U extends KeyValue> {
		U data;	// storage for data : in HW 3, T will be Item
		TreeNode<U> leftChild;	// link to the left Child
		TreeNode<U> rightChild;	// link to the right Child
		int cnt;
		// constructors come here
		TreeNode() {
			leftChild = rightChild = null;
		}
		TreeNode(U d) {
			// data is given
			data = d;
			// the leftChild and rightChild field are null
			leftChild = rightChild = null;
		}
	};

	TreeNode <T> root;// the reference to the root node

	BST() { 
		// BST constructor. 
		root = null;
	}

    void Show() {

		System.out.print( "Pre  Order : ");
		PreOrder(root);
		System.out.println("");
		System.out.print("In   Order : ");
		InOrder(root);
		System.out.println("");
		System.out.print("Post Order : ");
		PostOrder(root);
		System.out.println("");
		if(root == null){
			System.out.print("Count      : " + 0);
			System.out.println("");
			System.out.print("Height      : " + 0);
			System.out.println("");
			System.out.println("");
		}
		else{
		root.cnt = 0;
		System.out.print("Count      : ");
		System.out.print( Count(root));
		System.out.println("");
		System.out.print("Height      : ");
		System.out.println( Height(root)+1);
		System.out.println("");
		}
	}


	// IMPLEMENT THE FOLLOWING FUNCTIONS
	

	boolean Insert(T item)  {
		// first search the key
		if(root == null) {
			root = new TreeNode<T>(item);
			return true;
		}
		TreeNode<T> ptr, parent;
		ptr = root;
		while(true){
			parent = ptr; // 반복문을 한번씩할때마다 parent를 한칸씩 내림
			if(ptr.data.GetKey() == item.GetKey()){ // root 값 update
				ptr.data = item;
				return false;
			}
			else if(ptr.data.GetKey() > item.GetKey()){ //넣으려는 노드의 데이터가 parent(root)보다 작으면 왼쪽
				ptr = parent.leftChild; 
				if(ptr == null){
					parent.leftChild = new TreeNode<T>(item);
					break;
				}
				if(ptr.data.GetKey() == item.GetKey()){ // leftchild 값 update
					parent.leftChild.data = item;
					return false;
				}
			}
			else if(ptr.data.GetKey() < item.GetKey()){ //넣으려는 노드의 데이터가 parent(root)보다 크면 오른쪽
				ptr = parent.rightChild;
				if(ptr == null){
					parent.rightChild = new TreeNode<T>(item);
					break;
				}
				if(ptr.data.GetKey() == item.GetKey()){ // righttchild 값 update
					parent.rightChild.data = item;
					return false;
				}
			}
			 
		}
		return true;
	}

	T Get(T item)  {
		// use the key field of item and find the node
		// do not use val field of item
		TreeNode<T> ptr;
		ptr = root;
		while(true){
			if(ptr == null)
				return null;
			if (ptr.data.GetKey() == item.GetKey())
				return ptr.data; 
			if(ptr.data.GetKey() > item.GetKey()){ //찾으려는 노드의 데이터가 parent(root)보다 작으면 왼쪽
				ptr = ptr.leftChild;
			}
			else if(ptr.data.GetKey() < item.GetKey()){ //찾으려는 노드의 데이터가 parent(root)보다 크면 오른쪽
				ptr = ptr.rightChild;
			} 
		 }

	} 


	boolean Delete(T item)  {
		if(root == null || Get(item) == null)
			return false;	// non existing key

		TreeNode<T> ptr, parent, tmp_parent;
		ptr = root;
		tmp_parent = root;
		if(item.GetKey() == ptr.data.GetKey() && root.leftChild == null && root.rightChild == null){ //root 혼자 잇는걸 지우면
			root = null;
			return true;
		}
		while(true){
			parent = ptr;

			if(ptr.data.GetKey() > item.GetKey()){ //지우려는 노드의 키값이  parent(root)보다 작으면 왼쪽으로
				ptr = ptr.leftChild;
				tmp_parent = ptr;
				if(ptr.data.GetKey() == item.GetKey()){ //지우려는 노드의 key와 item의 key가 같으면
					if(ptr.leftChild != null && ptr.rightChild == null){          // 근데 얘가 leftchild만 갖고잇으면
						parent.leftChild = parent.leftChild.leftChild;
						break;
					}
					else if(ptr.leftChild == null && ptr.rightChild != null){ // 근데 얘가 rightchild만 갖고잇으면
						parent.leftChild = parent.leftChild.rightChild;
						break;
					}
					else if(ptr.leftChild != null && ptr.rightChild != null){ // 둘 다 child를 갖고있으면
						ptr = ptr.rightChild; // 지우려는 노드의 right로 이동, 현재 parent는 지우려는 노드의 parent
						 	// 왼쪽으로 쭉감
							while(ptr.leftChild != null){ // rightchild가 null이 아니면 왼쪽으로 쭉 이동
					
								tmp_parent = ptr; 
								ptr = ptr.leftChild;
							}
							if(ptr.rightChild != null){ // 대체하려는 애가 또 오른쪽 애를 갖고잇어 그럼 tmp_parent.rightchild에 rightchild로 연결시켜
								parent.leftChild.data = ptr.data;
								tmp_parent.leftChild = ptr.rightChild;
								break;
								}
							else{ // 아무것도없으면
								parent.leftChild.data = ptr.data;
								tmp_parent.leftChild = null;
								break;
							}
						
					}
					else{
						parent.leftChild = null; // 걍 혼자 있는 leaf면 바로 null
					}
					break;
				}
			}
			else if(ptr.data.GetKey() < item.GetKey()){ //찾으려는 노드의 데이터가 parent(root)보다 크면 오른쪽
				ptr = parent.rightChild;
				tmp_parent = ptr;
				if(ptr.data.GetKey() == item.GetKey()){ //지우려는 노드의 key와 item의 key가 같으면
					if(ptr.leftChild != null && ptr.rightChild == null){          // 근데 얘가 leftchild만 갖고잇으면
						parent.rightChild = parent.rightChild.leftChild;
						break;
					}
					else if(ptr.leftChild == null && ptr.rightChild != null){ // 근데 얘가 rightchild만 갖고잇으면
						parent.rightChild = parent.rightChild.rightChild;
						break;
					}
					else if(ptr.leftChild != null && ptr.rightChild != null){ // 둘 다 child를 갖고있으면 그 노드의 leftchild 중에서 가장 큰 값을 찾아 넣는다
						ptr = ptr.rightChild; // 지우려는 노드의 오른쪽으로 이동, 현재 parent는 지우려는 노드의 parent

						while(ptr.leftChild != null){ // rightchild가 null이 아니면 왼쪽으로 쭉 이동
							tmp_parent = ptr; 
							ptr = ptr.leftChild;
						}
						if(ptr.rightChild != null){ // 대체하려는 애가 또 오른쪽 애를 갖고잇어 그럼 tmp_parent.rightchild에 rightchild로 연결시켜
							parent.leftChild.data = ptr.data;
							tmp_parent.leftChild = ptr.rightChild;
							break;
							}
						else{ // 아무것도없으면
							parent.rightChild.data = ptr.data;
							tmp_parent.leftChild = null;
							break;
						}	
					}
					else{
						parent.rightChild = null; //혼자있는 leaft 이므로 바로 null
						break;
					}
				}
			}
			else if(root.data.GetKey() == item.GetKey()){ //root 제거
				if(root.leftChild != null && root.rightChild == null){          // 근데 얘가 leftchild만 갖고잇으면
					root.data = root.leftChild.data;
					root.leftChild = null;
					break;
				}
				else if(root.leftChild == null && root.rightChild != null){ // 근데 얘가 rightchild만 갖고잇으면
					root.data = root.rightChild.data;
					root.rightChild = null;
					break;
				}
				else{
				ptr = root.rightChild;
				if(root.rightChild.leftChild == null){
					if(root.rightChild.rightChild != null){
						root.data = root.rightChild.data;
						root.rightChild = root.rightChild.rightChild;
						break;
					}
					root.data = root.rightChild.data;
					root.rightChild = null;
					break;
				}
				while(ptr.leftChild != null){
					tmp_parent = ptr;
					ptr = ptr.leftChild;
				}
				if(ptr.rightChild != null){
					root.data = ptr.data; //root 대체
					tmp_parent.leftChild = ptr.leftChild;
					break;
				}
				else{
					root.data = ptr.data;
					tmp_parent.leftChild = null;
					break;
				}
			}
			}

		} //while의 끝 
		 

		return true;
	}

	void PreOrder(TreeNode<T> t)  {
		if(t == null){
			System.out.print("");
		}
		else{
		System.out.print(t.data.GetKey());
		System.out.print("(" + t.data.GetValue() + ")" + " ");
		if(t.leftChild != null)
			PreOrder(t.leftChild);
		if(t.rightChild != null)
			PreOrder(t.rightChild);
		}
	}

	void InOrder(TreeNode<T> t)  {
		if(t == null){
			System.out.print("");
		}
		else{
		if(t.leftChild != null){
			InOrder(t.leftChild);
		}
		System.out.print(t.data.GetKey());
		System.out.print("(" + t.data.GetValue() + ")" + " ");
		if(t.rightChild != null)
			InOrder(t.rightChild);
	}
	}

	void PostOrder(TreeNode<T> t)  {
		if(t == null){
			System.out.print("");
		}
		else{
		if(t.leftChild != null)
			PostOrder(t.leftChild);	
		if(t.rightChild != null)
			PostOrder(t.rightChild);
		System.out.print(t.data.GetKey());
		System.out.print("(" + t.data.GetValue() + ")" + " ");
		}
	}

	int Count(TreeNode<T> t)  {
		if(t.leftChild != null)
			Count(t.leftChild);
		if(t.rightChild != null)
			Count(t.rightChild);
		root.cnt += 1;
		return root.cnt;
		
	}
	
	int Height(TreeNode<T> t)  {
			if(t == null) { // null이면 -1 
				return -1;
			} 
		else{
			int leftHeight = Height(t.leftChild) + 1;
			int rightHeight = Height(t.rightChild) + 1;
			return (leftHeight > rightHeight ? leftHeight: rightHeight);

		}
	}
}


