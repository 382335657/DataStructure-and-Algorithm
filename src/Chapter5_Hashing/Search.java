package Chapter5_Hashing;

public class Search {
    //二分查找 O(logn)
    public static int binartSearch(Comparable[] a, int first, int size, Comparable target){
        int middle;
        int comparison;
        if(size<=0)
            return -1;
        else{
            middle = first+size/2;
            comparison = target.compareTo(a[middle]);
            if(comparison == 0) return middle;
            else if(comparison<0)
                return binartSearch(a,first,size/2,target);
            else
                return binartSearch(a,middle+1,(size-1)/2,target);
        }
    }
}
