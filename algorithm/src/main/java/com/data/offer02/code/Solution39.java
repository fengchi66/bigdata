package com.data.offer02.code;

import java.util.Stack;

// 直方图的最大矩形面积
public class Solution39 {
    public int largestRectangleArea(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;

        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < nums.length; i++) {
            while (!stack.empty() && nums[i] < nums[stack.peek()]) { // 维持递增栈，当前位置小于栈顶，出栈
                int j = stack.pop();
                int left = stack.empty() ? -1 : stack.peek();
                int area = (i - left - 1) * nums[j];
                maxArea = Math.max(maxArea, area);
            }
            stack.push(i);
        }

        // 将栈中剩下的递增的元素弹出，并计算面积
        while (!stack.empty()) {
            int j = stack.pop();
            int left = stack.empty() ? -1 : stack.peek();
            int area = (nums.length - left - 1) * nums[j];
            maxArea = Math.max(maxArea, area);
        }
        return maxArea;
    }
}
