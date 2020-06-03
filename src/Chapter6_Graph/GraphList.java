package Chapter6_Graph;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class GraphList<E> implements Graph<E>{
    //邻接表中表对应的链表的顶点
    private static class EdgeNode{
        int adjvex;  //邻接顶点序号
        int weight; //存储边或弧相关的信息
        EdgeNode nextadj;  //下一个邻接表结点

        public EdgeNode(int adjvex, int weight){
            this.adjvex = adjvex;
            this.weight = weight;
        }
    }
    //邻接表中表的顶点
    private static class VertexNode<E>{
        E data;
        int in;//顶点入度
        EdgeNode firstadj;

        public void setIn(int in){
            this.in = in;
        }
    }

    private VertexNode<E>[] vexs;
    private int numOfVexs;
    private int maxNumOfVexs;
    private boolean[] visited;

    public GraphList(int maxNumOfVexs){
        this.maxNumOfVexs = maxNumOfVexs;
        vexs = (VertexNode<E> []) Array.newInstance(VertexNode.class,maxNumOfVexs);
    }

    @Override
    public int getNumOfVertex() {
        return numOfVexs;
    }

    @Override
    public boolean insertVex(E v) {
        if(numOfVexs >= maxNumOfVexs)
            return false;
        VertexNode<E> vex = new VertexNode<>();
        vex.data = v;

        vexs[numOfVexs++] = vex;
        return true;
    }
    public boolean insertVexs(E[] vs){
        boolean succeeded  = true;
        for(int i=0; i<vs.length; i++){
            succeeded = insertVex(vs[i]);
            if(succeeded == false)
                break;
        }
        return succeeded;
    }

    @Override
    public boolean deleteVex(E v) {
        for(int i=0;i<numOfVexs;i++){
            if(vexs[i].data.equals(v)){
                //结点表更新
                for(int j=i;j<numOfVexs;j++)
                    vexs[j] = vexs[j+1];
                vexs[numOfVexs-1] = null;
                numOfVexs--;
                //
                EdgeNode current;
                EdgeNode previous;
                for(int j=0; j<numOfVexs; j++){
                    if(vexs[j].firstadj == null)
                        continue;
                    if(vexs[j].firstadj.adjvex == i){
                        vexs[j].firstadj = null;
                        continue;
                    }
                    current = vexs[j].firstadj;
                    while(current!=null){
                        previous = current;
                        current = current.nextadj;
                        if(current!=null && current.adjvex == i){
                            previous.nextadj = current.nextadj;
                            break;
                        }
                    }
                }
                for(int j=0;j<numOfVexs;j++){
                    current = vexs[j].firstadj;
                    while(current!=null){
                        if(current.adjvex >i)
                            current.adjvex -- ;
                        current = current.nextadj;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public int indexOfVex(E v) {
        for(int i=0;i<numOfVexs;i++)
            if(vexs[i].data.equals(v))
                return i;
        return -1;
    }

    @Override
    public E valueOfVex(int v) {
        if(v<0||v>=numOfVexs)
            return null;
        return vexs[v].data;
    }

    @Override
    public boolean insertEdge(int start, int end, int weight) {
        if(start<0 || end<0 || start>=numOfVexs || end>=numOfVexs)
            throw new ArrayIndexOutOfBoundsException();
        EdgeNode vex= new EdgeNode(end,weight);
        //索引为start的顶点没有邻接顶点
        if(vexs[start].firstadj == null)
            vexs[start].firstadj = vex;
            //有邻接顶点
        else{
            EdgeNode current = vexs[start].firstadj;
            for(;current.nextadj!=null;current = current.nextadj) ;
            current.nextadj = vex;
        }
        return true;
    }

    @Override
    public boolean deleteEdge(int start, int end) {
        if(start<0 || end<0 || start>=numOfVexs || end>=numOfVexs)
            throw new ArrayIndexOutOfBoundsException();

        EdgeNode current = vexs[start].firstadj;
        EdgeNode previous = null;
        while(current!=null && current.adjvex !=end){
            previous = current;
            current = current.nextadj;
        }
        if(current!=null)
            previous.nextadj = current.nextadj;
        return true;
    }

    @Override
    public int getEdge(int start, int end) {
        if(start<0 || end<0 || start>=numOfVexs || end>=numOfVexs)
            throw new ArrayIndexOutOfBoundsException();
        EdgeNode current = vexs[start].firstadj;
        while(current!=null) {
            if (current.adjvex == end)
                return current.weight;
            current = current.nextadj;
        }
        return 0;
    }

    public VertexNode getVertex(E v){
        for(int i=0;i<numOfVexs;i++)
            if(v.equals(vexs[i].data))
                return vexs[i];
        return null;
    }

    @Override
    public void depthFirstSearch() {
        visited = new boolean[numOfVexs];
        DFS(0,visited);
    }
    private void DFS(int v, boolean[] visited) {
        System.out.print(vexs[v].data+" ");
        visited[v] = true;
        int w = getFirstNeighbour(v);
        while(w!=-1){
            if(!visited[w]) DFS(w,visited);
            w = getNextNeighbour(v,w);
        }
    }
    private int getFirstNeighbour(int v){
        if(vexs[v].firstadj == null)
            return -1;
        else
            return vexs[v].firstadj.adjvex;
    }
    private int getNextNeighbour(int v, int w){
        EdgeNode current = vexs[v].firstadj;
        while(current!=null && current.adjvex!=w)
            current = current.nextadj;
        if(current == null || current.nextadj == null)
            return -1;
        else return current.nextadj.adjvex;
    }

    @Override
    public void breadFirstSearch() {
        visited = new boolean[numOfVexs];
        BFS(0,visited);
    }
    private void BFS(int v, boolean[] visited) {
        Queue queue = new LinkedList();
        queue.add(0);
        while(!queue.isEmpty()){
            v = (int)queue.poll();
            if(!visited[v]){
                for(EdgeNode node = vexs[v].firstadj;node!=null;node = node.nextadj)
                    queue.add(node.adjvex);
                System.out.print(vexs[v].data+" ");
                visited[v] = true;
            }
        }
    }

    @Override
    public int[] dijkstra(int v) {
        return new int[0];
    }

    public boolean topologicalSort(){
        //统计输出顶点数
        int count =0;
        //建栈存储入度为0的顶点
        Stack<Integer> leafnodes = new Stack<>();
        for(int i=0;i<numOfVexs;i++){
            if(vexs[i].in ==0)
                leafnodes.push(i);
        }
        while(!leafnodes.isEmpty()){
            //栈顶 顶点出栈
            int leafIndex = leafnodes.pop();
            System.out.print(leafIndex+" -> ");
            count++;
            //删除该节点 更新入度
            EdgeNode node = vexs[leafIndex].firstadj;
            while(node!=null){
                vexs[node.adjvex].in--;
                if(vexs[node.adjvex].in==0)
                    leafnodes.push(node.adjvex);
                node = node.nextadj;
            }
        }
        if(count!=numOfVexs)
            return false; //有回路
        else
            return true;
    }

    private void refreshIn(VertexNode v){
        for(EdgeNode node = v.firstadj;node!=null;node = node.nextadj)
            vexs[node.adjvex].in--;
    }


    public static void main(String[] args){
        GraphList test = new GraphList(7);
        test.insertVexs(new Integer[]{0,1,2,3,4,5,6});

        //0
        test.insertEdge(0,1,1);
        test.insertEdge(0,5,1);
        test.getVertex(0).setIn(0);
        //1
        test.insertEdge(1,2,1);
        test.getVertex(1).setIn(2);
        //2
        test.insertEdge(2,3,1);
        test.getVertex(2).setIn(1);
        //3
        test.insertEdge(3,6,1);
        test.getVertex(3).setIn(2);
        //4
        test.insertEdge(4,3,1);
        test.getVertex(4).setIn(2);
        //5
        test.insertEdge(5,4,1);
        test.getVertex(5).setIn(1);
        //6
        test.insertEdge(6,4,1);
        test.insertEdge(6,1,1);
        test.getVertex(6).setIn(1);


//        test.topologicalSort();


//        GraphList test = new GraphList(14);
//        test.insertVexs(new Integer[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13});
//
//        //0
//        test.insertEdge(0,11,1);
//        test.insertEdge(0,5,1);
//        test.insertEdge(0,4,1);
//        test.getVertex(0).setIn(0);
//        //1
//        test.insertEdge(1,8,1);
//        test.insertEdge(1,4,1);
//        test.insertEdge(1,2,1);
//        test.getVertex(1).setIn(0);
//        //2
//        test.insertEdge(2,9,1);
//        test.insertEdge(2,6,1);
//        test.insertEdge(2,5,1);
//        test.getVertex(2).setIn(2);
//        //3
//        test.insertEdge(3,13,1);
//        test.insertEdge(3,2,1);
//        test.getVertex(3).setIn(0);
//        //4
//        test.insertEdge(4,7,1);
//        test.getVertex(4).setIn(2);
//        //5
//        test.insertEdge(5,12,1);
//        test.insertEdge(5,8,1);
//        test.getVertex(5).setIn(3);
//        //6
//        test.insertEdge(6,5,1);
//        test.getVertex(6).setIn(1);
//        //7
//        test.getVertex(7).setIn(2);
//        //8
//        test.insertEdge(8,7,1);
//        test.getVertex(8).setIn(2);
//        //9
//        test.insertEdge(9,11,1);
//        test.insertEdge(9,10,1);
//        test.getVertex(9).setIn(2);
//        //10
//        test.insertEdge(10,13,1);
//        test.getVertex(10).setIn(1);
//        //11
//        test.getVertex(11).setIn(2);
//        //12
//        test.insertEdge(12,9,1);
//        test.getVertex(12).setIn(1);
//        //13
//        test.getVertex(13).setIn(2);
    }
}

