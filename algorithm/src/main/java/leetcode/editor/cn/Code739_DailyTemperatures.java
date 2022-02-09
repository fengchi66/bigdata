//请根据每日 气温 列表 temperatures ，请计算在每一天需要等几天才会有更高的温度。如果气温在这之后都不会升高，请在该位置用 0 来代替。 
//
// 示例 1: 
//
// 
//输入: temperatures = [73,74,75,71,69,72,76,73]
//输出: [1,1,4,2,1,1,0,0]
// 
//
// 示例 2: 
//
// 
//输入: temperatures = [30,40,50,60]
//输出: [1,1,1,0]
// 
//
// 示例 3: 
//
// 
//输入: temperatures = [30,60,90]
//输出: [1,1,0] 
//
// 
//
// 提示： 
//
// 
// 1 <= temperatures.length <= 10⁵ 
// 30 <= temperatures[i] <= 100 
// 
// Related Topics 栈 数组 单调栈 👍 1011 👎 0

package leetcode.editor.cn;

import java.util.Stack;

// 739.每日温度
public class Code739_DailyTemperatures {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    // 计算在每一天需要等几天才会有更高的温度
    // 递减栈
    public int[] dailyTemperatures(int[] temperatures) {
      int n = temperatures.length;
      int[] ans = new int[n];
      Stack<Integer> stack = new Stack<>();

      for (int i = 0; i < n; i++) {
        // 当出现递增的时候，打破递减栈的原则
        while (!stack.empty() && temperatures[stack.peek()] < temperatures[i]) {
          // 栈顶元素就是要计算的位置
          Integer pop = stack.pop();
          ans[pop] = i - pop;
        }
        stack.push(i);
      }
      return ans;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}