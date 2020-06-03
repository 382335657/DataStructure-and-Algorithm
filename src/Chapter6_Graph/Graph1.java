package Chapter6_Graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Graph1 {
    Map<String,Vertex> vertexMap;

    private class Vertex {
        private String name; //顶点名称
        private Edge edge; //从该顶点出发的弧

        Vertex(String name, Edge edge){
            this.name = name;
            this.edge = edge;
        }
    }

    private class Edge {
        private String name; //被指向的顶点
        private int weight; //弧的权重
        private Edge next;  //被指向的下一段弧

        Edge(String name, int weight, Edge next){
            this.name = name;
            this.weight = weight;
            this.next = next;
        }
    }

    public Graph1(){
        this.vertexMap = new HashMap<>();
    }

    public void insertVertex(String vertexName){
        Vertex vertex = new Vertex(vertexName,null);
        vertexMap.put(vertexName,vertex);
    }

    public void insertEdge(String begin, String end, int weight){
        Vertex beginVertex =  vertexMap.get(begin);
        if(beginVertex == null)
            insertVertex(begin);
        Edge edge = new Edge(end,weight,null);
        if(beginVertex.edge == null)
            beginVertex.edge =edge;
        else{
            Edge lastEdge = beginVertex.edge;
            while(lastEdge.next !=null)
                lastEdge = lastEdge.next;
            lastEdge.next = edge;
        }
    }

    public void print(){
        Set<Map.Entry<String,Vertex>> set = vertexMap.entrySet();
        Iterator<Map.Entry<String,Vertex>> iterator = set.iterator();
        while(iterator.hasNext()){
            Map.Entry<String,Vertex> entry = iterator.next();
            Vertex vertex = entry.getValue();
            Edge edge = vertex.edge;
            while(edge!=null){
                System.out.println(vertex.name +" 指向 "+edge.name +" 权值为： "+edge.weight);
                edge = edge.next;
            }
        }
    }

    public static void main(String[] args){
        Graph1 graph = new Graph1();
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");
        graph.insertVertex("F");

        graph.insertEdge("C", "A", 1);
        graph.insertEdge("F", "C", 2);
        graph.insertEdge("A", "B", 4);
        graph.insertEdge("E", "B", 2);
        graph.insertEdge("A", "D", 5);
        graph.insertEdge("D", "F", 4);
        graph.insertEdge("D", "E", 3);

        graph.print();
    }
}
