package Chapter4_Tree;

public class ThreadTree {
    private Node preNode;

    static class Node{
        String data;
        Node left;
        Node right;
        boolean isLeftThread = false;
        boolean isRightThread = false;

        Node(String data){
            this.data = data;
        }
    }

    static Node createBinaryTree(String[] array, int index) {
        Node node = null;
        if(index < array.length) {
            node = new Node(array[index]);
            node.left = createBinaryTree(array, index * 2 + 1);
            node.right = createBinaryTree(array, index * 2 + 2);
        }
        return node;
    }

    //遍历过程中实现线索化
    public void inThreading(Node node){
        if(node == null)   return;

        //处理左子树
        inThreading(node.left);

        //左指针为空， 将左指针指向前驱结点
        if(node.left == null){
            node.left = preNode;
            node.isLeftThread = true;
        }
        //前一个结点的后继结点指向当前结点
        if(preNode != null && preNode.right == null){
            preNode.right = node;
            preNode.isRightThread = true;
        }
        preNode = node;

        //处理右子树
        inThreading(node.right);
    }

    public void inThreadList(Node node) {
        //1、找中序遍历方式开始的节点
        while(node != null && !node.isLeftThread) {
            node = node.left;
        }
        while(node != null) {
            System.out.print(node.data + " ");
            //如果右指针是线索
            if(node.isRightThread) {
                node = node.right;
            } else {    //如果右指针不是线索，找到右子树开始的节点
                node = node.right;
                while(node != null && !node.isLeftThread) {
                    node = node.left;
                }
            }
        }
    }

    public static void main(String[] args){
        String[] array = {"A", "B", "C", "D", "E", "F", "G", "H"};
        Node root = createBinaryTree(array, 0);

        ThreadTree tree = new ThreadTree();
        tree.inThreading(root);

        System.out.println("中序按后继节点遍历线索二叉树结果：");
        tree.inThreadList(root);
    }
}
