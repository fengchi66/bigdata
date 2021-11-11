package com.data.geektime.code00;

import java.util.HashMap;
import java.util.Stack;

// 有效的括号 ()[]{}
public class Solution20 {
    public boolean isValid(String s) {
        if (s == null || s.length() == 0 || s.length() % 2 != 0) {
            return false;
        }

        HashMap<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            // 每当遇到一个右括号，就去看栈顶中是否是左括号
            if (map.containsKey(ch)) {
                if (stack.empty() || stack.peek() != map.get(ch)) {
                    return false;
                }
                stack.pop();
            } else { // 遍历到的是一个左括号，则加入到栈中
                stack.push(ch);
            }
        }
        return stack.empty();
    }
}
