package Chapter6_Graph;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class GraphCycle {
    private static int[][] edges = {
           //0  1  2  3  4  5  6
            {0, 1, 0, 0, 0, 1, 0}, //0
            {0, 0, 1, 0, 0, 0, 0}, //1
            {0, 0, 0, 1, 0, 0, 0}, //2
            {0, 0, 0, 0, 0, 0, 1}, //3
            {0, 0, 0, 1, 0, 0, 0}, //4
            {0, 0, 0, 0, 1, 0, 0}, //5
            {0, 1, 0, 0, 1, 0, 0}, //6
    };
    private static boolean[] visited = new boolean[edges.length];
    private static Stack<Integer> trace = new Stack<>();
    private static ArrayList<String> traces = new ArrayList<>();

    private static boolean contains(Stack<Integer> trace, int num){
        for(int i:trace)
            if(i == num)
                return true;
        return false;
    }
    private static void dfs(int start){
        visited[start] = true;
        trace.push(start);
        for(int i=0;i<edges.length;i++){
            if(!visited[i] && edges[start][i] == 1)
                dfs(i);
            else if(edges[start][i] == 1 && i==trace.get(0)){
                trace.push(i);
                traces.add(trace.toString());
                System.out.println(trace);
                trace.pop();
            }
        }
        trace.pop();
        visited[start] = false;
    }
    private static void reset(boolean[] a){
        for(int i=0;i<a.length;i++)
            a[i] = false;
    }

    private static void percolate(ArrayList list){
        for(int i=0;i<list.size();i++){
            for(int j=i+1;j<list.size();j++) ;

        }
    }

    public static void main(String[] args){
        for(int i=0;i<edges.length;i++){
            dfs(i);
            trace.empty();
            reset(visited);
        }
        percolate(traces);
    }
}
