//给你一个链表的头节点 head ，旋转链表，将链表每个节点向右移动 k 个位置。 
//
// 
//
// 示例 1： 
//
// 
//输入：head = [1,2,3,4,5], k = 2
//输出：[4,5,1,2,3]
// 
//
// 示例 2： 
//
// 
//输入：head = [0,1,2], k = 4
//输出：[2,0,1]
// 
//
// 
//
// 提示： 
//
// 
// 链表中节点的数目在范围 [0, 500] 内 
// -100 <= Node.val <= 100 
// 0 <= k <= 2 * 10⁹ 
// 
// Related Topics 链表 双指针 👍 662 👎 0

package leetcode.editor.cn;

// 61.旋转链表
public class Code61_RotateList {

  public static void main(String[] args) {
    System.out.println(2000000000 % 3);
  }
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for singly-linked list. public class ListNode { int val; ListNode next; ListNode()
   * {} ListNode(int val) { this.val = val; } ListNode(int val, ListNode next) { this.val = val;
   * this.next = next; } }
   */
  class Solution {

    public ListNode rotateRight(ListNode head, int k) {
      if (head == null || head.next == null) {
        return head;
      }

      // 先求链表的长度
      int n = 0;
      while (head != null) {
        n++;
        head = head.next;
      }

      // n与k的关系，如果k>=n,只需要将链表向右移动k % n;如果k < n,则移动n次
      if (k == n) {
        return head;
      } else if (k < n) {
        return move(head, k);
      } else {
        return move(head, k % n);
      }
    }

    // 将链表向右移动k步，其中k < 链表的长度
    private ListNode move(ListNode head, int k) {
      ListNode dummyHead = new ListNode(-1, head);
      ListNode pre = head;

      for (int i = 0; i < k; i++) {
        pre = pre.next;
      }
      // 此时的pre是该断裂的上一个节点
      ListNode l2 = pre.next;
      pre.next = null;

      // 此时的链表现状
      // head         l2
      // 1 -> 2 -> 3  4 -> 5
      // 遍历找到l2的尾部节点，让它指向head
      ListNode cur = l2;
      while (cur.next != null) {
        cur = cur.next;
      }

      cur.next = head;
      return dummyHead.next;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}