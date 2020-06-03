package Chapter7_Sorting;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Sorting {
    private int[] a;
    private int[] res;
    private Sorting(int[] a){
        this.a = a;
        res = new int[a.length];
    }

    private void arraycopy(){
        for(int i=0;i<a.length;i++)
            res[i] = a[i];
    }

    //插入排序
    public void insertSort(){
        for(int i=0;i<a.length;i++){
            int j=i;
            while(j>0 && res[j-1]>a[i]){
                res[j] = res[j-1];
                j--;
            }
            res[j] = a[i];
        }
    }
    public void insertSorting(){
        for(int i=0;i<a.length;i++)
            insert(i);
    }
    private void insert(int index){
        int i = index;
        while(i>0 && res[i-1]>a[index]){
            res[i] = res[i-1];
            i--;
        }
        res[i] = a[index];
    }

    //希尔排序
    public void shellSorting(){
        arraycopy();
        for(int gap = res.length/2;gap>0;gap/=2)
            for(int i=0;i<gap;i++)    //将i,i+gap,i+2gap...上的数字排好序
                bubbleSort(i,gap);

    }
    private void bubbleSort(int start, int gap){
        for(int i=0;i<res.length-gap-start;i+=gap)
            for(int j=gap+start;j<res.length;j+=gap)
                if(res[j-gap]>res[j]) swap(j-gap,j);
    }

    //选择排序——直接选择排序
    public void selectSorting(){
        arraycopy();
        selectSort(0,res.length-1);
    }
    private void selectSort(int start,int end){
        if(start<end) {
            int min = res[start];
            int minPos = start;
            for (int i = start; i < end; i++)
                if (min > res[i]) {
                    min = res[i];
                    minPos = i;
                }
            swap(start, minPos);
            selectSort(start + 1, end);
        }
    }

    //冒泡排序
    public void bubbleSort(){
        arraycopy();
        for(int i=0;i<res.length;i++)
            for(int j=1;j<res.length-i;j++)
                if(res[j-1]>res[j]) swap(j-1,j);
    }

    //快速排序
    public void quickSorting(){
        arraycopy();
        quickSort(0,res.length-1);
    }
    private void quickSort(int start, int end){
        if(start < end) {
            int pivotpos = partition(start,end);
            quickSort(start, pivotpos);
            quickSort(pivotpos + 1, end);
        }
    }
    private void swap(int m,int n){
        int temp = res[m];
        res[m] = res[n];
        res[n] = temp;
    }
    private int partition(int start,int end){
        int pivot = res[start];
        int i = start;
        int j = end;
        while (i != j) {
            //从左边找到第一个大于等于基准值的数
            while (res[i] < pivot)
                i++;
            //从右边找到第一个小于基准值的数
            while (res[j] > pivot)
                j--;
            swap(i, j);
        }
        return i;
    }



    //归并排序
    public void mergeSorting(){
        arraycopy();
        mergeSort(0,res.length-1);
    }
    private void mergeSort(int start, int end){
        if(start<end){
            int mid = (start+end)/2;
            mergeSort(start,mid);
            mergeSort(mid+1,end);
            merge();
        }
    }
    private void merge(){
        //todo
    }


    public static void main(String[] args){
        int[] a = {2,5,4,7,9,1,3,6,8};
        Sorting test = new Sorting(a);

        test.shellSorting();
        System.out.println(Arrays.toString(test.res));
    }
}
