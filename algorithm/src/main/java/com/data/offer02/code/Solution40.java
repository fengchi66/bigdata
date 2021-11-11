package com.data.offer02.code;

import java.util.Stack;

// 矩阵中的最大矩形
public class Solution40 {

  // 将矩阵转化为直方图，变成39题的问题
  public int maximalRectangle(char[][] matrix) {
    if (matrix.length == 0) {
      return 0;
    }
    int[] heights = new int[matrix[0].length];
    int maxArea = 0;
    for (int row = 0; row < matrix.length; row++) {
      //遍历每一列，更新高度
      for (int col = 0; col < matrix[0].length; col++) {
        if (matrix[row][col] == '1') {
          heights[col] += 1;
        } else {
          heights[col] = 0;
        }
      }
      //调用上一题的解法，更新函数
      maxArea = Math.max(maxArea, largestRectangleArea(heights));
    }
    return maxArea;
  }

  private int largestRectangleArea(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }

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
