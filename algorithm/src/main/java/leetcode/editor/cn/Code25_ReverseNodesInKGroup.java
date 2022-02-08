//给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。 
//
// k 是一个正整数，它的值小于或等于链表的长度。 
//
// 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。 
//
// 进阶： 
//
// 
// 你可以设计一个只使用常数额外空间的算法来解决此问题吗？ 
// 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：head = [1,2,3,4,5], k = 2
//输出：[2,1,4,3,5]
// 
//
// 示例 2： 
//
// 
//输入：head = [1,2,3,4,5], k = 3
//输出：[3,2,1,4,5]
// 
//
// 示例 3： 
//
// 
//输入：head = [1,2,3,4,5], k = 1
//输出：[1,2,3,4,5]
// 
//
// 示例 4： 
//
// 
//输入：head = [1], k = 1
//输出：[1]
// 
//
// 
// 
//
// 提示： 
//
// 
// 列表中节点的数量在范围 sz 内 
// 1 <= sz <= 5000 
// 0 <= Node.val <= 1000 
// 1 <= k <= sz 
// 
// Related Topics 递归 链表 👍 1462 👎 0

package leetcode.editor.cn;

import com.sun.xml.internal.bind.v2.model.core.ID;
import java.util.HashSet;
import java.util.Set;

// 25.K 个一组翻转链表
public class Code25_ReverseNodesInKGroup {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for singly-linked list. public class ListNode { int val; ListNode next; ListNode()
   * {} ListNode(int val) { this.val = val; } ListNode(int val, ListNode next) { this.val = val;
   * this.next = next; } }
   */
  class Solution {

    public ListNode reverseKGroup(ListNode head, int k) {

      Set<ListNode> visited = new HashSet<>();

      // 虚拟头节点，避免last的讨论
      ListNode dummyHead = new ListNode(0, head);
      ListNode last = dummyHead;

      while (head != null) {
        // 1. 分组，找到每一组的开始结尾
        ListNode end = getEnd(head, k);

        // 边界问题
        if (end == null) {
          break;
        }

        // 2. 记下end.next
        ListNode next = end.next;

        // 处理head到end之间k-1条边的反转
        reverseList(head, end);

        // 上一组跟本组的新head(就是旧的end)连接
        last.next = end;

        // 本组的新结尾和下一组的head建立联系,下一组的head其实就是当前组的end.next,但end已经被改过了，记下end.next
        head.next = next;

        // 更新last和head
        last = head;
        head = next;
      }
      return dummyHead.next;
    }

    // 给定头结点，获取每k个一组中的尾节点
    private ListNode getEnd(ListNode head, int k) {
      while (head != null) {
        k--;
        if (k == 0) {
          break;
        }
        head = head.next;
      }
      return head;
    }

    // head到end之间反转
    private void reverseList(ListNode head, ListNode end) {
      if (head == end) {
        return;
      }
      ListNode pre = head;
      ListNode cur = head.next;

      while (cur != end) {
        ListNode next = cur.next;
        cur.next = pre;
        pre = cur;
        cur = next;
      }
      end.next = pre;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}