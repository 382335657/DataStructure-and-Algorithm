package Chapter6_Graph;

public class BTNode<E> {
    private E data;
    private BTNode<E> left;
    private BTNode<E> right;

    public BTNode(E initialData,
                  BTNode<E> initialLeft,
                  BTNode<E> initialRight){
        data = initialData;
        left = initialLeft;
        right = initialRight;
    }

    public E getData() { return data; }
    public void setData(E data) { this.data = data; }
    public BTNode<E> getLeft() { return left; }
    public void setLeft(BTNode<E> left) { this.left = left; }
    public BTNode<E> getRight() { return right; }
    public void setRight(BTNode<E> right) { this.right = right; }

    //测试一个结点是否是叶结点
    public boolean isLeaf(){
        return (left==null) && (right==null);
    }

    //存取方法，从本结点开始取得树的最左结点的数据
    public E getLeftmostData(){
        if(left == null)
            return data;
        else
            return left.getLeftmostData();
    }

    public E getRightmostData(){
        if(right == null)
            return data;
        else
            return right.getRightmostData();
    }

    //删除最左结点
    //调用方法： 如果n是一个指向数的某个结点的引用，并且有一个右孩子
                // n.setRight(n.getRight().removeLeftMost());
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
    //non-recursive algorithm
    public void inorder(){

    }

    public void preorderPrint(){
        System.out.println(data);
        if(left != null)
            left.preorderPrint();
        if(right != null)
            right.preorderPrint();
    }

    public void inorderPrint(){
        if(left != null)
            left.inorderPrint();
        System.out.println(data);
        if(right != null)
            right.inorderPrint();
    }

    public void postorderPrint(){
        if(left != null)
            left.postorderPrint();
        if(right != null)
            right.postorderPrint();
        System.out.println(data);
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

        //打印右子树（如果有左孩子但没有右孩子，则打印短划线）
        if(right !=null)
            right.print(depth+1);
//        else if(left!=null){
//            for(i=1; i<=depth+1; i++)
//                System.out.print("  ");
//            System.out.println("--");
//        }
    }

    public static void main(String[] args){
        BTNode<Integer> root;
        BTNode<Integer> child;

        root = new BTNode<>(14, null, null);
        root.setLeft(new BTNode<>(17, null, null));
        root.setRight(new BTNode<>(11,null,null));
        root.left.setLeft(new BTNode<>(9,null,null));
        root.left.setRight(new BTNode<>(53,null,null));
        root.left.left.setRight(new BTNode<>(13,null,null));

        root.print(0);
        System.out.println(treeHeight(root));
    }
}
