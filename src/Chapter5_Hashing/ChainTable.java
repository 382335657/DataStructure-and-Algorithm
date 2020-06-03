package Chapter5_Hashing;

import java.util.LinkedList;

public class ChainTable {
    private int manyItems;
    private LinkedList keys[];
    private LinkedList data[];
    private boolean hasBeenUsed[];

    public ChainTable(int capacity){
        manyItems = 0;
        keys = new LinkedList[capacity];
        data = new LinkedList[capacity];
        hasBeenUsed = new boolean[capacity];
    }
    private int hash(int key){
        return key % data.length;
    }
    private int findIndex(int key){
        int index = hash(key);
        if(keys[index]!=null && keys[index].contains(key))
            return index;
        else return -1;
    }
    public boolean containsKey(int key){
        return findIndex(key)!=-1;
    }

    public String put(int key,String element){
        int index = findIndex(key);
        String answer = null;
        if(index!=-1){
            //关键字在表中已经存在
            int i = data[index].indexOf(key);
            answer = (String)data[index].get(i);
            data[index].remove(i);
            data[index].add(i,element);
            return answer;
        }
        else if(manyItems<data.length){
            //关键字在表中不存在
            if(keys[index] == null) {

         //       keys = new LinkedList[];
                data[index].add(element);
                keys[index].add(key);
                hasBeenUsed[index] = true;
                manyItems++;
                return null;
            }
        }
        else throw new IllegalStateException("Table is full.");
        return null;
    }
    public String get(int key){
        int index = findIndex(key);
        if(index!=-1)
            return (String)data[index].get(keys[index].indexOf(key));
        else
            return null;
    }
    public String remove(int key){
        int index = findIndex(key);
        String answer = null;
        if(index != -1){
            int i = keys[index].indexOf(key);
            answer = (String)data[index].get(i);
            data[index].remove(i);
            keys[index].remove(i);
            manyItems--;
        }
        return answer;
    }

    public static void main(String[] args){
        ChainTable test = new ChainTable(10);
        test.put(89,"String 89");
        test.put(18,"String 18");
        test.put(49,"String 49");
        test.put(58,"String 58");

        test.put(58,"Another String 58");

    }
}
