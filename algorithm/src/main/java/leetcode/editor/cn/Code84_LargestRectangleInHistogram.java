//给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。 
//
// 求在该柱状图中，能够勾勒出来的矩形的最大面积。 
//
// 
//
// 示例 1: 
//
// 
//
// 
//输入：heights = [2,1,5,6,2,3]
//输出：10
//解释：最大的矩形为图中红色区域，面积为 10
// 
//
// 示例 2： 
//
// 
//
// 
//输入： heights = [2,4]
//输出： 4 
//
// 
//
// 提示： 
//
// 
// 1 <= heights.length <=10⁵ 
// 0 <= heights[i] <= 10⁴ 
// 
// Related Topics 栈 数组 单调栈 👍 1741 👎 0

package leetcode.editor.cn;

import java.util.Stack;

// 84.柱状图中最大的矩形
public class Code84_LargestRectangleInHistogram {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    // 递增栈：找两边比它小的元素,保持递增，当出现大的元素时，打破递增原则
    public int largestRectangleArea(int[] heights) {

      int ans = 0;
      int n = heights.length;
      Stack<Integer> stack = new Stack<>();

      for (int i = 0; i < n; i++) {
        while (!stack.empty() && heights[i] < heights[stack.peek()]) {
          // 栈顶元素
          int pop = stack.pop();
          int left = stack.isEmpty() ? -1 : stack.peek();
          int size = i - left - 1;
          int height = heights[pop];
          ans = Math.max(ans, size * height);
        }
        stack.push(i);
      }

      // 栈中元素全部递增
      while (!stack.empty()) {
        int pop = stack.pop();
        int left = stack.isEmpty() ? -1 : stack.peek();
        int size = n - left - 1;
        int height = heights[pop];
        ans = Math.max(ans, size * height);
      }
      return ans;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}