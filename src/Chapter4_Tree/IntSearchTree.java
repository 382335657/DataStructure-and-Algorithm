package Chapter4_Tree;

public class IntSearchTree {
    private IntNode root;

    public IntSearchTree(){
        root = null;
    }
    public IntSearchTree(IntNode root){
        this.root = root;
    }
    //non-recursive
    public void add(int element){
        if(root == null)
            root = new IntNode(element, null,null);
        else{
            //root.insert(element,root);
            IntNode parent = root;
            IntNode cursor = root;
            while(true){
                parent = cursor;
                if(element>cursor.getData()){
                    cursor = cursor.getRight();
                    if(cursor == null) {
                        parent.setRight(new IntNode(element, null, null));
                        return;
                    }
                }
                else{
                    cursor = cursor.getLeft();
                    if(cursor == null){
                        parent.setLeft(new IntNode(element,null,null));
                        return;
                    }
                }
            }
        }
    }

    public void addAll(int[] elements){
        for(int i=0; i<elements.length; i++)
            add(elements[i]);
        root.renewLeftSize();
    }
    public void print(){
        root.print();
    }
    public IntNode find(int element){
        if(root==null)
            return null;
        IntNode cursor = root;
        while(cursor.getData()!=element){
            if(element>cursor.getData())
                cursor = cursor.getRight();
            else
                cursor = cursor.getLeft();
            if(cursor == null) return null; //未找到
        }
        return cursor;
    }
    public IntNode findMin(IntNode t){//相当于找最左边的结点 recursive
        if(t==null)
            return null;
        else if(t.getLeft() == null)
            return t;
        return findMin(t.getLeft());
    }
    public IntNode findKthMin(IntNode t, int k){ //找第k个小的结点
        if(t == null)
            return null;

        if(k==1)
            return t;
        else {
            if (t.getLeftSize() >= k)
                return findKthMin(t.getLeft(), k);
            else
                return findKthMin(t.getRight(),k-t.getLeftSize()-1);
        }
    }
    public IntNode findMax(IntNode t){ //相当于找最右边的结点 Nonrecursive
        if(t!=null)
            while(t.getRight()!=null)
                t = t.getRight();
        return t;
    }

    //判定是否为有效的二叉搜索树
    public static boolean isValidSBT(IntSearchTree tree){
        return tree.subTree(tree.root,tree.findMin(tree.root).getData(),tree.findMax(tree.root).getData());
    }
    public boolean subTree(IntNode root,int min, int max){
        if(root == null)
            return true;
        if(root.getData()<=max && root.getData()>=min
        && subTree(root.getLeft(),min,root.getData())
        && subTree(root.getRight(),root.getData(),max))
            return true;
        else return false;
    }
    /**
     * 删除结点为叶结点 将这个parent.left或right设置为null
     * 删除结点没有左子树 将parent的左树或右树设置为cursor的右子树
     * 删除结点有左子树
     *          将cursor的值设为左子树中最大的数字（左子树中最右边的结点）
     *          调用removeRightMost方法删除最大数字（最右边）的结点
     * @param element
     * @return
     */
    public boolean delete(int element){
        IntNode cursor = root;
        IntNode parent = root;
        boolean isRightChild = true;
        while(cursor.getData()!=element){
            parent = cursor;
            if(element>cursor.getData()){
                cursor = cursor.getRight();
                isRightChild = true;
            }
            else{
                cursor = cursor.getLeft();
                isRightChild = false;
            }
            if(cursor == null) return false;
        }
        //此时cursor就是要删除的结点 parent为其父结点

        //要删除的结点没有左子树
        if(cursor.getLeft()==null){
            if(isRightChild)
                parent.setRight(cursor.getRight());
            else
                parent.setLeft(cursor.getRight());
        }
        //要删除的结点有左子树
        else{
            cursor.setData(cursor.getLeft().getRightMost().getData());  //将cursor的数据设置为左子树最大的数字
            cursor.setLeft(cursor.getLeft().removeRightmost());
        }
        return true;
    }

    public static void main(String[] args){
        IntSearchTree test = new IntSearchTree();
        test.addAll(new int[]{39,24,64,30,26,53,70,75,72,35,27,23,25,60});
//        System.out.println(test.findKthMin(test.root,7).toString());
//        System.out.println(test.findKthMin(test.root,12).toString());
        System.out.println(isValidSBT(test));
//        test.print();
//        test.delete(24);
//        System.out.println();
//        test.print();
//        System.out.println("\n"+test.findMin(test.root)+" "+test.findMax(test.root));
    }
}

