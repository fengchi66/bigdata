//给你单链表的头指针 head 和两个整数 left 和 right ，其中 left <= right 。请你反转从位置 left 到位置 right 的链
//表节点，返回 反转后的链表 。
// 
//
// 示例 1： 
//
// 
//输入：head = [1,2,3,4,5], left = 2, right = 4
//输出：[1,4,3,2,5]
// 
//
// 示例 2： 
//
// 
//输入：head = [5], left = 1, right = 1
//输出：[5]
// 
//
// 
//
// 提示： 
//
// 
// 链表中节点数目为 n 
// 1 <= n <= 500 
// -500 <= Node.val <= 500 
// 1 <= left <= right <= n 
// 
//
// 
//
// 进阶： 你可以使用一趟扫描完成反转吗？ 
// Related Topics 链表 👍 1088 👎 0

package leetcode.editor.cn;

import java.util.function.Predicate;

// 92.反转链表 II
public class Code92_ReverseLinkedListIi {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for singly-linked list. public class ListNode { int val; ListNode next; ListNode()
   * {} ListNode(int val) { this.val = val; } ListNode(int val, ListNode next) { this.val = val;
   * this.next = next; } }
   */
  class Solution {

    // 穿针引线
    public ListNode reverseBetween(ListNode head, int left, int right) {
      if (head == null || head.next == null) {
        return head;
      }

      ListNode dummyHead = new ListNode(-1, head);
      ListNode p1 = dummyHead;
      ListNode p2 = dummyHead;
      ListNode p3 = dummyHead;

      // 从dummyHead出发，走left-1步，来到left的上一个节点
      for (int i = 0; i < left - 1; i++) {
        p1 = p1.next;
      }
      // 从从dummyHead出发，走right步，来到right的上一个节点
      for (int i = 0; i < right; i++) {
        p2 = p2.next;
      }

      ListNode l2 = p1.next;
      ListNode l3 = p2.next;

      p1.next = null;
      p2.next = null;

      // 将l2部分反转
      l2 = reverseNode(l2);

      p1.next = l2;

      while (p3.next != null) {
        p3 = p3.next;
      }
      p3.next = l3;

      return dummyHead.next;
    }

    private ListNode reverseNode(ListNode head) {
      ListNode last = null;

      while (head != null) {
        ListNode next = head.next;
        head.next = last;
        last = head;
        head = next;
      }
      return last;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}