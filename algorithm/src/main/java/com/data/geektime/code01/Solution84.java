package com.data.geektime.code01;

import java.util.Stack;

// 柱状图中最大矩形
public class Solution84 {

  public int largestRectangleArea(int[] heights) {

    // 固定一个高，向左右两边扩展，知道遇到矮的柱子，因为位置维持一个递增栈
    Stack<Integer> stack = new Stack<>();
    int maxArea = 0;

    for (int i = 0; i < heights.length; i++) {
      while (!stack.empty() && heights[i] < heights[stack.peek()]) { // 当前柱子比栈顶低
        int pop = stack.pop(); // 栈顶元素
        int left = !stack.empty() ? stack.peek() : -1;
        int area = (i - left - 1) * heights[pop];
        maxArea = Math.max(maxArea, area);
      }
      stack.push(i);
    }

    while (!stack.empty()) {
      int pop = stack.pop(); // 栈顶元素
      int left = !stack.empty() ? stack.peek() : -1;
      int area = (heights.length - left - 1) * heights[pop];
      maxArea = Math.max(maxArea, area);
    }
    return maxArea;
  }
}
