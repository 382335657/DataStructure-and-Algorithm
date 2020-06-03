package Chapter4_Tree;

import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {
    public static final Pattern UNSIGNED_DOUBLE =  //浮点数
            Pattern.compile("((\\d+\\.?\\d*)|(\\.\\d+))([Ee][+-]?\\d+)?.*?");
    public static final Pattern CHARACTER = Pattern.compile("\\S.*?");

    private String middle;  //中缀
    private String post;   //后缀
    private BTNode root;
    private double value;

    public Expression(String expression){
        middle = expression;
        post = translate(middle);
        makeTree(post);
        value = evaluate(root);
        middle = addBraces(root);
    }

    private BTNode makeTree(String expression) {
        Scanner input = new Scanner(expression);
        String next;
        Stack<BTNode> node = new Stack();

        while(input.hasNext()){
            if(input.hasNext(UNSIGNED_DOUBLE)){
                next = input.findInLine(UNSIGNED_DOUBLE);
                node.push(new BTNode<String>(next));
            }
            else if(input.hasNext(CHARACTER)){
                next = input.findInLine(CHARACTER);
                BTNode right = node.pop();
                BTNode left = node.pop();
                node.push(new BTNode(next,left,right));
            }
        }
        root = node.pop();
        return root;
    }

    public double evaluate(BTNode root){
        int result = 0;
        if ("+".equals(root.getData()))
            result = (int) evaluate(root.getLeft()) + (int) evaluate(root.getRight());
        else if("-".equals(root.getData()))
            result = (int) evaluate(root.getLeft()) - (int) evaluate(root.getRight());
        else if("*".equals(root.getData()))
            result = (int) evaluate(root.getLeft()) * (int) evaluate(root.getRight());
        else if("/".equals(root.getData()))
            result = (int) evaluate(root.getLeft()) / (int) evaluate(root.getRight());
        else
            result = Integer.valueOf((String)root.getData());
        return result;
    }

    /**
     * 将中缀表达式转换成后缀表达式
     * 遇到操作数 直接输出
     * 遇到运算符：比较当前运算符与栈顶运算符的优先级，若当前运算符的优先级<=栈顶运算符的优先级，则不断取出运算符栈顶输出；
     *            否则进栈。
     * 遇到左括号：压栈
     * 遇到右括号：不断退栈输出，直到遇到左括号
     * @param expression 一个完全括号化的式子
     * @return
     */
    public String translate(String expression){
        Stack<Character> stack = new Stack<Character>();
        StringBuffer sb = new StringBuffer();
        String next;
        Scanner input = new Scanner(expression);
        while(input.hasNext()){
            if(input.hasNext(UNSIGNED_DOUBLE)) {//如果下一个token的开始是一个无符号双精度浮点数，返回true
                next = input.findInLine(UNSIGNED_DOUBLE);
                sb.append(next);
                sb.append(" ");
            }
            else{
                next = input.findInLine(CHARACTER);
                if(next.charAt(0) == '(')
                    stack.push(next.charAt(0));
                else if(next.charAt(0) == ')'){
                    while(stack.peek()!='(' && !stack.isEmpty()){
                        sb.append(stack.pop());
                        sb.append(" ");
                    }
                    if(stack.peek() == '(')
                        stack.pop();
                    else
                        throw new IllegalArgumentException("Illegal input expression");
                }
                else if(next.charAt(0) == '+' || next.charAt(0) == '-')
                    if(stack.isEmpty() || stack.peek() == '(')
                        stack.push(next.charAt(0));
                    else{
                        do{
                            sb.append(stack.pop());
                            sb.append(" ");
                        }while(!stack.isEmpty() && stack.peek() != '(');
                        stack.push(next.charAt(0));
                    }
                else if(next.charAt(0) == '*' || next.charAt(0) == '/')
                    stack.push(next.charAt(0));
                else
                    throw new IllegalArgumentException("Illegal character");
            }
        }
        while(!stack.isEmpty()){
            sb.append(stack.pop());
            sb.append(" ");
        }
        return sb.toString();
    }

    public double evaluateFromPost(String post){
        Stack<Double> stack = new Stack<Double>();
        String next;
        Scanner input = new Scanner(post);
        while(input.hasNext()){
            if(input.hasNext(UNSIGNED_DOUBLE)) {//如果下一个token的开始是一个无符号双精度浮点数，返回true
                next = input.findInLine(UNSIGNED_DOUBLE);
                stack.push(Double.valueOf(next));
            }
            else if(input.hasNext(CHARACTER)){
                double number2 = stack.pop();
                double number1 = stack.pop();
                next = input.findInLine(CHARACTER);
                switch(next){
                    case "+":
                        stack.push(number1+number2);
                        break;
                    case "-":
                        stack.push(number1-number2);
                        break;
                    case "*":
                        stack.push(number1*number2);
                        break;
                    case "/":
                        stack.push(number1/number2);
                        break;
                    default:
                        ;
                }
            }
        }
        return stack.pop();
    }

    /**
     * 将中缀表达式完全括号化
     * @param root
     * @return 完全括号化的式子
     */
    private static String addBraces(BTNode root){
        String string = "";
        Matcher m =UNSIGNED_DOUBLE.matcher((CharSequence) root.getData());

        if(!m.matches())
            string +="(";

        if(root.getLeft()!=null)
            string += addBraces(root.getLeft());
        string +=root.getData();
        if(root.getRight()!=null)
            string += addBraces(root.getRight());

        if(!m.matches())
            string +=")";
        return string;
    }

    /**
     * 中缀表示法求值
     * @param expression  一个完全括号化了的算术表达式
     * @return
     */
    public static double evaluateFromMiddle(String expression){
        Stack<Double> numbers = new Stack<Double>();
        Stack<Character> operations = new Stack<Character>();

        Scanner input =  new Scanner(expression);
        String next;

        while(input.hasNext()){
            if(input.hasNext(UNSIGNED_DOUBLE)) {//如果下一个token的开始是一个无符号双精度浮点数，返回true
                next = input.findInLine(UNSIGNED_DOUBLE);
                numbers.push(new Double(next));
            }
            else{//如果下一个是运算符
                next = input.findInLine(CHARACTER);
                switch(next.charAt(0)){
                    case '+'://加法
                    case '-'://减法
                    case '*'://乘法
                    case '/'://除法
                        operations.push(next.charAt(0));
                        break;
                    case '(':break;
                    case ')'://右括号
                        evaluateStackTops(numbers,operations);
                        break;
                    default://非法字符
                        throw new IllegalArgumentException("Illegal character");
                }
            }
        }
        if(numbers.size() != 1)
            throw new IllegalArgumentException("Illegal input expression" );
        return numbers.pop();
    }
    //从栈中取出两个数值，并执行一次运算
    private static void evaluateStackTops(Stack<Double> numbers, Stack<Character> operations) {
        double operand1,operand2;

        //检查是否有足够的数据项
        if((numbers.size()<2)||(operations.isEmpty()))
            throw new IllegalArgumentException("Illegal expression");
        operand2 = numbers.pop();
        operand1  = numbers.pop();
        switch(operations.pop()){
            case '+': numbers.push(operand1+operand2);
                break;
            case '-': numbers.push(operand1-operand2);
                break;
            case '*': numbers.push(operand1*operand2);
                break;
            case '/': numbers.push(operand1/operand2);
                break;
            default:throw new IllegalArgumentException("Illegal operation");
        }
    }

    public static void main(String[] args){
        String[] tests = {"((6+9)/3)*(6-4)",
                "3*5+(13-12)+5",
                "(1+7)*(10/2)-2*9",
                "9+(3-1)*3+10/2"};

        for(int i=0;i<tests.length;i++){
            Expression test = new Expression(tests[i]);
            System.out.println("后缀表达式： "+test.post);
            System.out.println("后缀表达式求值结果： "+tests[i]+" = "+test.evaluateFromPost(test.post));
            System.out.println("中缀表达式求值结果： "+test.middle+" = "+evaluateFromMiddle(test.middle));
            System.out.println();
        }
    }
}
