package Chapter4_Tree;

import static java.lang.Math.max;

public class AVLTree {
    AVLNode root;
    public AVLTree(){
        root = new AVLNode(3);
    }
    public void addALL(int[] elements){
        for(int i=0;i<elements.length;i++)
            add(elements[i]);
    }
    public void add(int element) {
        root = AVLNode.insert(element,root);
    }

    public static void main(String[] args){
        //int[] numbers = {2,1,4,5,6,7,10,9,8};
        int[] numbers = {16,3,7,11,9,28,18,14,15};
        AVLTree tree = new AVLTree();
        tree.addALL(numbers);
    }
}

class AVLNode {
    private int data;
    private AVLNode left;
    private AVLNode right;
    private int height;

    public AVLNode(int element){
        this(element,null,null);
    }
    public AVLNode(int element, AVLNode left, AVLNode right){
        this.data = element;
        this.left = left;
        this.right = right;
        this.height = max(height(left),height(right))+1;
    }
    public String toString(){
        return data+"("+height+")";
    }

    public static AVLNode insert(int x, AVLNode t){
        if(t== null)
            t = new AVLNode(x,null,null);
        else if(x<t.data){
            t.left = insert(x,t.left);
            //插入后计算子树的高度,等于2则需要重新恢复平衡,由于是左边插入,左子树的高度肯定大于等于右子树的高度
            if(height(t.left)-height(t.right)==2)
                if(x<t.left.data)
                    t = rightRotate(t);   //插在左外侧 只需右单旋
                else t = leftRightRotate(t);  //插在左内侧 左右双旋
        }
        else if(x>t.data){
            t.right = insert(x,t.right);
            if(height(t.right)-height(t.left)==2)
                if(x>t.right.data)
                    t = leftRotate(t);  //插在右外侧 只需左单旋
                else t = rightLeftRotate(t);  //插在右内侧 右左双旋
        }
        else ;
        //重新计算各个结点的高度
        t.height = max(height(t.left),height(t.right))+1;
        return t;  //返回根结点
    }


    public AVLNode delete(){
        //todo
        return null;
    }

    //左旋
    public static AVLNode leftRotate(AVLNode k2){
        AVLNode k1 = k2.right;
        k2.right = k1.left;
        k1.left = k2;
        k2.height = max(height(k2.left),height(k2.right))+1;
        k1.height = max(height(k1.left),height(k1.right))+1;
        return k1;
    }
    //右旋
    public static AVLNode rightRotate(AVLNode k2){
        AVLNode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = max(height(k2.left),height(k2.right))+1;
        k1.height = max(height(k1.left),height(k1.right))+1;
        return k1;
    }
    //右左双旋
    public static AVLNode rightLeftRotate(AVLNode k3){
        k3.right = rightRotate(k3.right);
        return leftRotate(k3);
    }
    //左右双旋
    public static AVLNode leftRightRotate(AVLNode k3){
            k3.left = leftRotate(k3.left);
            return rightRotate(k3);
        }


    private static int height(AVLNode root) {
        return root==null? -1:root.height;
    }
    public static void print(AVLNode root){
        if(root.left!=null)
            print(root.left);
        System.out.print(root.data+" ");
        if(root.right!=null)
            print(root.right);
    }
}