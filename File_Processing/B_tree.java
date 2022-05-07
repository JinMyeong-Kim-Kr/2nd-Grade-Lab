import java.beans.beancontext.BeanContext;
import java.util.*;

public class B_tree {
    class B_node{
        int n, m;
        List<Integer> key;
        B_node address;
        List<B_node> ptr;
        B_node(int size){
            m = size;
            n = 0;
            key = new ArrayList<>();
            ptr = new ArrayList<>();
        }
    }
    B_node root;
    B_tree(){
        root = null;
    }
    B_node getBTNode(int m){
        return new B_node(m);
    }
    void inorderBT(B_node tree, int m){
        if(root == null){
            return;
        }
        for(int q = 0; q < tree.ptr.size(); q++){ 
            // shift down until terminal node
            if(tree.ptr.get(q) != null){ 
                inorderBT(tree.ptr.get(q), m);
                // if most right key,end inorderBT 
                if(q == tree.ptr.size() - 1){ 
                    return;
                }
                // print self key
                else{
                    System.out.print(tree.key.get(q)+ " "); 
                }
            }
        }      
         // print terminal node
            for(int p = 0; p < tree.key.size(); p++){
                 System.out.print(tree.key.get(p)+ " ");
            }
       
    }


    void insertBT(B_node tree, int m, int newKey){
        Stack<B_node> nodestack = new Stack<>();
        Stack<Integer> idxstack = new Stack<>();
        B_node current = tree; // current tree pointer
        B_node y = null;
        
        if(root == null){
            root = getBTNode(m);
            root.key.add(0, newKey);
            root.ptr.add(0, null);
            root.ptr.add(1, null);
            root.n = 1;
            return;
        }

        while(current != null){ // terminal node
            int i = Collections.binarySearch(current.key, newKey);
            if(i > -1) { // Already exist key return positive number
                return;
            }
            i = (i+1)*-1; // ex) {1,3,5} <<4 = -3 return so (-3 + 1) * -1 = 2 , p[2]
            nodestack.push(current);
            idxstack.push(i);
            
            current = current.ptr.get(i);
        }

        while(!nodestack.empty()){
            current = nodestack.peek();
            int idx = idxstack.peek();
            nodestack.pop();
            idxstack.pop();
            if(current.n < m - 1){    //
                int i = Collections.binarySearch(current.key, newKey);
                current.key.add((i+1) * -1, newKey);
                current.n += 1;
                if(y != null){
                    current.ptr.add((i) * -1, y);
                    return;
                }
                current.ptr.add((i+1) * -1, null); // ptr add 
                return;
            }

            //DO SPLIT
            
            // TMPNODE 
            B_node tempNode = getBTNode(m+1);
            tempNode.key.addAll(current.key);
            tempNode.ptr.addAll(current.ptr);
            tempNode.key.add(idx, newKey);
            tempNode.ptr.add(idx+1, y);
            
            // WILL RIGHT NODE

            y = getBTNode(m);

            current.key.clear(); // clear and add first half tempnode
            current.ptr.clear();
            current.n = 0;

            for(int k = 0; k < (m-1)/2; k++){ // first half 
                current.key.add(tempNode.key.get(k));
                current.ptr.add(tempNode.ptr.get(k));
                current.n += 1;
                if(k == (m-1)/2 - 1){
                    // insert null at current.ptr[lastindex] (To avoid nullpoint error)
                    current.ptr.add(tempNode.ptr.get(k+1)); 
                }
            }
            for(int j = (m+1)/2; j < m; j++){ // second half
                y.key.add(tempNode.key.get(j));
                y.ptr.add(tempNode.ptr.get(j));
                y.n += 1;
                if(j == m - 1){
                    // insert null at y.ptr[lastindex] (To avoid nullpoint error)
                    y.ptr.add(tempNode.ptr.get(j+1)); 
                }
            }
            newKey = tempNode.key.get((m-1)/2);
        }
        // root has been splited
        root = getBTNode(m);
        root.key.add(0, newKey);
        root.ptr.add(0, current);
        root.ptr.add(1, y);
        root.n = 1;
        
    }

    void deleteBT(B_node tree, int m, int oldKey){
        Stack<B_node> nodestack = new Stack<>();
        Stack<Integer> idxstack = new Stack<>();
        int idx;
        // current tree pointer
        B_node current = tree;
        
        if(root == null){
            return;
        }
        // Shift Down
        while(current != null){ 
            // idx = binarySearch
            idx = Collections.binarySearch(current.key, oldKey);
            if(idx <= -1){
                // ex) if {66,80}, binarySearch 70, will return idx = -2 so target idx = 1
                idx = (idx+1)*-1;
            }
            if(idx < current.key.size() && current.key.get(idx) == oldKey){ // success find oldkey 
                break;
            }
            nodestack.push(current);
            idxstack.push(idx);
            current = current.ptr.get(idx);
            
         }//end while 
        if(current == null){ // oldKey Not Found
            return;
        }

        // if oldKey was found in internal node,
        // exchange key with successor and delete successor
        B_node internalNode = current;
        idx = Collections.binarySearch(current.key, oldKey);
        if(current.ptr.get(idx) != null){ //if internal node
            internalNode = current;
            nodestack.push(current);
            idxstack.push(idx);
            current = current.ptr.get(idx+1);
            while(current != null){ //to exchange key with successor, shift down terminal node 
                nodestack.push(current); 
                idxstack.push(0); 
                current = current.ptr.get(0);
            }
            // exchange current, internalNode
            current = nodestack.peek();
            int tmp = current.key.get(0);
            current.key.add(0, internalNode.key.get(idx));
            current.key.remove(1);
            internalNode.key.add(idx,tmp);
            internalNode.key.remove(idx+1);
            nodestack.pop();

            idx = idxstack.peek();
            idxstack.pop();
        }
        // delete target key, define n 
         current.key.remove(idx); 
         current.n = current.key.size(); 

         while(!nodestack.isEmpty()){
             // No underflow
             if(current.n >= (m-1)/2){ 
                return;
             }

            // Occur underflow

            // currentNode's parent node
            B_node parent = nodestack.peek(); 
            idx = idxstack.peek();
            nodestack.pop();
            idxstack.pop();
            
            // Choose bestSibling 

            B_node bestSibling;
            String caseof = "";
            // Most Left ptr Node = current, then Sibling is 1
            if(parent.ptr.get(0) == current){ 
                caseof = "ml";
                bestSibling = parent.ptr.get(1);
            } 
            // Most right ptr Node = current,then Sibling is parent.n - 1
            else if(parent.key.size() == idx){ 
                caseof = "mr";
                bestSibling = parent.ptr.get(parent.key.size()-1);
            }
            // bestSibilng is current's right
            else if(parent.ptr.get(idx+1).n > parent.ptr.get(idx).n){ // 오른쪽 형제의 키 개수가 더 많은 경우
                caseof = "right";
                bestSibling = parent.ptr.get(idx+1); //오른쪽 선택
            }
            // bestSibilng is current's left
            else{
                caseof = "left";
                bestSibling = parent.ptr.get(idx);
            }

            // case key redistribution 

            if(bestSibling.n > (m-1)/2){ 
                // case current is most right
               if(caseof.equals("mr")){ 
                    current.key.add(0,parent.key.get(idx - 1)); 
                    current.ptr.add(0, bestSibling.ptr.get(bestSibling.key.size())); 
                    current.n = current.key.size();

                    parent.key.add(parent.key.size() - 1, bestSibling.key.get(bestSibling.key.size()-1)); 
                    parent.key.remove(parent.key.size() - 1); 

                    bestSibling.key.remove(bestSibling.key.size() - 1);
                    bestSibling.ptr.remove(bestSibling.ptr.size() - 1);
                    bestSibling.n = bestSibling.key.size();
                }
                // case current is most left
                else if(caseof.equals("ml")){
                    current.key.add(parent.key.get(0)); 
                    current.ptr.add(bestSibling.ptr.get(0)); 
                    current.n= current.key.size();

                    parent.key.add(0,bestSibling.key.get(0)); 
                    parent.key.remove(1); 

                    bestSibling.key.remove(0);
                    bestSibling.ptr.remove(0);
                    bestSibling.n = bestSibling.key.size();
                }
                // case current in middle, bestSibling is right
                else if(caseof.equals("right")){ // sibiling이 오른쪽
                    current.key.add(parent.key.get(idx)); 
                    current.ptr.add(current.key.size(),bestSibling.ptr.get(0)); 
                    current.n= current.key.size();

                    parent.key.add(idx,bestSibling.key.get(0)); //0값을 parent로
                    parent.key.remove(idx+1); 

                    bestSibling.key.remove(0);
                    bestSibling.ptr.remove(0);
                    bestSibling.n = bestSibling.key.size();


                }
                // case current in middle, bestSibling is left
                else if(caseof.equals("left")){ //sibilng이 왼쪽
                    current.key.add(0, parent.key.get(idx)); 
                    current.ptr.add(0, bestSibling.ptr.get(bestSibling.key.size())); 
                    current.n= current.key.size();

                    parent.key.add(idx,bestSibling.key.get(bestSibling.key.size()-1)); //0값을 parent로
                    parent.key.remove(idx+1); 

                    bestSibling.key.remove(bestSibling.key.size()-1);
                    bestSibling.ptr.remove(bestSibling.key.size()+1);
                    bestSibling.n = bestSibling.key.size();

                }
                return;     
            }
 
            // Merge 
            // case current is most right
            if(caseof.equals("mr")){
                bestSibling.key.addAll(current.key);
                bestSibling.key.add(parent.key.get(parent.key.size() - 1));
                bestSibling.ptr.addAll(current.ptr);
                bestSibling.n = bestSibling.key.size();

                // clear current node
                current.key.clear(); 
                current.ptr.clear(); 

                parent.key.remove(parent.key.size() - 1);
                parent.ptr.remove(parent.key.size() + 1);
                parent.n = parent.key.size();

            }
            // case current is most left
            else if(caseof.equals("ml")){ 
                current.key.add(parent.key.get(0));
                current.key.addAll(bestSibling.key);
                current.ptr.addAll(bestSibling.ptr);
                current.n = current.key.size();

                // clear bestsibilng node
                bestSibling.key.clear(); 
                bestSibling.ptr.clear(); 

                parent.key.remove(0);
                parent.ptr.remove(1);
                parent.n = parent.key.size();
            }
            // case current in middle, bestSibling is right
            else if(caseof.equals("right")){
                current.key.add(parent.key.get(idx));
                current.key.addAll(bestSibling.key);
                current.ptr.addAll(bestSibling.ptr);
                current.n = current.key.size();

                // clear bestsibilng node
                bestSibling.key.clear(); // clear bestsibilng.key,
                bestSibling.ptr.clear(); // clear bestsibilng.key,

                parent.key.remove(idx);
                parent.ptr.remove(idx+1);
                parent.n = parent.key.size();


            }
            // case current in middle, bestSibling is left
            else if(caseof.equals("left")){
                bestSibling.key.addAll(current.key);
                bestSibling.key.add(parent.key.get(idx));
                bestSibling.ptr.addAll(current.ptr);
                bestSibling.n = bestSibling.key.size();
                current.key.clear(); // clear bestsibilng.key,
                current.ptr.clear(); // clear bestsibilng.key,

                parent.key.remove(idx);
                parent.ptr.remove(idx+1);
                parent.n = parent.key.size();
            }
            current = parent;
         } //end while

         // Merge new root, level - 1
         if(root.n == 0){
            if(root.ptr.get(0) == null){
                root = null;
                return;
            }
            root = root.ptr.get(0);
         }

         

    }

    public static void main(String[] args) {
        
        int[] insertSequence = {40, 11, 77, 33, 20, 90, 
            99, 70, 88, 80, 66, 10, 22, 
            30, 44, 55, 50, 60, 100, 28,
             18, 9 ,5, 17 ,6 ,3 ,1 ,4 ,2, 
             7 ,8 ,73 ,12, 13, 14 ,16 ,15, 
             25, 24, 28, 45 ,49, 42, 43, 41, 47,
             48, 46, 63 ,68 ,61, 62 ,64 ,69 ,67, 65, 54, 59
             ,58 ,51 ,53 ,57 ,52 ,56 ,83 ,81 ,82 ,84 ,75, 89};
        int[] deleteSequence = {
            66, 10, 22, 30, 44, 55, 50, 60, 100, 28,
             18, 9, 5, 17, 6, 3, 1, 4, 2, 7, 8, 73,
              12, 13, 14, 16, 15, 25, 24, 28, 40, 11,
               77, 33, 20, 90, 99, 70, 88, 80, 45, 49
               , 42, 43, 41, 47, 48, 46, 63, 68, 53, 57,
                52, 56, 83, 81, 82, 84, 75, 89, 61, 62,
                 64, 69, 67, 65, 54, 59, 58, 51
        };
        B_tree tree = new B_tree();
        for(int i : insertSequence){
            tree.insertBT(tree.root, 3, i);tree.inorderBT(tree.root, 3);System.out.println();
        }
        for(int i : deleteSequence){
            tree.deleteBT(tree.root, 3, i);tree.inorderBT(tree.root, 3);System.out.println();
        }
        for(int i : insertSequence){
            tree.insertBT(tree.root, 4, i);tree.inorderBT(tree.root, 3);System.out.println();
        }
        for(int i : deleteSequence){
            tree.deleteBT(tree.root, 4, i);tree.inorderBT(tree.root, 3);System.out.println();
        }
        
    }
}
