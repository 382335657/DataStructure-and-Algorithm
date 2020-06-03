package Chapter3_ListStackQueue;

import java.util.Queue;

public class LinkedList {
    private IntNode head;
    private int manyNodes;

    public static void main(String[] args){
        IntNode n1 = new IntNode(0,null);
        IntNode cursor = n1;
        for(int i=1; i<10; i++,cursor = cursor.getLink())
            cursor.addNodeAfter(i);

        IntNode n2 = new IntNode(1,null);
        cursor = n2;
        for(int i=6; i<10; i++,cursor = cursor.getLink())
            cursor.addNodeAfter(i+1);

        LinkedList l1 = new LinkedList(n1);
        LinkedList l2 = new LinkedList(n2);

        print(l1);
        print(l2);

        print(merge(l1,l2));
        print(union(l1,l2));
        print(reverse(l1));
    }

    public static void print(LinkedList l){
        IntNode cursor;
        for(cursor = l.head;cursor!=null; cursor = cursor.getLink())
            System.out.print(cursor.getData()+" ");
        System.out.println();
    }

    public LinkedList(){
        head = new IntNode(0,null);
        manyNodes = 0;
    }

    public LinkedList(IntNode head){
        this.head = head;
        IntNode cursor;
        for(cursor=head,manyNodes=0;cursor!=null; cursor = cursor.getLink())
            manyNodes++;
    }

    //Given two sorted lists L1 and L2, compute L1 merge L2
    public static LinkedList merge(LinkedList l1, LinkedList l2){
        LinkedList res = new LinkedList();
        IntNode cursor1 = l1.head;
        IntNode cursor2 = l2.head;
        IntNode cursor = res.head;

        while(cursor1 != null && cursor2 != null){
            if(cursor1.getData()>cursor2.getData())
                cursor2 = cursor2.getLink();
            else if(cursor1.getData()<cursor2.getData())
                cursor1 = cursor1.getLink();
            else{
                cursor.addNodeAfter(cursor1.getData());
                cursor1 = cursor1.getLink();
                cursor2 = cursor2.getLink();
                cursor = cursor.getLink();
            }
        }
        res.head = res.head.getLink();
        return res;
    }

    public static LinkedList union(LinkedList l1, LinkedList l2){
        LinkedList res = new LinkedList();
        IntNode cursor1 = l1.head;
        IntNode cursor2 = l2.head;
        IntNode cursor = res.head;

        while(cursor1 != null && cursor2 != null){
            if(cursor1.getData()>cursor2.getData()){
                cursor.addNodeAfter(cursor2.getData());
                cursor2 = cursor2.getLink();
                cursor = cursor.getLink();
            }
            else if(cursor1.getData()<cursor2.getData()){
                cursor.addNodeAfter(cursor1.getData());
                cursor1 = cursor1.getLink();
                cursor = cursor.getLink();
            }
            else{
                cursor.addNodeAfter(cursor1.getData());
                cursor1 = cursor1.getLink();
                cursor2 = cursor2.getLink();
                cursor = cursor.getLink();
            }
        }
        for(;cursor1!=null;cursor1 = cursor1.getLink(),cursor = cursor.getLink())
            cursor.addNodeAfter(cursor1.getData());
        for(;cursor2!=null;cursor2 = cursor2.getLink(),cursor = cursor.getLink())
            cursor.addNodeAfter(cursor2.getData());
        res.head = res.head.getLink();
        return res;
    }

    public static LinkedList reverse(LinkedList l){
        LinkedList res = new LinkedList(null);
        IntNode cursor = l.head;
        while(cursor!=null){
            res.head = new IntNode(cursor.getData(),res.head);
            cursor = cursor.getLink();
        }
        return res;
    }

    public static class Binomial_expansion {
        private int n; //层数
        private Queue<Integer> numbers = new java.util.LinkedList<Integer>();

        public Binomial_expansion(int n){
            this.n = n;

        }

        public void calculate(){

        }
        public static void main(String[] args){
            Binomial_expansion test = new Binomial_expansion(6);
        }
    }
}
