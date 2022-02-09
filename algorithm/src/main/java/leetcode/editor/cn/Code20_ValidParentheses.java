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
// Related Topics 栈 字符串 👍 2948 👎 0

package leetcode.editor.cn;

import java.util.HashMap;
import java.util.Stack;

// 20.有效的括号
public class Code20_ValidParentheses {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public boolean isValid(String s) {

      HashMap<Character, Character> map = new HashMap<>();
      map.put(')', '(');
      map.put(']', '[');
      map.put('}', '{');

      Stack<Character> stack = new Stack<>();

      for (char ch : s.toCharArray()) {
        // 遇到左括号，加入到栈
        if (map.containsValue(ch)) {
          stack.push(ch);
        } else if (map.containsKey(ch) && !stack.isEmpty()) { // 遇到右括号且stack不为空，则从stack中取出看是不是左括号
          Character pop = stack.pop();
         if (map.get(ch) != pop) {
           return false;
         }
        } else {
          return false;
        }
      }
      return stack.isEmpty();
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}