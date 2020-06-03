package Chapter3_ListStackQueue;

import java.util.LinkedList;
import java.util.Queue;

public class Binomial_expansion {
    private int n;
    private Queue<Integer> queue = new LinkedList<Integer>();

    public Binomial_expansion(int n){
        this.n = n;
        calculate();
    }
    //0 1 1 0
    //0
    public void calculate(){
        int s,t;
        queue.add(0);
        queue.add(1);
        queue.add(1);
        System.out.println("1 1");
        // 0 1 1 0 1 2
        for(int i=1;i<n;i++ ){  //i 层数
            queue.add(0);
            for(int j=0;j<i+2;j++){
                s = queue.poll();
                t = s+queue.peek();
                queue.add(t);
                System.out.print(t+" ");
            }
            System.out.println();
        }
    }
    public static void main(String[] args){
        Binomial_expansion test = new Binomial_expansion(8);
    }}
