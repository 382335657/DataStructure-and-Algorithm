package Chapter3_ListStackQueue;

public class Josephus {
    private int sum;
    private int step;  //每隔step个去掉一个数字
    private IntNode head;

    public Josephus(int number,int step){
        sum = number;
        this.step = step;
        head = new IntNode(1,null);
        IntNode cursor = head;
        for(int i = 1;i<number;i++,cursor = cursor.getLink())
            cursor.addNodeAfter(i+1);
        cursor.setLink(head);        //循环
    }

    //链表实现
    public int evaluateFromList(){
        IntNode cursor = head;
        for(int i=1;i<sum;i++){
            for(int j=0;j<step-1;j++) cursor = cursor.getLink();
            cursor.removeNodeAfter();
            cursor = cursor.getLink();
        }
        return cursor.getData();
    }

    //数组实现
    public int evaluateFromArray(){
        int res=0;
        boolean[] isChosen = new boolean[sum];
        for(int i=0;i<sum;i++)
            isChosen[i] = true;
        int cursor=0;
        for(int i=1;i<=sum-1;i++){  //执行n-1次
            int k = 0;
            while(k<step){
                if(isChosen[cursor]==true)
                    k++;
                cursor++;
                if(cursor>=sum){
                    cursor-=sum;
                }
            }
            while(isChosen[cursor]==false){
                cursor++;
                if(cursor>=sum) cursor-=sum;
            }
            isChosen[cursor] = false;
            cursor+=1;
            if(cursor>=sum) cursor-=sum;
        }
        for(int i=0;i<sum;i++)
            if(isChosen[i] == true)
                res = i;
        return res+1;
    }


    public static void main(String[] args){
        Josephus test = new Josephus(15,4);
        System.out.println(test.evaluateFromList());
        System.out.println(test.evaluateFromArray());
    }
}