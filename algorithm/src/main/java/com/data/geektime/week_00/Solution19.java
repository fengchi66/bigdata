package com.data.geektime.week_00;

import com.data.entity.ListNode;

// 删除链表中的倒数第个节点 1 -> 2 -> 3 -> 4 -> 5
public class Solution19 {

  public ListNode removeNthFromEnd(ListNode head, int n) {
    // 删除的可能也是头结点
    ListNode dummyHead = new ListNode(-1, head);
    ListNode pre = dummyHead;
    ListNode cur = head;

    for (int i = 0; i < n; i++) {
      cur = cur.next;
    }
    while (cur != null) {
      cur = cur.next;
      pre = pre.next;
    }

    // 此时的pre是待删除的上一个节点
    pre.next = pre.next.next;
    return dummyHead.next;
  }
}
