package Chapter5_Hashing;

import java.util.NoSuchElementException;

public class PriorityQueue<E> {
    private TwoWayNode<E> front;
    private TwoWayNode<E> rear;
    private int manyNodes;
    private int highestPriority;

    public PriorityQueue(){
        front = null;
        rear = null;
    }

    public void add(E item,int priority){
        if(priority<=0)
            throw new IllegalArgumentException("priority is negative: " + priority);

        if(manyNodes == 0){
            front = new TwoWayNode<E>(item,priority,null,null);
            rear = front;
        }
        else{
            rear.addNodeAfter(item,priority);
            rear = rear.getForelink();
        }

        if(priority>highestPriority)
            highestPriority = priority;
        manyNodes++;
    }

    public E remove(){
        E answer;

        if(manyNodes == 0)
            throw new NoSuchElementException("Queue underflow");
        TwoWayNode cursor = front;
        while(cursor.getPriority() < highestPriority){
            cursor = cursor.getForelink();
        }
        answer = (E)cursor.getData();
        cursor.removeNode();

        if(front == rear)
            front = null;
        else {
            if (cursor == front)
                front = front.getForelink();
            else if (cursor == rear)
                rear = rear.getBacklink();
        }
        manyNodes--;
        renewHighestPriority();

        return answer;
    }

    public void renewHighestPriority(){
        TwoWayNode<E> cursor;
        for(highestPriority=0,cursor = front; cursor!=null; cursor = cursor.getForelink()){
            if(cursor.getPriority()>highestPriority)
                highestPriority = cursor.getPriority();
        }
    }

    public void print(){
        for(TwoWayNode cursor = front;cursor != null;cursor = cursor.getForelink())
            System.out.println(cursor.getData()+ " "+cursor.getPriority());
    }

    public static void main(String[] args){
        PriorityQueue<String> patients = new PriorityQueue<>();
        patients.add("Paul",4);
        patients.add("Nancy",3);
        patients.add("Crystal",3);
        patients.add("Melody",1);
        patients.add("Bob",5);
        patients.add("Mike",4);
        patients.add("Zoe",5);

        patients.print();

        while(patients.manyNodes!=0){
            System.out.println();
            System.out.println("Now we are seving "+ patients.remove());
            patients.print();
        }
    }
}

class TwoWayNode<E> {//with priority //双向链表
    private E data;
    private int priority;
    private TwoWayNode<E> backlink;
    private TwoWayNode<E> forelink;

//    public static void main(String[] args){
//        TwoWayNode poly = new TwoWayNode(0,0,null,null);
//        TwoWayNode cursor = poly;
//        for(int i=1;i<6;i++,cursor = cursor.getForelink())
//            cursor.addNodeAfter(i,0);
//        cursor = poly.getForelink();
//        print(poly);
//        cursor.swapNodesAfter();
//        print(poly);
//    }

    public TwoWayNode(E initialData, int initialPriority,
                      TwoWayNode<E> initialForelink, TwoWayNode<E> initialBacklink) {
        data = initialData;
        priority = initialPriority;
        forelink = initialForelink;
        backlink = initialBacklink;
    }

    public void swapNodesAfter(){
        backlink.forelink = forelink;
        forelink.backlink = backlink;
        forelink = forelink.forelink;
        forelink.backlink = this;
        backlink = backlink.forelink;
        backlink.forelink = this;
    }

    public static void print(TwoWayNode head){
        TwoWayNode cursor = head;
        for(cursor = head;cursor!=null;cursor = cursor.getForelink())
            System.out.print(cursor.data+" ");
        System.out.println();
    }

    public void addNodeAfter(E element,int priority){
        forelink = new TwoWayNode<E>(element,priority,forelink,this);
    }

    public E getData(){ return data;}
    public int getPriority(){ return priority;}
    public TwoWayNode<E> getForelink(){ return forelink;}
    public TwoWayNode<E> getBacklink(){ return backlink;}

    /**
     * 复制一个链表，返回这个副本的头结点和尾结点
     * @param source  被复制的链表的头结点
     * @param <E>
     * @return 返回头结点和尾结点
     */
    public static <E> TwoWayNode<E>[] listCopyWithTail(TwoWayNode<E> source){
        TwoWayNode<E> copyHead;
        TwoWayNode<E> copyTail;
        TwoWayNode<E>[] answer = (TwoWayNode<E>[]) new Object[2];
        //处理空链表的特殊情况
        if(source == null)
            return null;
        //为新创建的链表建立第一个结点
        copyHead = new TwoWayNode<>(source.data, source.priority,null,null);
        copyTail = copyHead;
        //为新创建的链表建立其余结点
        while(source.backlink != null){
            source = source.backlink;
            copyTail.addNodeAfter(source.data,source.priority);
            copyTail = copyTail.forelink;
        }
        //返回新链表的头结点
        answer[0] = copyHead;
        answer[1] = copyTail;
        return answer;
    }

    public static <E> int listLength(TwoWayNode<E> head){
        TwoWayNode<E> cursor;
        int answer = 0;

        for(cursor = head; cursor != null; cursor = cursor.forelink)
            answer++;
        return answer;
    }

    public void removeNode(){
        if(forelink == null && backlink == null){
            ;
        }
        else {
            if (backlink == null) {//位于开头
                forelink.backlink = null;
            } else if (forelink == null) { //位于结尾
                backlink.forelink = null;
            }
            else {
                backlink.forelink = forelink;
                forelink.backlink = backlink;
            }
        }
    }
}


