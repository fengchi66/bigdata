//给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。 
//
// 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。 
//
// 
//
// 示例 1： 
//
// 
//输入：head = [1,2,3,4]
//输出：[2,1,4,3]
// 
//
// 示例 2： 
//
// 
//输入：head = []
//输出：[]
// 
//
// 示例 3： 
//
// 
//输入：head = [1]
//输出：[1]
// 
//
// 
//
// 提示： 
//
// 
// 链表中节点的数目在范围 [0, 100] 内 
// 0 <= Node.val <= 100 
// 
//
// 
//
// 进阶：你能在不修改链表节点值的情况下解决这个问题吗?（也就是说，仅修改节点本身。） 
// Related Topics 递归 链表 👍 1114 👎 0

package leetcode.editor.cn;

public class Code24_SwapNodesInPairs {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for singly-linked list. public class ListNode { int val; ListNode next; ListNode()
   * {} ListNode(int val) { this.val = val; } ListNode(int val, ListNode next) { this.val = val;
   * this.next = next; } }
   */
  class Solution {

    // 两两交换链表中的节点
    public ListNode swapPairs(ListNode head) {
      if (head == null || head.next == null) {
        return head;
      }

      ListNode dummy = new ListNode(0);
      ListNode last = dummy;

      while (head != null) {
        // 找到头结点对应的尾结点
        ListNode end = head.next;
        // 边界
        if (end == null) {
          break;
        }
        // 记下end.next
        ListNode next = end.next;

        // 反转
        end.next = head;

        // 上一组和本组的新head相连
        last.next = end;
        // 本组的新end和下一组的head相连
        head.next = next;

        // 更新last和head(更新到下一组交换)
        last = head; // head也就是本组的新的end
        head = next; // next是下一组的head
      }
      return dummy.next;

    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}