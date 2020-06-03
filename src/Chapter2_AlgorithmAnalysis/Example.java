package Chapter2_AlgorithmAnalysis;

public class Example {
    /**
     * 二分法查找
     * @param a 事先已排好序的数列
     * @param x 查找对象
     * @return 对象所在位置下表 如没有 返回-1
     */
    public static int BinarySearch(Comparable[] a, Comparable x){
        int low = 0;
        int high = a.length-1;
        while(low<high){
            int mid = (low+high)/2;
            if(a[mid].compareTo(x)<0)
                low = mid+1;
            else if(a[mid].compareTo(x)>0)
                high = mid-1;
            else return mid;
        }
        return -1;
    }

    public static int maxSumRec(int[] a, int left, int right){
        return 0;
    }
}
