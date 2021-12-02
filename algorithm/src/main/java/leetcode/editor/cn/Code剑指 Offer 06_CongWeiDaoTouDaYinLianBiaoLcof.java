//输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。
//
// 
//
// 示例 1： 
//
// 输入：head = [1,3,2]
//输出：[2,3,1] 
//
// 
//
// 限制： 
//
// 0 <= 链表长度 <= 10000 
// Related Topics 栈 递归 链表 双指针 👍 210 👎 0

package leetcode.editor.cn;

import java.util.ArrayList;
import java.util.Stack;

// 剑指 Offer 06.从尾到头打印链表
class Code_Offer_CongWeiDaoTouDaYinLianBiaoLcof {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for singly-linked list. public class ListNode { int val; ListNode next; ListNode(int
   * x) { val = x; } }
   */
  class Solution {

    public int[] reversePrint(ListNode head) {
      Stack<Integer> stack = new Stack<>();
      ListNode cur = head;
      while (cur != null) {
        stack.push(cur.val);
        cur = cur.next;
      }
      int[] ans = new int[stack.size()];
      int i = 0;
      while (!stack.empty()) {
        ans[i] = stack.pop();
        i++;
      }

      return ans;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}