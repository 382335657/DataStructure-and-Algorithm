package Chapter5_Hashing;

public class MaxHeap {
    private int manyItems;
    private int[] items;
    
    public MaxHeap(int[] data){
        this.manyItems = data.length;
        items = new int[(manyItems+1)*5]; //保证有足够的空间大小
        for(int i=0; i<data.length; i++)
            items[i+1] = data[i];
        buildHeapFromDown();
        //sort();
    }

    //算法思想 从最小堆开始（size/2）把每一个结点的左右树都变成堆
    private void buildHeapFromDown() {
        if(items==null || items.length<=0)
            return;
        for(int i=manyItems/2; i>0; i--)
            percolateDown(i);
    }
    //下滤
    private void percolateDown(int index){
        int temp = items[index];
        int parent,child;
        for(parent = index;parent*2<=manyItems;parent = child){
            child = parent*2;
            if(child!=manyItems && items[child]<items[child+1])
                child++;
            if(temp>items[child]) break;
            else items[parent] = items[child];
        }
        items[parent] = temp;
    }

    private void sort(){
        if(manyItems>1){
            swap(1,manyItems);
            manyItems--;
            percolateDown(1);
            sort();
        }
        else  return;
        manyItems++;
    }
    private void swap(int i, int j){
        int temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }

    public void insert(int element){
        //进行上滤
        int i;
        for(i=++manyItems; i==1 && element<items[i/2]; i/=2)
            items[i]=items[i/2];
        items[i] = element;
    }
    public int delete(int element){
        //寻找element的序号index
        int index = 0;
        for(int i=1;i<manyItems;i++)
            if(items[i] == element) index = i;
        //向下过滤
        if(index!=0){
            int res = items[index];
            swap(index,manyItems);

            items[manyItems]=0;
            manyItems--;
            percolateDown(index);
            return res;
        }
        else return -1;//未找到
    }
    public int deleteMax(){
        if(manyItems==0)
            return -1;//未删除
        int min = items[1];
        swap(1,manyItems);
        items[manyItems] =0;
        manyItems--;
        percolateDown(1);
        return min;
    }

    public String toString(){
        StringBuffer sb = new StringBuffer("");
        for(int i=1;i<=manyItems;i++){
            sb.append(items[i]+" ");
        }
        return "items = [ "+sb.toString()+"]";
    }

    //建堆 执行k-1次删除操作 返回items[1]
    public static int findKthMax(int[] a,int k){
        MaxHeap heap = new MaxHeap(a);
        for(int i=0;i<k-1;i++)
            heap.deleteMax();
        return heap.items[1];
    }

    public static void main(String[] args) {
        int[] a = {10,12,1,14,6,5,8,15,3,9,7,4,11,13,2};
        MaxHeap test = new MaxHeap(a);
        System.out.println(test);
//
//        test.deleteMax();
//        System.out.println(test);
//
//        System.out.println(findKthMax(a,2));

        System.out.println(test.delete(13));

        System.out.println(test);
    }
}

