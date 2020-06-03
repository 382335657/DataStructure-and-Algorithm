package Chapter4_Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HuffmanTree {
    private Node root;
    static private int wpl=0;
    private ArrayList<Node> leaves = new ArrayList<>();


    public HuffmanTree(List nodes) {
        root = null;
        makeTree(nodes);
        calWPL(root,0);
        setCode(root,"");
    }

    //构造树
    public void makeTree(List nodes) {
        while (nodes.size() > 1) {
            Collections.sort(nodes);
            Node left = (Node) nodes.get(nodes.size() - 1);
            Node right = (Node) nodes.get(nodes.size() - 2);//取最小的两个结点
            Node parent = new Node(null, left.getWeight() + right.getWeight());
            parent.setLeft(left);
            parent.setRight(right);
            nodes.remove(left);
            nodes.remove(right);
            nodes.add(parent);
        }
        root = (Node) nodes.get(0);
    }

    //计算WPL
    public void calWPL(Node node, int levelNum) {
        if(node.isLeaf())
            wpl+=node.getWeight()*levelNum;
        else{
            calWPL(node.getLeft(),levelNum+1);
            calWPL(node.getRight(),levelNum+1);
        }
    }

    public void setCode(Node node, String levelString){
        if(node.isLeaf()){
            node.setCode(levelString);
            leaves.add(node);
        }
        else{
            setCode(node.getLeft(),levelString+"0");
            setCode(node.getRight(),levelString+"1");
        }
    }

    public void print(){
        for(int i=0; i<leaves.size();i++){
            Node node = leaves.get(i);
            System.out.println(node.getData()+" "+node.getCode());
        }
    }
    public static void main(String[] args){
        List<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node("S",29));
        nodes.add(new Node("A",8));
        nodes.add(new Node("M",10));
        nodes.add(new Node("T",4));
        nodes.add(new Node("K",7));
        nodes.add(new Node("Q",15));
        HuffmanTree tree = new HuffmanTree(nodes);
        tree.print();
    }
}

class Node implements Comparable<Node>{
    private String data;
    private String code = "";
    private int weight;
    private Node left;
    private Node right;

    public Node(String data, int weight){
        this.data = data;
        this.weight = weight;
    }

    public String toString(){
        return data+": "+weight;
    }

    public String getData() {
        return data;
    }
    public int getWeight() {
        return weight;
    }
    public Node getLeft() {
        return left;
    }
    public void setLeft(Node left) {
        this.left = left;
    }
    public Node getRight() {
        return right;
    }
    public void setRight(Node right) {
        this.right = right;
    }
    public void setCode(String code){ this.code = code;}
    public String getCode(){ return code;}

    @Override
    public int compareTo(Node o) {
        if(o.weight>this.weight)
            return 1;
        else if(o.weight<this.weight)
            return -1;
        return 0;
    }

    public boolean isLeaf(){
        return left==null && right==null;
    }
}
