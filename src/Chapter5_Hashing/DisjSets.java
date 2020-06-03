package Chapter5_Hashing;

import java.util.Arrays;

public class DisjSets {
    private int[] s;
    public DisjSets(int numElements){
        s = new int[numElements];
        for(int i=0;i<s.length;i++)
            s[i] = -1;//指向同一个根结点
    }

    public void union(int element1,int element2){
        int root1 = find(element1);
        int root2 = find(element2);
        //高度规则  根结点中放负数，代表高度
        if(s[root2]<s[root1]) s[root1] =root2;
        else{
            if(s[root1] == s[root2]) s[root1]--;
            s[root2] = root1;
        }
    }

    public int find(int x){
        if(s[x]<0)
            return x;
        else return find(s[x]);
    }

    public String toString(){
        return Arrays.toString(s);
    }
    public static void main(String[] args){
        DisjSets test = new DisjSets(11);
        test.union(1,7);
        test.union(1,8);
        test.union(1,9);
        test.union(5,2);
        test.union(5,10);
        test.union(3,4);
        test.union(3,6);
        test.union(1,5);
        System.out.println(test);
    }
}
