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

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

// 22.括号生成
public class Code22_GenerateParentheses {

  public static void main(String[] args) {
    Solution solution = new Solution();
    System.out.println(solution.generateParenthesis(3));
  }


  //leetcode submit region begin(Prohibit modification and deletion)
  static class Solution {

    public List<String> generateParenthesis(int n) {
      List<String> ans = new LinkedList<>();

      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < n; i++) {
        builder.append('(');
        builder.append(')');
      }
      char[] chars = builder.toString().toCharArray();

      System.out.println(chars);

      System.out.println("=======================");

      dfs(chars, ans, 0, new StringBuilder());
      return ans;
    }

    // 递归：回溯
    private void dfs(char[] chars, List<String> ans, int index, StringBuilder subset) {
      // base case
      if (index == chars.length) {
        String s = new String(subset);
        ans.add(s);
      } else if (index < chars.length) {
        // 还需要将index位置的字符加入到subset中
        subset.append(chars[index]);
        dfs(chars, ans, index + 1, subset);
//        subset.deleteCharAt(index);
      }
    }

    // 判断是否为一个有效的括号
    private boolean isValid(String s) {
      HashMap<Character, Character> map = new HashMap<>();
      map.put('}', '{');
      map.put(']', '[');
      map.put(')', '(');

      Stack<Character> stack = new Stack<>();

      for (int i = 0; i < s.length(); i++) {
        // 遇到左括号就加入到栈中
        if (map.values().contains(s.charAt(i))) {
          stack.push(s.charAt(i));
        } else { // 遇到右括号,看栈顶元素是否是左括号，如果不是，直接返回false
          if (stack.empty()) {
            return false;
          }
          Character ch = stack.pop();
          if (map.get(s.charAt(i)) != ch) {
            return false;
          }
        }
      }
      return stack.empty();
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}