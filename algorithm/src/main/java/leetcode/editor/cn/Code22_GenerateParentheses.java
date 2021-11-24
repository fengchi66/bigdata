//数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。 
//
// 有效括号组合需满足：左括号必须以正确的顺序闭合。 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 3
//输出：["((()))","(()())","(())()","()(())","()()()"]
// 
//
// 示例 2： 
//
// 
//输入：n = 1
//输出：["()"]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= n <= 8 
// 
// Related Topics 字符串 动态规划 回溯 👍 2168 👎 0

package leetcode.editor.cn;

import java.util.LinkedList;
import java.util.List;

public class Code22_GenerateParentheses {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public List<String> generateParenthesis(int n) {
      List<String> result = new LinkedList<>();
      helper(n, n, "", result);
      return result;
    }

    private void helper(int left, int right,
        String parenthesis, List<String> result) {
      if (left == 0 && right == 0) {
        result.add(parenthesis);
        return;
      }

      // 在什么样的情况下可以生成左括号和右括号
      if (left > 0) {
        helper(left - 1, right, parenthesis + "(", result);
      }

      if (left < right) {
        helper(left, right - 1, parenthesis + ")", result);
      }
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}