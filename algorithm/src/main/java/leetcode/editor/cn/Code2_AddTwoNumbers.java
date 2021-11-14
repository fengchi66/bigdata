//给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。 
//
// 请你将两个数相加，并以相同形式返回一个表示和的链表。 
//
// 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。 
//
// 
//
// 示例 1： 
//
// 
//输入：l1 = [2,4,3], l2 = [5,6,4]
//输出：[7,0,8]
//解释：342 + 465 = 807.
// 
//
// 示例 2： 
//
// 
//输入：l1 = [0], l2 = [0]
//输出：[0]
// 
//
// 示例 3： 
//
// 
//输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
//输出：[8,9,9,9,0,0,0,1]
// 
//
// 
//
// 提示： 
//
// 
// 每个链表中的节点数在范围 [1, 100] 内 
// 0 <= Node.val <= 9 
// 题目数据保证列表表示的数字不含前导零 
// 
// Related Topics 递归 链表 数学 👍 7058 👎 0

package leetcode.editor.cn;

public class Code2_AddTwoNumbers {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for singly-linked list. public class ListNode { int val; ListNode next; ListNode()
   * {} ListNode(int val) { this.val = val; } ListNode(int val, ListNode next) { this.val = val;
   * this.next = next; } }
   */
  class Solution {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
      // 1.先将两个链表反转
      l1 = reverseList(l1);
      l2 = reverseList(l2);
      return reverseList(addList(l1, l2));
    }

    // 反转链表
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

    private ListNode addList(ListNode l1, ListNode l2) {
      ListNode dummyHead = new ListNode(0);
      ListNode pre = dummyHead;

      int curry = 0;
      // 两数相加
      while (l1 != null || l2 != null) {
        int sum = (l1 != null ? l1.val : 0) + (l2 != null ? l2.val : 0) + curry;
        curry = sum >= 10 ? 1 : 0;
        sum = sum >= 10 ? sum - 10 : sum;

        ListNode node = new ListNode(sum);
        pre.next = node;
        pre = pre.next;

        l1 = l1 != null ? l1.next : null;
        l2 = l2 != null ? l2.next : null;
      }

      if (curry > 0) {
        pre.next = new ListNode(curry);
        pre = pre.next;
      }
      return dummyHead.next;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)

}