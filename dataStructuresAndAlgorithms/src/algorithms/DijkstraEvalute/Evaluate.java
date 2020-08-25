package algorithms.DijkstraEvalute;

import java.util.Scanner;
import java.util.Stack;

public class Evaluate {

    /**
     * 10 + (5*2) + ((2*25)+60/2)
     * vals: 10  5  2
     * opts:   +  *
     *
     * vals: 10  10   2    25
     * opts:   +    +   *
     *
     * vals: 10  10   50    60
     * opts:   +    +     +
     */

    public static void main(String[] args) {
        System.out.println(calculator());;
    }

    public static double calculator() {
        Scanner scanner = new Scanner(System.in);
        String str = null;
        Stack<String> opts = new Stack<String>();
        Stack<Double> vals = new Stack<Double>();

        while (!(str = scanner.next()).isEmpty()) {
            System.out.println("input char" + str);
           if (!str.equals("(")) {
               if (str.equals("+")) {
                    opts.push(str);
               } else if (str.equals("-")) {
                    opts.push(str);
               } else if (str.equals("*")) {
                   opts.push("*");
               } else if (str.equals("/")) {
                   opts.push("/");
               } else if (str.equals("sqrt")) {
                   opts.push("sqrt");
               } else if (str.equals(")")) {
                   if (opts.size() > 0) {
                   String opinion = opts.pop();
                   double val = vals.pop();
                   if (opinion.equals("+"))
                       vals.push(val + vals.pop());
                   if (opinion.equals("-"))
                       vals.push(val - vals.pop());
                   if (opinion.equals("*"))
                       vals.push(val * vals.pop());
                   if (opinion.equals("/"))
                       vals.push(vals.pop() / val);
                   if (opinion.equals("sqrt"))
                       vals.push(Math.sqrt(val));
                   }

               } else if (str.equals("end")){
                   if (opts.size() > 0) {
                        while (vals.size() > 1) {
                            switch (opts.pop()){
                                case "+":
                                    vals.push(vals.pop() + vals.pop());
                                  break;
                                 case "-":
                                     vals.push(vals.pop() - vals.pop());
                                     break;

                                case "*":
                                     vals.push(vals.pop() * vals.pop());
                                    break;

                                case "/":
                                     vals.push(vals.pop() / vals.pop());
                                    break;

                                case "sqrt":
                                     vals.push(Math.sqrt(vals.pop()));
                                    break;
                                default:
                                    break;
                            }
                        }
                   }
                   break;
               } else
                   vals.push(Double.valueOf(str));
           }
        }

        return vals.pop();

    }
}
