package com.data.offer02.code;

import java.util.Stack;

// 后缀表达式 +、-、*、/
public class Solution36 {
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String c : tokens) {
            switch (c) {
                case "+":
                case "-":
                case "*":
                case "/":
                    int num2 = stack.pop();
                    int num1 = stack.pop();
                    stack.push(calc(num1, num2, c));
                    break;
                default:
                    stack.push(Integer.parseInt(c));
            }
        }
        return stack.pop();
    }

    private int calc(int a, int b, String op) {
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            default:
                return a / b;
        }
    }
}
