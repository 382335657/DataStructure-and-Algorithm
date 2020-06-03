package Chapter4_Tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BTNode<E> {
    private E data;
    private BTNode<E> left;
    private BTNode<E> right;

    public BTNode(E initialData, BTNode<E> initialLeft, BTNode<E> initialRight){
        data = initialData;
        left = initialLeft;
        right = initialRight;
    }

    public BTNode(E initialData){
        this(initialData,null,null);
    }

    public E getData() { return data; }
    public BTNode<E> getLeft() { return left; }
    public BTNode<E> getRight() { return right; }

    //测试一个结点是否是叶结点
    public boolean isLeaf(){
        return (left==null) && (right==null);
    }
    public int countLeaf(BTNode root){
        if(root == null)
            return 0;
        else if(root.isLeaf())
            return 1;
        else
            return countLeaf(root.left)+countLeaf(root.right);
    }

    //交换左子女和右子女
    public void exchange(BTNode root){
        if(root == null || root.isLeaf())
            return;
        else{
            BTNode temp = root.left;
            root.left = root.right;
            root.right = temp;
        }
        exchange(root.left);
        exchange(root.right);
    }

    //存取方法，从本结点开始取得树的最左结点的数据
    public E getLeftmostData(){
        if(left == null)
            return data;
        else
            return left.getLeftmostData();
    }
    //存取方法，从本结点开始取得树的最右结点的数据
    public E getRightmostData(){
        if(right == null)
            return data;
        else
            return right.getRightmostData();
    }

    /**
     * 删除最左结点
     * @return
     * 调用方法：如果n是一个指向数的某个结点的引用，并且有一个右孩子
     *              n.setRight(n.getRight().removeLeftMost());
     */
    public BTNode<E> removeLeftmost(){
        if(left == null)  //得到最左结点
            return right;
        else { //递归调用删除自己左孩子的最左面的结点
            left = left.removeLeftmost();
            return this;
        }
    }
    public BTNode<E> removeRightmost(){
        if(right == null)  //得到最左结点
            return left;
        else { //
            right = right.removeRightmost();
            return this;
        }
    }

    //复制二叉树
    public static <E> BTNode<E> treeCopy(BTNode<E> source){
        BTNode<E> leftCopy, rightCopy;

        if(source == null)
            return null;
        else{
            leftCopy = treeCopy(source.left);
            rightCopy = treeCopy(source.right);
            return new BTNode<E>(source.data, leftCopy, rightCopy);
        }
    }

    //non-recursive algorithm print
    public void levelorder(){
        Queue<BTNode> queue = new LinkedList<>();
        BTNode cursor = this;
        while(cursor!= null){
            System.out.print(cursor.data+" ");
            if(cursor.left!=null)
                queue.add(cursor.left);
            if(cursor.right!=null)
                queue.add(cursor.right);
            try{
                cursor = queue.poll();
            }catch(Exception e){return;}
        }
    }
    public void inorder(){
        BTNode cursor = this;
        Stack<BTNode> stack = new Stack<>();
        for(;;){
            while(cursor!=null){
                stack.push(cursor);
                cursor = cursor.left;
            }//不断地进入左树直到叶结点
            if(!stack.isEmpty()){
                cursor = stack.pop();
                System.out.print(cursor.data+" ");
                cursor = cursor.right;
            }//先打印中间，再延伸到右子树
            else return;
        }
    }

    //recursive algorithm print
    public void preorderPrint(){
        System.out.print(data+" ");
        if(left != null)
            left.preorderPrint();
        if(right != null)
            right.preorderPrint();
    }
    public void inorderPrint(){
        if(left != null)
            left.inorderPrint();
        System.out.print(data+" ");
        if(right != null)
            right.inorderPrint();
    }
    public void postorderPrint(){
        if(left != null)
            left.postorderPrint();
        if(right != null)
            right.postorderPrint();
        System.out.print(data+" ");
    }

    //recursive algorithm string
    public String inorderPrint(String string){
        if(left!= null)
            string = left.inorderPrint(string);
        string+=this.data;
        string+=" ";
        if(right!=null)
            string = right.inorderPrint(string);
        return string;
    }

    //make a tree
    public static BTNode<String> makeTree(BTNode<String> root, String preorder, String inorder){
        if(preorder.length()==0)
            root = null;
        else {
            root = new BTNode(preorder.substring(0, 1), null, null);
            int index = inorder.indexOf(root.data);
            root.left = makeTree(root.left, preorder.substring(1, index + 1), inorder.substring(0, index));
            root.right = makeTree(root.right, preorder.substring(index + 1), inorder.substring(index + 1));
        }
        return root;
    }

    public static <E> int treeSize(BTNode<E> root){
        if(root == null)
            return 0;
        else
            return 1 + treeSize(root.left) +treeSize(root.right);   
    }
    public static <E> int treeHeight(BTNode<E> root){
        if(root == null)
            return 0;
        else{
            int HL = treeHeight(root.left);
            int HR = treeHeight(root.right);
            return ((HL>HR)?HL:HR)+1;//取左右树的最大高度加一
        }
    }
    public void print(int depth){
        int i;
        //打印缩进和当前结点中的数据
        for(i=1;i<=depth;i++)
            System.out.print("  ");
        System.out.println(data);

        //打印左子树（如果有右孩子但没有左孩子，则打印短划线
        if(left != null)
            left.print(depth+1);
        else if(right!=null){
            for(i=1;i<=depth+1;i++)
                System.out.print("  ");
            System.out.println("--");
       }

        //打印右子树
        if(right !=null)
            right.print(depth+1);

    }

    //将表达式括号化
    public String expression(String string){
        if(this.data.equals("+") || this.data.equals("-"))
            string +="(";
        if(left!=null)
            string = left.expression(string);
        string+=this.data;
        if(right!=null)
            string = right.expression(string);

        if(this.data.equals("+") || this.data.equals("-"))
            string+=")";
        return string;
    }

    public static void main(String[] args){
        BTNode<String> root;
        root = makeTree(null,"ABDCEGFHI","DBAEGCHFI");

        System.out.print("PreOrder:   ");
        root.preorderPrint();
        System.out.println();

        System.out.print("InOrder:    ");
        root.inorderPrint();
        System.out.println();
        root.inorder();

        System.out.print("PostOrder:  ");
        root.postorderPrint();
        System.out.println();

        System.out.print("LevelOrder: ");
        root.levelorder();
    }
}
