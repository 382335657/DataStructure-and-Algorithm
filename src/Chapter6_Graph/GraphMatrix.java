package Chapter6_Graph;

import org.omg.CORBA.INITIALIZE;
import sun.awt.image.ImageWatched;

import java.lang.reflect.Array;
import java.util.*;

//无定向图
public class GraphMatrix<E> implements Graph<E> {
    static int INFINITY = Integer.MAX_VALUE;
    private E[] vexs;
    private int[][] edges;
    private int numOfVexs;
    private int maxNumOfVexs;
    private boolean[] visited;

    public GraphMatrix(int maxNumOfVexs, Class<E> type) {
        this.maxNumOfVexs = maxNumOfVexs;
        edges = new int[maxNumOfVexs][maxNumOfVexs];
        for (int i = 0; i < maxNumOfVexs; i++)
            for (int j = 0; j < maxNumOfVexs; j++)
                edges[i][j] = INFINITY;
        vexs = (E[]) Array.newInstance(type, maxNumOfVexs);
    }

    @Override
    public int getNumOfVertex() {
        return numOfVexs;
    }

    @Override
    public boolean insertVex(E v) {
        if (numOfVexs >= maxNumOfVexs)
            return false;
        vexs[numOfVexs++] = v;
        return true;
    }

    public boolean insertVexs(E[] vs) {
        boolean succeeded = true;
        for (int i = 0; i < vs.length; i++) {
            succeeded = insertVex(vs[i]);
            if (succeeded == false)
                break;
        }
        return succeeded;
    }

    @Override
    public boolean deleteVex(E v) {
        for (int i = 0; i < numOfVexs; i++)
            if (vexs[i].equals(v)) {
                for (int j = i; j < numOfVexs; j++)
                    vexs[j] = vexs[j + 1];
                vexs[numOfVexs - 1] = null;
                for (int col = i; col < numOfVexs - 1; col++)
                    for (int row = 0; row < numOfVexs; row++)
                        edges[col][row] = edges[col + 1][row];
                for (int row = i; row < numOfVexs - 1; row++)
                    for (int col = 0; col < numOfVexs; col++)
                        edges[col][row] = edges[col][row + 1];
                numOfVexs--;
                return true;
            }
        return false;
    }

    @Override
    public int indexOfVex(E v) {
        for (int i = 0; i < numOfVexs; i++)
            if (vexs[i].equals(v))
                return i;
        return -1;
    }

    @Override
    public E valueOfVex(int v) {
        if (v < 0 || v >= numOfVexs)
            return null;
        return vexs[v];
    }

    @Override
    public boolean insertEdge(int start, int end, int weight) {
        if (start < 0 || end < 0 || start >= numOfVexs || end >= numOfVexs)
            throw new ArrayIndexOutOfBoundsException();
        edges[start][end] = weight;
        // edges[end][start] = weight;
        return true;
    }

    @Override
    public boolean deleteEdge(int start, int end) {
        if (start < 0 || end < 0 || start >= numOfVexs || end >= numOfVexs)
            throw new ArrayIndexOutOfBoundsException();
        edges[start][end] = 0;
        //    edges[end][start] = 0;
        return true;
    }

    @Override
    public int getEdge(int start, int end) {
        if (start < 0 || end < 0 || start >= numOfVexs || end >= numOfVexs)
            throw new ArrayIndexOutOfBoundsException();
        return edges[start][end];
    }

    @Override
    public void depthFirstSearch() {
        visited = new boolean[numOfVexs];
        DFS(0, visited);
    }

    private void DFS(int v, boolean[] visited) {
        System.out.print(vexs[v] + " ");
        visited[v] = true;
        int w = getFirstNeighbour(v);
        while (w != -1) {
            if (!visited[w]) DFS(w, visited);
            w = getNextNeighbour(v, w);
        }
    }

    private int getFirstNeighbour(int v) {
        for (int i = 0; i < numOfVexs; i++)
            if (edges[v][i] < INFINITY)
                return i;
        return -1;
    }

    private int getNextNeighbour(int v, int w) {
        for (int i = w + 1; i < numOfVexs; i++)
            if (edges[v][i] < INFINITY)
                return i;
        return -1;
    }

    @Override
    public void breadFirstSearch() {  //类似于层序遍历
        visited = new boolean[numOfVexs];
        BFS(0, visited);
    }

    private void BFS(int v, boolean[] visited) {
        Queue queue = new LinkedList();
        queue.add(0);
        while (!queue.isEmpty()) {
            v = (int) queue.poll();
            if (!visited[v]) {
                for (int i = 0; i < numOfVexs; i++)
                    if (edges[v][i] < INFINITY) queue.add(i);
                System.out.print(vexs[v] + " ");
                visited[v] = true;
            }
        }
    }

    @Override
    //统计顶点v到其他各个顶点的最短路径
    public int[] dijkstra(int v) {
        int[] dist = new int[numOfVexs];
        int[] prev = new int[numOfVexs];
        boolean[] hasFound = new boolean[numOfVexs];
        String[] traces = new String[numOfVexs];

        for (int i = 0; i < numOfVexs; i++) {
            hasFound[i] = false;
            dist[i] = edges[v][i];
            traces[i] = String.valueOf(v) + "->";
            prev[i] = 0;
        }//初始化
        hasFound[v] = true;
        dist[v] = 0;// 对顶点v自身初始化

        int k = 0;
        //遍历n-1次 每次找出一个顶点的最短路径
        for (int i = 1; i < numOfVexs; i++) {
            //寻找当前最小的路径
            int min = INFINITY;
            for (int j = 0; j < numOfVexs; j++)
                if (hasFound[j] == false && dist[j] < min) {
                    min = dist[j];
                    k = j;
                }
            //顶点k为已经获取到的最短路径
            hasFound[k] = true;

            //修正当前最短路径和前驱顶点
            for (int j = 0; j < numOfVexs; j++)
                if (!hasFound[j] && edges[k][j] < INFINITY && min + edges[k][j] < dist[j]) {
                    dist[j] = min + edges[k][j];
                    prev[j] = k;
                    traces[j] = traces[j] + String.valueOf(k) + "->";
                }
        }
        System.out.println("dijkstra:" + vexs[v]);

        for (int i = 0; i < numOfVexs; i++)
            traces[i] += String.valueOf(i);

        for (int i = 0; i < numOfVexs; i++)
            System.out.println(vexs[v] + " to " + vexs[i] + " : " + traces[i].toString() + " = " + dist[i]);
        return dist;
    }

    //多源最短路算法
    public int[][] floyed() {
        int[][] dist = new int[numOfVexs][numOfVexs];
        int[][] path = new int[numOfVexs][numOfVexs];

        for (int i = 0; i < numOfVexs; i++)
            for (int j = 0; j < numOfVexs; j++) {
                if (i == j)
                    dist[i][j] = 0;
                else
                    dist[i][j] = edges[i][j];
                path[i][j] = -1;
            }

        for (int k = 0; k < numOfVexs; k++)//经过顶点k
            for (int i = 0; i < numOfVexs; i++)
                for (int j = 0; j < numOfVexs; j++)
                    if (dist[i][k] != INFINITY && dist[k][j] != INFINITY
                            && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        path[i][j] = k;
                    }
        return dist;
    }


    public BTNode generateMinTreePrim() {
        int index = 0;
        int sum = 0;
        int[] lowcost = new int[numOfVexs];//新集合的最小权重
        int[] nearvex = new int[numOfVexs];//生成树中的结点
        for (int i = 0; i < lowcost.length; i++) {
            lowcost[i] = edges[index][i];
            nearvex[i] = 0;
        }
        nearvex[0] = -1;//将第零个结点加到生成树中去

        //每一轮加入一个点，一共要加n-1个点
        for (int i = 1; i < numOfVexs; i++) {
            int min = INFINITY;
            int v = 0;
            for (int j = 1; j < numOfVexs; j++)
                if (nearvex[j] != -1 && lowcost[j] < min) {
                    v = j;
                    min = lowcost[j];
                }//寻找最小边
            if (v != 0) {
                lowcost[v] = 0;
                sum += min;
                for (int j = 1; j < numOfVexs; j++) {

                }
            }

        }
        return null;
    }


    public static void main(String[] args) {
        GraphMatrix test = new GraphMatrix(4, String.class);
        test.insertVexs(new String[]{"v0", "v1", "v2", "v3"});

        test.insertEdge(0, 2, 5);
        test.insertEdge(1, 0, 5);
        test.insertEdge(1, 3, 20);
        test.insertEdge(2, 1, 5);
        test.insertEdge(2, 3, 7);
        test.insertEdge(3, 1, 10);

//        test.insertEdge(0,1,1);
//        test.insertEdge(0,2,1);
//        test.insertEdge(1,3,1);
//        test.insertEdge(1,4,1);
//        test.insertEdge(2,5,1);
//        test.insertEdge(2,6,1);
//        test.insertEdge(3,7,1);
//        test.insertEdge(4,7,1);
//        test.insertEdge(5,6,1);
        test.dijkstra(0);
//        test.depthFirstSearch();
//        test.breadFirstSearch();
    }
}