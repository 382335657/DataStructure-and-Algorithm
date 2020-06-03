package Chapter1_recursion;

public class Hanoi<E> {
    static String from = "fromTower";
    static String target = "toTower";
    static String auxillary = "auxTower";

    public static void solve(int n){
        moveDisk(n,from,target,auxillary);
    }
    public static void moveDisk(int numOfDisk, String from, String target, String auxiliary){
        if(numOfDisk>1){
            //将n-1个盘子从当前的柱子上移动到辅助柱子上
            moveDisk(numOfDisk-1,from, auxiliary, target);
            //将最大的盘子移动到目标柱子上
            System.out.println("Move Disk"+numOfDisk+": "+from+" --> "+target);
            //将n-1个盘子从辅助柱子上移动到目标柱子上
            moveDisk(numOfDisk-1,auxiliary,target,from);
        }
        else
            System.out.println("Move Disk1: "+from+" --> "+target);
    }
    public static void main(String[] args){
        solve(3);
    }
}
