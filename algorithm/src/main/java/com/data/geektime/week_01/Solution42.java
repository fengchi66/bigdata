package com.data.geektime.week_01;
import java.util.Stack;

// 接雨水
public class Solution42 {

  public int trap(int[] height) {
    Stack<Integer> stack = new Stack<>();
    int ans = 0;

    for (int i = 0; i < height.length; i++) {
      // 维持最小栈
      while (!stack.empty() && height[i] > height[stack.peek()]) {
        int pop = stack.pop();
        // 此时栈为空，说明左边没有柱子，一定接不住雨水
        if (stack.empty()) {
          break;
        }

        int left = stack.peek();
        int s = i - left - 1;
        // 高度：左、右柱子的最低高度减去底边柱子
        int h = Math.min(height[i], height[left]) - height[pop];
        ans += s * h;
      }
      stack.push(i);
    }
    return ans;
  }
}
