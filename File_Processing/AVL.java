import java.net.http.HttpResponse.ResponseInfo;



public class AVL {
    class TreeNode {
        TreeNode leftchild;
        TreeNode rightchild;
        TreeNode address; //그 노드의 부모노드
        int key, bf;
        String rotationType;

        TreeNode(){
            leftchild = rightchild = address = null;
            bf = 0;
            key = 0;
            rotationType = "NO";
        }
    }
    TreeNode root = new TreeNode();
    private TreeNode getNode(){
        return new TreeNode();
    }
    
    AVL(){
        root = null;
    }

    void inorderBST(TreeNode tree){
        if(tree == null){
            return;
        }
        if(tree.leftchild != null){
            inorderBST(tree.leftchild);
        }
        System.out.print(tree.key + " ");
        if(tree.rightchild != null){
            inorderBST(tree.rightchild);
        }
    }
    TreeNode rotateTree(TreeNode tree,String rotationType){
        if(rotationType.equals("LL")){
            TreeNode newroot = tree.leftchild;
            tree.leftchild = newroot.rightchild;
            newroot.rightchild = tree;

            newroot.bf = 0; tree.bf = 0;
            return newroot;

        }
        else if(rotationType.equals("RR")){
            TreeNode newroot = tree.rightchild;
            tree.rightchild = newroot.leftchild;
            newroot.leftchild = tree;

            newroot.bf = 0; tree.bf = 0;
            return newroot;

        }
        return tree;
            }          
        
    

    TreeNode checkBalance(TreeNode p){
        
        TreeNode ptr = p;
        ptr.bf = height(ptr.leftchild) - height(ptr.rightchild);
        if(ptr.bf == 2){
            if(height(ptr.leftchild.leftchild) - height(ptr.leftchild.rightchild) == 1){ //LL
                ptr = rotateTree(ptr, "LL");
                ptr.rotationType = "LL";
                root.rotationType = "LL";
                return ptr;
            }
            else if(height(ptr.leftchild.leftchild) - height(ptr.leftchild.rightchild) == -1){ //LR
                ptr.leftchild = rotateTree(ptr.leftchild, "RR");
                ptr = rotateTree(ptr, "LL");
                ptr.rotationType = "LR";
                root.rotationType = "LR";
                return ptr;
            }
        }
        else if(ptr.bf == -2){ //RR
            if(height(ptr.rightchild.leftchild) - height(ptr.rightchild.rightchild) == -1){
                ptr = rotateTree(ptr, "RR");
                ptr.rotationType = "RR";
                root.rotationType = "RR";
                return ptr;
            }
            else if(height(ptr.rightchild.leftchild) - height(ptr.rightchild.rightchild) == 1) { //RL
                ptr.rightchild = rotateTree(ptr.rightchild, "LL");
                ptr = rotateTree(ptr, "RR");
                ptr.rotationType = "RL";
                root.rotationType = "RL";
                return ptr;
            }
        }
        
        if(p.leftchild != null){
            p.leftchild = checkBalance(ptr.leftchild);
        }
        if(p.rightchild != null){   
            p.rightchild = checkBalance(ptr.rightchild);
        }
        return ptr;
    }

    String rotationType(){
        if(root == null){
            return "NO";
        }
        else{
            return root.rotationType;
        }
    }


    void insertAVL(TreeNode tree, int newkey){
        root = insertBST(tree, newkey);
    }

    TreeNode insertBST(TreeNode tree, int newkey){
        TreeNode ptr = tree;
        if(root == null){ //root 노드 삽입
           root = getNode();
           root.key = newkey;
           return root;
        }
        if(ptr == null){
            ptr = getNode();
            ptr.key = newkey;
            root.rotationType = "NO";
            return ptr;
        }
        if(newkey == ptr.key){ // 넣으려는 키 값과 기존에 있던 키 값이 같다면 delete
            ptr = deleteAVL(ptr, newkey);
            return ptr;
         }
        else if(newkey < ptr.key){ // 넣으려는 키 값이 현재 p의 키 값보다 작다면 p를 leftchild 노드로 이동 
            ptr.leftchild = insertBST(ptr.leftchild, newkey);
            ptr = checkBalance(ptr);
            return ptr;
         }
        else{
            ptr.rightchild = insertBST(ptr.rightchild, newkey);
            ptr = checkBalance(ptr);
            return ptr;
            }
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
    
    TreeNode deleteAVL(TreeNode tree, int delkey){
        TreeNode ptr = tree;
        TreeNode tmp;
        if(noNodes(root) == 1){ // 노드가 단 하나 
            root = null;
        }

        if(ptr.key > delkey){
            ptr.leftchild = deleteAVL(ptr.leftchild, delkey);
            ptr = checkBalance(ptr);
        }
        else if(ptr.key < delkey){
            ptr.rightchild = deleteAVL(ptr.rightchild, delkey);
            ptr = checkBalance(ptr);
        }
        else{
            if(ptr.leftchild == null && ptr.rightchild == null){
                ptr = null;
                if(root != null)
                    root.rotationType = "NO";
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
                ptr.key = tmp.key;
                if(flag){//right
                    ptr.rightchild = deleteAVL(ptr.rightchild, tmp.key);
                    ptr = checkBalance(ptr);
                }
                else{
                    ptr.leftchild = deleteAVL(ptr.leftchild, tmp.key);
                    ptr = checkBalance(ptr);
                }
            }
        }
        return ptr;
            

    }
    

    public static void main(String[] argv){
        AVL test = new AVL();
       //삽입
        test.insertAVL(test.root,40);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,11);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,77);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,33);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,20);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,90);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,99);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,70);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,88);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,80);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,66);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        
        test.insertAVL(test.root,10);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,22);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,30);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,44);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,55);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,50);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,60);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,25);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,49);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        //삭제
        test.insertAVL(test.root,40);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,11);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,77);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,33);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,20);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,90);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,99);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,70);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,88);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,80);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,66);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,10);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,22);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,30);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,44);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,55);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,50);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,60);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,25);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,49);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        //삽입
        test.insertAVL(test.root,40);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,11);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,77);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,33);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,20);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,90);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,99);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,70);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,88);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,80);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,66);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,10);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,22);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,30);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,44);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,55);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,50);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,60);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,25);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,49);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        //삭제
        test.insertAVL(test.root,40);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,11);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,77);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,33);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,20);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,90);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,99);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,70);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,88);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,80);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,66);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,10);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,22);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,30);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,44);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,55);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,50);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,60);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,25);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        test.insertAVL(test.root,49);System.out.print(test.rotationType() + " ");test.inorderBST(test.root);System.out.println();
        
        

    }
}
