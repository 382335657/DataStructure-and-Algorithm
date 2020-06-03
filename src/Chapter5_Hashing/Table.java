package Chapter5_Hashing;

public class Table {
    private int manyItems;
    private Object[] keys;
    private Object[] data;
    private boolean[] hasBeenUsed;
    public Table(int capacity){
        if(capacity<=0)
            throw new IllegalArgumentException("Capacity is negative.");
        keys = new Object[capacity];
        data = new Object[capacity];
        hasBeenUsed = new boolean[capacity];
    }

    private int hash(Object key){
        //返回值是表的数组的一个有效下标（%data.length)
        //下标是关键字的散列码的绝对值与表的数组长度作除法运算得到的余数
        return Math.abs(key.hashCode())&data.length;
    }
    private int nextIndex(int i){
        //返回值通常是i+1, 如果i+1的值为data.length 将返回0
        if(i+1 == data.length)
            return 0;
        else
            return i+1;
    }
    private int findIndex(Object key){
        int count=0;
        int i = hash(key);

        while(count<data.length && hasBeenUsed[i]){
            if(key.equals(keys[i]))
                return i;
            count++;
            i = nextIndex(i);     //linear probing
        }
        return -1;
    }

    public boolean containsKey(Object key){
        return (findIndex(key)!=-1);
    }

    public Object get(Object key){
        int index = findIndex(key);
        if(index == -1)
            return null;
        else
            return index;
    }
    public Object put(Object key, Object element){
        int index = findIndex(key);
        Object answer;
        if(index!=-1){
            //关键字在表中已经存在
            answer = data[index];
            data[index] = element;
            return answer;
        }
        else if(manyItems<data.length){
            //关键字在表中不存在
            index = hash(key);
            while(keys[index] !=null)
                index = nextIndex(index);
            keys[index] = key;
            data[index] = element;
            hasBeenUsed[index] = true;
            manyItems++;
            return null;
        }
        else throw new IllegalStateException("Table is full.");
    }
    public Object remove(Object key){
        int index = findIndex(key);
        Object answer = null;
        if(index!=-1){
            answer = data[index];
            keys[index] = null;
            data[index] = null;
            manyItems--;
        }
        return answer;
    }

    public static void main(String[] args){
        System.out.println("45".hashCode());
        System.out.println("String".hashCode());

    }
}
