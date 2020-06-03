package Chapter5_Hashing;

public class IntTable {
    private int manyItems;
    private int keys[];
    private String data[];
    private boolean hasBeenUsed[];

    public IntTable(int capacity){
        manyItems = 0;
        keys = new int[capacity];
        data = new String[capacity];
        hasBeenUsed = new boolean[capacity];
    }
    private int hash(int key){
        return key % data.length;
    }
    private int nextIndex(int index,int k){
        return (index+k*k)%data.length;
    }
    private int findIndex(int key){
        int count=0;
        int i = hash(key);

        while(count<data.length && hasBeenUsed[i]){
            if(key == keys[i])
                return i;
            count++;
            i = nextIndex(hash(key),count);     //linear probing
        }
        return -1;   //未找到
    }

    private void rehash(){
        int[] oldKeys = keys;
        String[] oldData = data;

        int newLength = nextPrime(2*oldData.length);
        keys = new int[newLength];
        data = new String[newLength];
        hasBeenUsed = new boolean[newLength];
        manyItems =0;

        for(int i=0;i<oldData.length;i++)
            if(oldKeys[i]!=0)
                put(oldKeys[i],oldData[i]);
    }
    private int nextPrime(int k){
        if(isPrime(k)) return k;
        else return nextPrime(k+1);
    }
    private boolean isPrime(int k){
        for(int i=2;i<Math.sqrt(k);i++)
            if(k%i==0)
                return false;
        return true;
    }
    public boolean containsKey(int key){
        return findIndex(key)!=-1;
    }

    public String put(int key,String element){
        if(manyItems>data.length/2)
            rehash();
        int index = findIndex(key);
        String answer;
        if(index!=-1){
            //关键字在表中已经存在
            answer = data[index];
            data[index] = element;
            return answer;
        }
        else if(manyItems<data.length){
            //关键字在表中不存在
            index = hash(key);
            for(int i=0;keys[index] != 0;i++)
                index = (hash(key)+i*i)%data.length;
            keys[index] = key;
            data[index] = element;
            hasBeenUsed[index] = true;
            manyItems++;
            return null;
        }
        else throw new IllegalStateException("Table is full.");
    }
    public String get(int key){
        int index = findIndex(key);
        if(index!=-1)
            return data[index];
        else
            return null;
    }
    public String remove(int key){
        int index = findIndex(key);
        String answer = null;
        if(index != -1){
            answer = data[index];
            data[index] = null;
            keys[index] = 0;
            manyItems--;
        }
        return answer;
    }

    public void putAll(int keys[],String elements[]){
        if(keys.length == elements.length)
            for(int i=0; i<keys.length;i++)
                put(keys[i],elements[i]);
    }

    public static void main(String[] args){
        IntTable test = new IntTable(5);
        test.put(89,"String 89");
        test.put(18,"String 18");
        test.put(49,"String 49");
        test.put(58,"String 58");
        test.put(69,"String 69");

        test.put(58,"Another String 58");
        test.remove(58);
        System.out.println(test.nextPrime(56));
    }
}
