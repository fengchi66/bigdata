//给定一个单链表 L 的头节点 head ，单链表 L 表示为： 
//
// L0 → L1 → … → Ln-1 → Ln 
//请将其重新排列后变为： 
//
// L0 → Ln → L1 → Ln-1 → L2 → Ln-2 → … 
//
// 不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。 
//
// 
//
// 示例 1: 
//
// 
//
// 
//输入: head = [1,2,3,4]
//输出: [1,4,2,3] 
//
// 示例 2: 
//
// 
//
// 
//输入: head = [1,2,3,4,5]
//输出: [1,5,2,4,3] 
//
// 
//
// 提示： 
//
// 
// 链表的长度范围为 [1, 5 * 10⁴] 
// 1 <= node.val <= 1000 
// 
//
// 
//
// 注意：本题与主站 143 题相同：https://leetcode-cn.com/problems/reorder-list/ 
// Related Topics 栈 递归 链表 双指针 👍 31 👎 0

package leetcode.editor.cn;

// 剑指 Offer II 026.重排链表
class Code_Offer_LGjMqU {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for singly-linked list. public class ListNode { int val; ListNode next; ListNode()
   * {} ListNode(int val) { this.val = val; } ListNode(int val, ListNode next) { this.val = val;
   * this.next = next; } }
   */
  class Solution {

    public void reorderList(ListNode head) {
      if (head == null || head.next == null) {
        return;
      }

      // 先找到链表的中间节点
      ListNode mid = getMid(head);
      ListNode l2 = mid.next;
      mid.next = null;

      // 将l2反转
      l2 = reverseList(l2);

      // merge
      merge(head, l2);

    }

    // 找到链表的中间节点
    private ListNode getMid(ListNode head) {
      ListNode slow = head;
      ListNode fast = head;

      while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
      }
      return slow;
    }

    // 反转
    private ListNode reverseList(ListNode head) {
      ListNode last = null;

      while (head != null) {
        ListNode next = head.next;
        head.next = last;
        last = head;
        head = next;
      }
      return last;
    }

    // 合并
    private void merge(ListNode head1, ListNode head2) {
      ListNode l1 = head1;
      ListNode l2 = head2;

      while (l1 != null && l2 != null) {
        ListNode next1 = l1.next;
        ListNode next2 = l2.next;

        l1.next = l2;
        l1 = next1;

        l2.next = l1;
        l2 = next2;
      }
    }
  }
  //leetcode submit region end(Prohibit modification and deletion)
}