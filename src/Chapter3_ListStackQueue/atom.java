package Chapter3_ListStackQueue;

public class atom {
    private atom link;
    private int index; //指数
    private int coefficient;  //系数

    public atom(int coefficient,int index, atom link){
        this.link = link;
        this.index = index;
        this.coefficient = coefficient;
    }

    public atom getLink() { return link; }
    public void setLink(atom link) { this.link = link; }
    public int getIndex() { return index; }
    public void setIndex(int index) { this.index = index; }
    public int getCoefficient() { return coefficient; }
    public void setCoefficient(int coefficient) { this.coefficient = coefficient; }

    public void addAtomAfter(int coefficient, int index){
        link = new atom(coefficient, index, link);
    }

    public static void print(atom head){
        atom cursor;
        for(cursor = head; cursor!=null; cursor = cursor.link)
            if(cursor.coefficient!=0){
                if(cursor.index==0)
                    System.out.print(cursor.coefficient);
                else if(cursor.index==1)
                    System.out.print(cursor.coefficient+"x");
                else
                    System.out.print(cursor.coefficient+"x^"+cursor.index);

                if(cursor.link != null) System.out.print(" + ");
                else System.out.println();
            }
    }
}
