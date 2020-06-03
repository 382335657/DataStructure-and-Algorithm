package Chapter1_recursion;

public class Calculation {
    private int[] array;
    private int maxNumber;
    private int average;

    public Calculation(int[] array){
        this.array = array;
        maxNumber = findMax(array[0],0);
        average = calSum(0)/array.length;
    }

    private int calSum(int point) {
        if(point<array.length)
            return array[point]+calSum(point+1);
        else
            return 0;
    }

    private int findMax(int tempMax,int point){
        if(point<array.length){
            if(array[point]>tempMax)
                tempMax = array[point];
            tempMax = findMax(tempMax,point+1);
        }
        return tempMax;
    }
    public static void main(String[] args){
        Calculation test = new Calculation(new int[]{5,2,3,6,4});
        System.out.println(test.maxNumber);
        System.out.println(test.average);
    }
}
