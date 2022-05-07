import java.lang.reflect.Array;
import java.util.*;

// Name :
// Student ID :


class HeapSort {
	int [] heap;	// Heap Array
	int heapSize;	// number of elements in the Heap
	int last;
	HeapSort(int cap) {
		heap = new int[cap + 1];
		heapSize = 0;
	}

	public String toString() { 
		// Convert heap array into a string
		String str;
		str = "Heap : - ";

		for(int i = 1; i <= heapSize; i++)
			str +=  heap[i] + "  ";

		return str;
	}

	void  Init(int [] es, int n) {	
		// fill the heap array by the input
		// we need to create heap structure when we call Sort()
		heapSize = n;
		heap = Arrays.copyOf(es, es.length);
	}

	void  Adjust(int root, int n) {	
		// adjust binary tree with root "root" to satisfy heap property.
		// The left and right subtrees of "root"  already satisfy the heap
		// property. No node index is > n.
		int tmp;
		int parent = root; //가장 maxheap이 완성되지않을 확률이 높은 노드	
		int leftchild = 2 * parent; 
		int rightchild = 2 * parent + 1;
		if (rightchild <= n){ // rightchild가 있음
			if(heap[parent] <= heap[leftchild] && heap[rightchild] <= heap[leftchild]){ //leftchild가 가장 크면
				tmp = heap[parent];
				heap[parent] = heap[leftchild];
				heap[leftchild] = tmp;
				Adjust(root * 2, n);
			}
			else if(heap[parent] <= heap[rightchild] && heap[leftchild] <= heap[rightchild]){ //rightchild가 더 크면
				tmp = heap[parent];
				heap[parent] = heap[rightchild];
				heap[rightchild] = tmp;
				Adjust(root * 2 + 1, n);
			}
		}
		else if (heap[parent] <= heap[leftchild] && leftchild <= n){// leftchild만 있음, left가 가장큼
				tmp = heap[parent];
				heap[parent] = heap[leftchild];
				heap[leftchild] = tmp;
				Adjust(root* 2, n);
				}
			} 
		

	void  Sort() {	
		// sort heap[1:n] into nondecreasing order
		//	"NEED TO IMPLEMENT"
		int tmp; 
		for(int c = heapSize; c > 0 ;c--){
			for(int t = c/2; t > 0; t--){
				Adjust(t, c); //2, 5
			}
			System.out.println(this);
			tmp = heap[1];
			heap[1] = heap[c];
			heap[c] = tmp;
		}


	}
}

