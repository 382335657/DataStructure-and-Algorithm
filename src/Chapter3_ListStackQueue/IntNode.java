package Chapter3_ListStackQueue;

public class IntNode {
    private int data;
    private IntNode link;
    public IntNode(int initialData, IntNode initialLink){
        data = initialData;
        link = initialLink;
    }

    /**
     * 在链表的非头部位置添加新的节点
     * 赋值语句右边 new IntNode代表生成了一个新结点 并指向当前link的位置
     * 赋值语句 代表link指向新结点
     * @param element 新元素
     */
    public void addNodeAfter(int element){
        link = new IntNode(element,link);
    }


    public static void print(IntNode head){
        IntNode cursor;
        for(cursor = head;cursor!=null; cursor = cursor.link)
            System.out.print(cursor.data+" ");
        System.out.println();
    }

    public int getData() { return data; }
    public void setData(int data) { this.data = data; }
    public IntNode getLink() { return link; }
    public void setLink(IntNode link) { this.link = link; }



    /**
     * link.link代表link指向的结点再指向的结点
     * 赋值语句代表 使当前结点指向link.link  也就是跳过link的下一个结点
     */
    public void removeNodeAfter(){
        link = link.link;
    }

    /**
     * 计算链表中结点的个数
     * @param head 引向链表中头结点的引用 该链表可以是空链表 头结点为null
     * @return 返回给定链表中结点的个数
     */
    public static int listLength(IntNode head){
        IntNode cursor;
        int answer = 0;
        //cursor代表游标 依次引向链表中的每个结点
        for(cursor=head; cursor !=null; cursor = cursor.link)
            answer++;
        return answer;
    }

    /**
     * 依次打印链表元素
     * @param head 引向链表中头结点的引用
     */
    public static void listPrint(IntNode head){
        IntNode cursor;
        for(cursor = head; cursor!=null; cursor = cursor.link)
            System.out.print(cursor.data +" ");
    }

    /**
     * 在链表中查找具有某个特定值的结点
     * @param head 引向链表头结点的引用（head==null 表示链表为空）
     * @param target 目标数据
     * @return 返回对目标数据的引用 未找到就返回null
     */
    public static IntNode listSearch(IntNode head, int target){
        IntNode cursor;
        for(cursor = head; cursor!= null; cursor = cursor.link)
            if(cursor.data == target)
                return cursor;
        return null;
    }

    /**
     * 查找链表中位于指定位置的结点
     * @param head 引向头结点的引用
     * @param position 结点的序号
     * @return 返回引向链表中指定位置的结点的引用 没有就返回null
     */
    public static IntNode listPosition(IntNode head, int position){
        IntNode cursor;
        int i;
        //position为负值
        if(position<=0)
            throw new IllegalArgumentException("Positon is not positive");

        cursor = head;
        for(i=1; i<position&&cursor!=null; i++){
            cursor = cursor.link;
        }
        return cursor;
    }

    /**
     * 复制链表
     * @param source 引向要复制链表的头结点的引用
     * @return 返回副本的头结点的引用
     */
    public static IntNode listCopy(IntNode source){
        IntNode copyHead;
        IntNode copyTail;

        //处理空链表的特殊情况
        if(source==null)
            return null;

        //为新创建的链表生成第一个结点
        copyHead = new IntNode(source.data,null);
        copyTail = copyHead;
        //建立其他结点
        while(source.link !=null ){
            source = source.link;
            copyTail.addNodeAfter(source.data);
            copyTail = copyTail.link;
        }
        //返回新创建链表中头结点的引用
        return copyHead;
    }

    public static IntNode[] listCopyWithTail(IntNode source){
        IntNode copyHead;
        IntNode copyTail;
        IntNode[] answer = new IntNode[2];
        if(source == null)
            return answer;

        copyHead = new IntNode(source.data,null);
        copyTail = copyHead;

        while(source.link != null){
            source = source.link;
            copyTail.addNodeAfter(source.data);
            copyTail = copyTail.link;
        }
        answer[0] = copyHead;
        answer[1] = copyTail;
        return answer;

    }

    /**
     * 复制链表的一部分
     * @param start
     * @param end
     * @return [0]-> 头结点 [1]->尾结点
     */
    public static IntNode[] listPart(IntNode start, IntNode end){
        IntNode copyHead;
        IntNode copyTail;
        if(start==null)
            throw new IllegalArgumentException("start is null");
        if(end==null)
            throw new IllegalArgumentException("end is null");

        copyHead = new IntNode(start.data,null);
        copyTail = copyHead;

        while( start != end){
            start = start.link;
            if(start == null)
                throw new IllegalArgumentException("end node was not found on the list");
            copyTail.addNodeAfter(start.data);
            copyTail = copyTail.link;
        }

        IntNode[] res = {copyHead,copyTail};
        return res;
    }
}