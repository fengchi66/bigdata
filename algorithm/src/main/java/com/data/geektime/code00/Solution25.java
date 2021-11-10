package com.data.geektime.code00;

import com.data.entity.ListNode;

// 25.k个一组翻转链表
public class Solution25 {

  public ListNode reverseKGroup(ListNode head, int k) {

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
