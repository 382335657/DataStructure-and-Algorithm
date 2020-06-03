package Chapter1_recursion;

public class Combination {
    private int n,r;
    private int[] a;

    public Combination(int n, int r){
        this.n = n;
        this.r = r;
        a = new int[r];
        comb(1,0);
    }
    //count记录数字的个数   i记录数字的大小
    public void comb(int i, int count) {
        if(count == r){
            print(a);
            return;
        }
        if(i>n) return;
        a[count] = i;
        comb(i+1,count+1);
        comb(i+1,count);
    }
    private void print(int[] a){
        for(int number:a)
            System.out.print(number+" ");
        System.out.println();
    }

    public static void main(String[] args) {
        Combination test = new Combination(5,3);
        Permutation t = new Permutation("abc");
    }
}

class Permutation<E>{
    private E[] a;

    public Permutation(String s){
        a = (E[]) s.split("");
        perm(a,0);
    }

    public void perm(E[] array, int start){
        int i;
        if(start == array.length)
            print(a);
        else{
            for(i=start; i<array.length; i++){
                swap(array,start,i);
                perm(a,start+1);
                swap(array,start,i);
            }
        }
    }
    private void swap(E[] array, int m,int n){
        E temp = array[m];
        array[m] = array[n];
        array[n] = temp;
    }

    private void swap(char[] array, int m, int n){
        char temp = array[m];
        array[m]=array[n];
        array[n] = temp;
    }
    private void print(E[] data){
        for(E element: data)
            System.out.print(element);
        System.out.println();
    }
}
