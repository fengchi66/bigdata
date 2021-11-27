//给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。 
//
// 你应当 保留 两个分区中每个节点的初始相对位置。 
//
// 
//
// 示例 1： 
//
// 
//输入：head = [1,4,3,2,5,2], x = 3
//输出：[1,2,2,4,3,5]
// 
//
// 示例 2： 
//
// 
//输入：head = [2,1], x = 2
//输出：[1,2]
// 
//
// 
//
// 提示： 
//
// 
// 链表中节点的数目在范围 [0, 200] 内 
// -100 <= Node.val <= 100 
// -200 <= x <= 200 
// 
// Related Topics 链表 双指针 👍 486 👎 0

package leetcode.editor.cn;

// 86.分隔链表
public class Code86_PartitionList {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for singly-linked list. public class ListNode { int val; ListNode next; ListNode()
   * {} ListNode(int val) { this.val = val; } ListNode(int val, ListNode next) { this.val = val;
   * this.next = next; } }
   */
  class Solution {

    public ListNode partition(ListNode head, int x) {
      // 存储小于x的节点
      ListNode smallHead = new ListNode(-1);
      ListNode small = smallHead;
      // 存储大于等于x的节点
      ListNode largeHead = new ListNode(-1);
      ListNode large = largeHead;

      while (head != null) {
        if (head.val < x) {
          small.next = head;
          small = small.next;
        } else {
          large.next = head;
          large = large.next;
        }
        head = head.next;
      }

      large.next = null;
      small.next = largeHead.next;
      return smallHead.next;

    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}