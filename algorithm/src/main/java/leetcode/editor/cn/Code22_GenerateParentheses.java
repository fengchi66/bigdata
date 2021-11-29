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

    // 1. 回溯
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
      // 可以生成左括号
      if (left > 0) {
        helper(left - 1, right, parenthesis + "(", result);
      }

      // 可以生成右括号
      if (left < right) {
        helper(left, right - 1, parenthesis + ")", result);
      }
    }

    // 2. 回溯
    public List<String> generateParenthesis2(int n) {
      List<String> ans = new LinkedList<>();

      helper2(n, n, new StringBuilder(), ans);
      return ans;
    }

    // 还剩left个左括号和righ个右括号可以用
    private void helper2(int left, int right, StringBuilder subset, List<String> ans) {
      if (left > right) {
        return;
      }
      if (left < 0 || right < 0) {
        return;
      }
      if (left == 0 && right == 0) {
        ans.add(subset.toString());
      }

      // 添加一个左括号
      subset.append('(');
      helper2(left - 1, right, subset, ans);
      subset.deleteCharAt(subset.length() - 1);

      // 添加一个右括号
      subset.append(')');
      helper2(left, right - 1, subset, ans);
      subset.deleteCharAt(subset.length() - 1);
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}