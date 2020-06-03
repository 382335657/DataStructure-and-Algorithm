package Chapter1_recursion;

public class CountBinaryOne {
    private int n;
    private StringBuffer binary_sb = new StringBuffer("");
    private int countOne;

    public CountBinaryOne(int n){
        this.n = n;
        breakdown(n,binary_sb);
    }

    public void breakdown(int n,StringBuffer binary_sb){
        if(n>0){
            breakdown(n/2,binary_sb);
            binary_sb.append(n%2);
            if(n%2==1)
                countOne++;
        }
    }

    public String toString(){
        return binary_sb.toString();
    }

    public static void main(String[] args){
        CountBinaryOne test = new CountBinaryOne(52);
        System.out.println(test.toString());
        System.out.println(test.countOne);
    }
}
