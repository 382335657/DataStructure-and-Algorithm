package Chapter3_ListStackQueue;

public class WireRouting {
    private int n;  //n*n的地图
    private String[][] map;

    public WireRouting(int n, point[] block){
        this.n = n;
        map = new String[n][n];

        for(int i=1;i<=n;i++)
            for(int j=1;j<=n;j++)
                map[i-1][j-1] = "_";

        for(int i=0;i<block.length;i++)
            map[block[i].row-1][block[i].col-1] = "#";
    }

    public void setAB(point a, point b){
        map[a.row-1][a.col-1] = "a";
        map[b.row-1][b.col-1] = "b";
    }

    public void print(){
        for(int i=1;i<=n;i++){
            for(int j=1;j<=n;j++)
                System.out.print(map[i-1][j-1]+" ");
            System.out.println();
        }
    }
    public static void main(String[] args){
        point[] block;
        block = new point[]{new point(3, 1),
                new point(3,2),new point(4,2),
                new point(5,3),
                new point(4,4),new point(5,4),
                new point(1,5),new point(5,5),
                new point(1,6), new point(2,6),new point(3,6),
                new point(1,7),new point(2,7),new point(3,7),};
        WireRouting test = new WireRouting(7,block);
        test.setAB(new point(2,3),new point(6,4));
        test.print();
    }
}

class point{
    int row;
    int col;

    public point(int col, int row){
        this.col = col;
        this.row = row;
    }
}
