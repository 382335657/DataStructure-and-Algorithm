package Chapter3_ListStackQueue;

import java.util.regex.Pattern;

public class Polynomial {
    private atom head;
    private int manyNodes;

    public Polynomial(){ head = new atom(0,0,null);}

    public static final Pattern UNSIGNED_DOUBLE =
            Pattern.compile("((\\d+\\.?\\d*)|(\\.\\d+))([Ee][+-]?\\d+)?.*?");
    public static final Pattern CHARACTER = Pattern.compile("\\S.*?");

    /**
     * 实现多项式的加法
     * @param poly1
     * @param poly2
     * @return
     */
    public static Polynomial add(Polynomial poly1, Polynomial poly2){
        //initiate
        Polynomial res = new Polynomial();
        //res.head = new atom(0,0,null);

        //cursors
        atom cursor1 = poly1.head;
        atom cursor2 = poly2.head;
        atom cursor = res.head;

        //calculate
        while(cursor1 != null && cursor2 != null){
            if(cursor1.getIndex()>cursor2.getIndex()){
                cursor.addAtomAfter(cursor1.getCoefficient(),cursor1.getIndex());
                cursor1 = cursor1.getLink();
            }
            else if(cursor1.getIndex()==cursor2.getIndex()) {
                cursor.addAtomAfter(cursor1.getCoefficient() + cursor2.getCoefficient(), cursor1.getIndex());
                cursor1 = cursor1.getLink();
                cursor2 = cursor2.getLink();
            }
            else{
                cursor.addAtomAfter(cursor2.getCoefficient(),cursor2.getIndex());
                cursor2 = cursor2.getLink();
            }
            cursor = cursor.getLink();
        }
        //add the rest to the result
        for(;cursor1!=null; cursor1 = cursor1.getLink(),cursor = cursor.getLink())
            cursor.addAtomAfter(cursor1.getCoefficient(),cursor1.getIndex());
        for(;cursor2!=null; cursor2 = cursor2.getLink(),cursor = cursor.getLink())
            cursor.addAtomAfter(cursor2.getCoefficient(),cursor2.getIndex());

        res.head = res.head.getLink();
        return res;
    }

    /**
     * 实现多项式的乘法
     * @param poly1
     * @param poly2
     * @return
     */
    public static Polynomial multiply(Polynomial poly1, Polynomial poly2){
        Polynomial res = new Polynomial();
       // res.head = new atom(0,0,null);
        atom cursor1;
        atom cursor2;
        for(cursor1 = poly1.head;cursor1!=null;cursor1 = cursor1.getLink()){
            Polynomial temp = new Polynomial();
            //temp.head = new atom(0,0,null);
            atom cursor = temp.head;
            for(cursor2 = poly2.head;cursor2!=null;cursor2 = cursor2.getLink()){
                cursor.addAtomAfter(cursor1.getCoefficient()*cursor2.getCoefficient(),
                        cursor1.getIndex()+cursor2.getIndex());
                cursor = cursor.getLink();
            }
            res = add(res,temp);   //将每一轮的乘积加到res中去
        }
        return res;
    }

    public static void main(String[] args) {
        String[] test ={"5x^5 + 4x^4 + 3x^3 + 2x^2 + 1x^1 + 8",
                        "4x^7 + 6x^5 + 4",
                        "10x^3 + 8x^2 + 2x^1",
                        "14x^10+14x^9+1"};

        String test1 =test[0];
        Polynomial poly1 = read(test1);
        String test2 = test[1];
        Polynomial poly2 = read(test2);

        System.out.print("The first polynomial is: ");
        atom.print(poly1.head);
        System.out.print("The second polynomial is: ");
        atom.print(poly2.head);
        System.out.print("The result of addtion is: ");
        atom.print(add(poly1,poly2).head);
        System.out.print("The result of multiplication is: ");
        atom.print(multiply(poly1,poly2).head);


        System.out.println();
        test1 =test[0];
        poly1 = read(test1);
        test2 = test[2];
        poly2 = read(test2);

        System.out.print("The first polynomial is: ");
        atom.print(poly1.head);
        System.out.print("The second polynomial is: ");
        atom.print(poly2.head);
        System.out.print("The result of addtion is: ");
        atom.print(add(poly1,poly2).head);
        System.out.print("The result of multiplication is: ");
        atom.print(multiply(poly1,poly2).head);

    }

    public static Polynomial read(String expression){
        int count = 0;
        Polynomial res = new Polynomial();
        atom cursor = res.head;

        int coefficient = 0;
        int index = 0;
        while(count<expression.length()){
            char ch = expression.charAt(count);
            if(ch<='9' && ch>='0'){
                while(ch<='9'&& ch>='0'){
                    coefficient = coefficient*10 + Character.getNumericValue(ch);
                    if(++count != expression.length())
                        ch = expression.charAt(count);
                    else break;
                }
            }
            else if(ch == 'x'){
                ch = expression.charAt(++count);
                if(ch == '^'){
                    ch = expression.charAt(++count);
                    while(ch<='9'&& ch>='0'){
                        index = index*10 + Character.getNumericValue(ch);
                        if(++count != expression.length())
                            ch = expression.charAt(count);
                        else break;
                    }
                }
                else{
                    index = 1;
                    count++;
                }
                cursor.addAtomAfter(coefficient,index);
                cursor = cursor.getLink();
                coefficient = 0;
                index = 0;
            }
            else count++;
        }
        if(coefficient!=0)
            cursor.addAtomAfter(coefficient,0);
        res.head = res.head.getLink();
        return res;
    }
}
