package Chapter4_Tree;

class IntNode{
    private int data;
    private IntNode left;
    private IntNode right;

    public int getLeftSize() {
        return leftSize;
    }

    private int leftSize;

    public int getData() {
        return data;
    }
    public void setData(int data) {
        this.data = data;
    }
    public IntNode getLeft() {
        return left;
    }
    public void setLeft(IntNode left) {
        this.left = left;
    }
    public IntNode getRight() {
        return right;
    }
    public void setRight(IntNode right) {
        this.right = right;
    }
    public String toString(){
        return String.valueOf(data);
    }
    public IntNode getLeftMost(){
        if(left == null)
            return this;
        else
            return left.getLeftMost();
    }
    public IntNode getRightMost(){
        if(right == null)
            return this;
        else
            return right.getRightMost();
    }
    public IntNode removeLeftmost(){
        if(left == null)  //得到最左结点
            return right;
        else { //递归调用删除自己左孩子的最左面的结点
            left = left.removeLeftmost();
            return this;
        }
    }
    public IntNode removeRightmost(){
        if(right == null)  //得到最左结点
            return left;
        else { //
            right = right.removeRightmost();
            return this;
        }
    }

    public IntNode(int data, IntNode left, IntNode right) {
        this.data = data;
        this.left = left;
        this.right = right;
        leftSize = treeSize(left);
    }

    public IntNode(int data){
        this(data,null,null);
    }

    public IntNode insert(int element, IntNode node){
        if(node == null)
            node = new IntNode(element);
        else {
            if (element < node.data)
                node.left = insert(element, node.left);
            else
                node.right = insert(element, node.right);
        }
        return node;

    }
    public boolean isLeaf(){return left==null && right==null;}
    public void print(){
        IntNode cursor = this;
        if(cursor.left!=null) cursor.left.print();
        System.out.print(cursor.data+" ");
        if(cursor.right!=null) cursor.right.print();
    }

    public static int treeSize(IntNode root){
        if(root == null)
            return 0;
        else
            return 1 + treeSize(root.left) +treeSize(root.right);
    }
    public void renewLeftSize(){
        leftSize = treeSize(left);
        if(left!=null)
            left.renewLeftSize();
        if(right!=null)
            right.renewLeftSize();
    }
}
