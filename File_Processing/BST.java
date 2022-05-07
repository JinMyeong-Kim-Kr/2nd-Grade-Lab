import java.net.http.HttpResponse.ResponseInfo;

public class BST {
    class TreeNode {
        TreeNode leftchild, rightchild, address;
        int data;

        TreeNode(){
            leftchild = rightchild = null;
        }
        TreeNode(int d, TreeNode a){
            data = d;
            address = a;
            leftchild = rightchild = null;
        }
    }
    TreeNode root = new TreeNode();

    private TreeNode getNode(int d, TreeNode a){
        return new TreeNode(d, a);
    }
    
    BST(){
        root = null;
    }

    void inorderBST(TreeNode t){
        if(t == null){
            return;
        }
        if(t.leftchild != null){
            inorderBST(t.leftchild);
        }
        System.out.print(t.data + " ");
        if(t.rightchild != null){
            inorderBST(t.rightchild);
        }
    }
    boolean insertBST(int newkey){
        if(root == null){ //처음 노드 삽입
           root = getNode(newkey, null);
           return true;
        }
        else{
            TreeNode ptr = root;
            TreeNode parent;
            while(ptr != null){
                parent = ptr;
                if(newkey == ptr.data){ // 넣으려는 키 값과 기존에 있던 키 값이 같다면 false
                    return false;
                }
                if(newkey < ptr.data){ // 넣으려는 키 값이 현재 p의 키 값보다 작다면 p를 leftchild 노드로 이동 
                    ptr = parent.leftchild;
                    if(ptr == null){
                        parent.leftchild = getNode(newkey, parent);
                        break;
                    }
                }
                else{
                    ptr = parent.rightchild;
                    if(ptr == null){
                        parent.rightchild = getNode(newkey, parent);
                        break;
                    }
                }
            }
            
        }
        return true;
    }
    //height => 자식트리를 받아서 높이 반환
    int height(TreeNode tree){
        if(tree == null){
            return 0;
        }
        else{
            int left_cnt = height(tree.leftchild);
            int right_cnt = height(tree.rightchild);
            return 1 + (left_cnt > right_cnt ? left_cnt : right_cnt);
        }
    }
    TreeNode maxNode(TreeNode tree){
        TreeNode maxnode = tree;
        while(maxnode.rightchild != null){
            maxnode = maxnode.rightchild;
        }
        return maxnode;
    }
    TreeNode minNode(TreeNode tree){
        TreeNode minnode = tree;
        while(minnode.leftchild != null){
            minnode = minnode.leftchild;
        }
        return minnode;
    }
    int noNodes(TreeNode tree){
        int num = 0;
        if(tree == null){
            return 0;
        }
        if(tree.leftchild != null){
            num += 1;
            return num + noNodes(tree.leftchild);
        }
        if(tree.rightchild != null){
            num += 1;
            return num + noNodes(tree.rightchild);
        }
        return num + 1;
    }
    
    TreeNode deleteBST(TreeNode tree, int delkey){
        TreeNode ptr = tree;
        TreeNode tmp;
        if(noNodes(root) == 1){ // 노드가 단 하나 
            root = null;
        }
        if(ptr.data > delkey){
            ptr.leftchild = deleteBST(ptr.leftchild, delkey);
        }
        else if(ptr.data < delkey){
            ptr.rightchild = deleteBST(ptr.rightchild, delkey);
        }
        else{
            if(ptr.leftchild == null && ptr.rightchild == null){
                ptr = null;
            }
            else if(ptr.leftchild == null || ptr.rightchild == null){
                if(ptr.leftchild != null){ // 자식 노드 1, left 존재
                    if(ptr.address == null){
                        root = ptr.leftchild;
                        root.address = null;
                        return root;
                    }
                    else{
                    ptr = ptr.leftchild;
                    }
                }
                else if(ptr.rightchild != null){
                    if(ptr.address == null){
                        root = ptr.rightchild;
                        root.address = null;
                        return root;
                    }
                    else{
                        ptr = ptr.rightchild;
                    }
                }
            }
            else if(ptr.leftchild != null && ptr.rightchild != null){ // 자식 노드 2
                boolean flag; // left = true, right = false
                if(height(ptr.leftchild) > height(ptr.rightchild)){ // 높이가 왼쪽이 더 큰 경우
                    tmp = maxNode(ptr.leftchild);
                    flag = false;
                }
                else if(height(ptr.leftchild) < height(ptr.rightchild)){ // 높이가 오른쪽이 더 큰 경우
                    tmp = minNode(ptr.rightchild);
                    flag = true;
                }
                else{ //높이가 같은 경우
                    if(noNodes(ptr.leftchild) >= noNodes(ptr.rightchild)){
                        tmp = maxNode(ptr.leftchild);
                        flag = false;
                    }
                    else{
                        tmp = minNode(ptr.rightchild);
                        flag = true; 
                         
                    }
                }
                ptr.data = tmp.data;
                if(flag){//right
                    ptr.rightchild = deleteBST(ptr.rightchild, tmp.data);
                }
                else{
                    ptr.leftchild = deleteBST(ptr.leftchild, tmp.data);
                }
            }
        }
        return ptr;
            

    }
    

    public static void main(String[] argv){
        BST test = new BST();

        test.insertBST(25);test.inorderBST(test.root);System.out.println();
        test.insertBST(500);test.inorderBST(test.root);System.out.println();
        test.insertBST(33);test.inorderBST(test.root);System.out.println();
        test.insertBST(49);test.inorderBST(test.root);System.out.println();
        test.insertBST(17);test.inorderBST(test.root);System.out.println();  
        test.insertBST(403);test.inorderBST(test.root);System.out.println();
        test.insertBST(29);test.inorderBST(test.root);System.out.println(); 
        test.insertBST(105);test.inorderBST(test.root);System.out.println();
        test.insertBST(39);test.inorderBST(test.root);System.out.println();
        test.insertBST(66);test.inorderBST(test.root);System.out.println();
        test.insertBST(305);test.inorderBST(test.root);System.out.println();
        test.insertBST(44);test.inorderBST(test.root);System.out.println();
        test.insertBST(19);test.inorderBST(test.root);System.out.println();
        test.insertBST(441);test.inorderBST(test.root);System.out.println();
        test.insertBST(390);test.inorderBST(test.root);System.out.println();
        test.insertBST(12);test.inorderBST(test.root);System.out.println();
        test.insertBST(81);test.inorderBST(test.root);System.out.println();
        test.insertBST(50);test.inorderBST(test.root);System.out.println();
        test.insertBST(100);test.inorderBST(test.root);System.out.println();
        test.insertBST(999);test.inorderBST(test.root);System.out.println();

        test.deleteBST(test.root, 25);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 500);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 33);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 49);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 17);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 403);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 29);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 105);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 39);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 66);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 305);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 44);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 19);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 441);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 390);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 12);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 81);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 50);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 100);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 999);test.inorderBST(test.root);System.out.println();

        test.insertBST(25);test.inorderBST(test.root);System.out.println();
        test.insertBST(500);test.inorderBST(test.root);System.out.println();
        test.insertBST(33);test.inorderBST(test.root);System.out.println();
        test.insertBST(49);test.inorderBST(test.root);System.out.println();
        test.insertBST(17);test.inorderBST(test.root);System.out.println();  
        test.insertBST(403);test.inorderBST(test.root);System.out.println();
        test.insertBST(29);test.inorderBST(test.root);System.out.println(); 
        test.insertBST(105);test.inorderBST(test.root);System.out.println();
        test.insertBST(39);test.inorderBST(test.root);System.out.println();
        test.insertBST(66);test.inorderBST(test.root);System.out.println();
        test.insertBST(305);test.inorderBST(test.root);System.out.println();
        test.insertBST(44);test.inorderBST(test.root);System.out.println();
        test.insertBST(19);test.inorderBST(test.root);System.out.println();
        test.insertBST(441);test.inorderBST(test.root);System.out.println();
        test.insertBST(390);test.inorderBST(test.root);System.out.println();
        test.insertBST(12);test.inorderBST(test.root);System.out.println();
        test.insertBST(81);test.inorderBST(test.root);System.out.println();
        test.insertBST(50);test.inorderBST(test.root);System.out.println();
        test.insertBST(100);test.inorderBST(test.root);System.out.println();
        test.insertBST(999);test.inorderBST(test.root);System.out.println();

        test.deleteBST(test.root, 999);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 100);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 50);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 81);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 12);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 390);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 441);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 19);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 44);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 305);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 66);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 39);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 105);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 29);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 403);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 17);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 49);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 33);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 500);test.inorderBST(test.root);System.out.println();
        test.deleteBST(test.root, 25);test.inorderBST(test.root);System.out.println();
        
    }
}
