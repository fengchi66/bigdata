//给定一个链表的 头节点 head ，请判断其是否为回文链表。 
//
// 如果一个链表是回文，那么链表节点序列从前往后看和从后往前看是相同的。 
//
// 
//
// 示例 1： 
//
// 
//
// 
//输入: head = [1,2,3,3,2,1]
//输出: true 
//
// 示例 2： 
//
// 
//
// 
//输入: head = [1,2]
//输出: false
// 
//
// 
//
// 提示： 
//
// 
// 链表 L 的长度范围为 [1, 10⁵] 
// 0 <= node.val <= 9 
// 
//
// 
//
// 进阶：能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？ 
//
// 
//
// 注意：本题与主站 234 题相同：https://leetcode-cn.com/problems/palindrome-linked-list/ 
// Related Topics 栈 递归 链表 双指针 👍 26 👎 0

package leetcode.editor.cn;

// 剑指 Offer II 027.回文链表
class Code_Offer_AMhZSa {
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

      ListNode mid = getMid(head);
      // 为了尽可能保留headB的信息，从mid开始，而不是从mid.next开始
      ListNode headB = mid;
//      mid.next = null;

      headB = reverseList(headB);

      return isPalindrome(head, headB);
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

    private boolean isPalindrome(ListNode headA, ListNode headB) {
      while (headA != null && headB != null) {
        if (headA.val != headB.val) {
          return false;
        }
        headA = headA.next;
        headB = headB.next;
      }
      return true;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}