//给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。 
//
// 有效字符串需满足： 
//
// 
// 左括号必须用相同类型的右括号闭合。 
// 左括号必须以正确的顺序闭合。 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "()"
//输出：true
// 
//
// 示例 2： 
//
// 
//输入：s = "()[]{}"
//输出：true
// 
//
// 示例 3： 
//
// 
//输入：s = "(]"
//输出：false
// 
//
// 示例 4： 
//
// 
//输入：s = "([)]"
//输出：false
// 
//
// 示例 5： 
//
// 
//输入：s = "{[]}"
//输出：true 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 10⁴ 
// s 仅由括号 '()[]{}' 组成 
// 
// Related Topics 栈 字符串 👍 2781 👎 0

package leetcode.editor.cn;

import java.util.HashMap;
import java.util.Stack;

// 20.有效的括号
public class Code20_ValidParentheses {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public boolean isValid(String s) {
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