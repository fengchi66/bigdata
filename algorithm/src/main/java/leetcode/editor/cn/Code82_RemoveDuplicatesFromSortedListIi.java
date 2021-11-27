//存在一个按升序排列的链表，给你这个链表的头节点 head ，请你删除链表中所有存在数字重复情况的节点，只保留原始链表中 没有重复出现 的数字。 
//
// 返回同样按升序排列的结果链表。 
//
// 
//
// 示例 1： 
//
// 
//输入：head = [1,2,3,3,4,4,5]
//输出：[1,2,5]
// 
//
// 示例 2： 
//
// 
//输入：head = [1,1,1,2,3]
//输出：[2,3]
// 
//
// 
//
// 提示： 
//
// 
// 链表中节点数目在范围 [0, 300] 内 
// -100 <= Node.val <= 100 
// 题目数据保证链表已经按升序排列 
// 
// Related Topics 链表 双指针 👍 751 👎 0

package leetcode.editor.cn;

// 82.删除排序链表中的重复元素 II
public class Code82_RemoveDuplicatesFromSortedListIi {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for singly-linked list. public class ListNode { int val; ListNode next; ListNode()
   * {} ListNode(int val) { this.val = val; } ListNode(int val, ListNode next) { this.val = val;
   * this.next = next; } }
   */
  class Solution {

    public ListNode deleteDuplicates(ListNode head) {
      if (head == null || head.next == null) {
        return head;
      }

      // 删除的节点也可能是头节点
      ListNode dummyHead = new ListNode(-1, head);
      ListNode pre = dummyHead;

      while (pre.next != null && pre.next.next != null) {
        if (pre.next.val == pre.next.next.val) {
          // 将第一个重复的节点值记下来,并且当前节点是需要删除的
          int x = pre.next.val;
          while (pre.next != null && pre.next.val == x) {
            // 将pre.next节点删除
            pre.next = pre.next.next;
          }
        } else { // pre.next不需要删除
          pre = pre.next;
        }
      }
      return dummyHead.next;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}