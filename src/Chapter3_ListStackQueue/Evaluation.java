package Chapter3_ListStackQueue;

import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

public class Evaluation {
    public static final Pattern UNSIGNED_DOUBLE =
            Pattern.compile("((\\d+\\.?\\d*)|(\\.\\d+))([Ee][+-]?\\d+)?.*?");
    public static final Pattern CHARACTER = Pattern.compile("\\S.*?");

    public static void main(String[] args) {
        String[] tests = {"(((6+9)/3)*(6-4))",
                "3*5+(13-12)+5",
                "(1+7)*(10/2)-2*9",
                "9+(3-1)*3+10/2"};

        for (int i = 0; i < tests.length; i++) {
            System.out.println(tests[i]);
            String temp = polish(tests[i]);
            System.out.println(temp);
            System.out.println(evaluateFromMiddle(temp));
        }
    }

    /**
     * 将中缀表达式完全括号化
     * @param expression
     * @return
     */
    public static String polish(String expression){
        //todo
        return null;
    }

    /**
     * 中缀表达式求值
     * @param expression  完全括号化的式子
     */
    public static double evaluateFromMiddle(String expression){
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operations = new Stack<>();

        Scanner input = new Scanner(expression);
        String next;

        while(input.hasNext()){
            if(input.hasNext(UNSIGNED_DOUBLE)){
                next = input.findInLine(UNSIGNED_DOUBLE);
                numbers.push(new Double(next));
            }
            else{
                next = input.findInLine(CHARACTER);
                switch(next.charAt(0)){
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                        operations.push(next.charAt(0));
                        break;
                    case '(':break;
                    case ')':
                        evaluateStackTops(numbers,operations);
                        break;
                    default:
                        throw new IllegalArgumentException("Illegal character");
                }
            }
        }
        if(numbers.size()!=1)
            throw new IllegalArgumentException("Illeagal input expression");
        return numbers.pop();
    }
    public static void evaluateStackTops(Stack<Double> numbers, Stack<Character> operations){
        double operand1,operand2;

        if(numbers.size()<2 || operations.isEmpty())
            throw new IllegalArgumentException("Illegal expression");
        operand2 = numbers.pop();
        operand1 = numbers.pop();

        switch(operations.pop()){
            case '+':numbers.push(operand1+operand2); break;
            case '-':numbers.push(operand2-operand1); break;
            case '*':numbers.push(operand1*operand2); break;
            case '/':numbers.push(operand2/operand1); break;
            default: throw new IllegalArgumentException("Illegal operation");
        }
    }

}
