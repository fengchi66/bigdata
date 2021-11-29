//给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。 
//
// 进阶： 
//
// 
// 你可以在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序吗？ 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：head = [4,2,1,3]
//输出：[1,2,3,4]
// 
//
// 示例 2： 
//
// 
//输入：head = [-1,5,3,4,0]
//输出：[-1,0,3,4,5]
// 
//
// 示例 3： 
//
// 
//输入：head = []
//输出：[]
// 
//
// 
//
// 提示： 
//
// 
// 链表中节点的数目在范围 [0, 5 * 10⁴] 内 
// -10⁵ <= Node.val <= 10⁵ 
// 
// Related Topics 链表 双指针 分治 排序 归并排序 👍 1385 👎 0

package leetcode.editor.cn;


import com.data.entity.ListNode;

// 148.排序链表
public class Code148_SortList {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for singly-linked list. public class ListNode { int val; ListNode next; ListNode()
   * {} ListNode(int val) { this.val = val; } ListNode(int val, ListNode next) { this.val = val;
   * this.next = next; } }
   */
  class Solution {

    // 分治：归并排序
    public ListNode sortList(ListNode head) {
      // base case
      if (head == null || head.next == null) {
        return head;
      }

      // 将链表head分为两部分
      ListNode head1 = head;
      ListNode head2 = split(head);

      // 将两部分链表排序
      ListNode l1 = sortList(head1);
      ListNode l2 = sortList(head2);

      // 合并两个排序升序链表
      return merge(l1, l2);
    }

    private ListNode split(ListNode head) {
      // 快慢指针
      ListNode slow = head;
      ListNode fast = head.next;

      while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
      }
      ListNode node = slow.next;
      slow.next = null;
      return node;
    }

    // 合并链表
    private ListNode merge(ListNode l1,
        ListNode l2) {
      ListNode dummyHead = new ListNode(0);
      ListNode pre = dummyHead;

      while (l1 != null && l2 != null) {
        if (l1.val <= l2.val) {
          pre.next = l1;
          l1 = l1.next;
        } else {
          pre.next = l2;
          l2 = l2.next;
        }
        pre = pre.next;
      }
      pre.next = l1 != null ? l1 : l2;
      return dummyHead.next;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)

}