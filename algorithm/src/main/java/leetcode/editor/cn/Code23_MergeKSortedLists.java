//给你一个链表数组，每个链表都已经按升序排列。 
//
// 请你将所有链表合并到一个升序链表中，返回合并后的链表。 
//
// 
//
// 示例 1： 
//
// 输入：lists = [[1,4,5],[1,3,4],[2,6]]
//输出：[1,1,2,3,4,4,5,6]
//解释：链表数组如下：
//[
//  1->4->5,
//  1->3->4,
//  2->6
//]
//将它们合并到一个有序链表中得到。
//1->1->2->3->4->4->5->6
// 
//
// 示例 2： 
//
// 输入：lists = []
//输出：[]
// 
//
// 示例 3： 
//
// 输入：lists = [[]]
//输出：[]
// 
//
// 
//
// 提示： 
//
// 
// k == lists.length 
// 0 <= k <= 10^4 
// 0 <= lists[i].length <= 500 
// -10^4 <= lists[i][j] <= 10^4 
// lists[i] 按 升序 排列 
// lists[i].length 的总和不超过 10^4 
// 
// Related Topics 链表 分治 堆（优先队列） 归并排序 👍 1622 👎 0

package leetcode.editor.cn;

// 23.合并K个升序链表
public class Code23_MergeKSortedLists {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for singly-linked list. public class ListNode { int val; ListNode next; ListNode()
   * {} ListNode(int val) { this.val = val; } ListNode(int val, ListNode next) { this.val = val;
   * this.next = next; } }
   */
  class Solution {

    // 分治 + 归并排序
    public ListNode mergeKLists(ListNode[] lists) {
      if (lists == null || lists.length == 0) {
        return null;
      }

      return mergeList(lists, 0, lists.length - 1);
    }

    // 合并lists[left, right]的升序链表
    private ListNode mergeList(ListNode[] lists, int left, int right) {
      // base case
      if (left == right) {
        return lists[left];
      }

      int mid = (left + right) / 2;

      ListNode l1 = mergeList(lists, left, mid);
      ListNode l2 = mergeList(lists, mid + 1, right);

      return merge(l1, l2);
    }

    // 合并两个升序链表
    private ListNode merge(ListNode l1, ListNode l2) {
      ListNode dummy = new ListNode(-1);
      ListNode pre = dummy;

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

      return dummy.next;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}