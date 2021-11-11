package com.data.offer02.code;

import java.util.Stack;

// 每日温度:要想观测到更高的气温，至少需要等待的天数
public class Solution38 {
    public int[] dailyTemperatures(int[] nums) {
        if (nums == null || nums.length == 0)
            return new int[0];

        // 单调递减栈，存储温度的数组下标
        int[] ans = new int[nums.length];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) { // 当前数字比栈顶大，违反单调栈，出栈
                // 当前出栈的元素就是要求的目标元素
                int temp = stack.pop();
                ans[temp] = i - temp;
            }
            stack.push(i);
        }
        return ans;
    }

}
