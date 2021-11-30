//给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。 
//
// 
//
// 示例 1： 
//
// 
//输入：head = [1,2,2,1]
//输出：true
// 
//
// 示例 2： 
//
// 
//输入：head = [1,2]
//输出：false
// 
//
// 
//
// 提示： 
//
// 
// 链表中节点数目在范围[1, 10⁵] 内 
// 0 <= Node.val <= 9 
// 
//
// 
//
// 进阶：你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？ 
// Related Topics 栈 递归 链表 双指针 👍 1184 👎 0

package leetcode.editor.cn;

// 234.回文链表
public class Code234_PalindromeLinkedList {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for singly-linked list. public class ListNode { int val; ListNode next; ListNode()
   * {} ListNode(int val) { this.val = val; } ListNode(int val, ListNode next) { this.val = val;
   * this.next = next; } }
   */
  class Solution {

    public boolean isPalindrome(ListNode head) {
      if (head == null || head.next == null) {
        return true;
      }

      // getMid
      ListNode mid = getMid(head);
      ListNode l2 = mid;
//      mid.next = null;

      l2 = reverseList(l2);

      ListNode l1 = head;

      while (l1 != null && l2 != null) {
        if (l1.val != l2.val) {
          return false;
        }
        l1 = l1.next;
        l2 = l2.next;
      }

      return true;
    }

    private ListNode getMid(ListNode head) {
      ListNode slow = head;
      ListNode fast = head;

      while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
      }
      return slow;
    }

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
  }
//leetcode submit region end(Prohibit modification and deletion)

}