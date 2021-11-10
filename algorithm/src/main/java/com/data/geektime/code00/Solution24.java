package com.data.geektime.code00;

import com.data.entity.ListNode;

// 反转链表
public class Solution24 {

  public ListNode reverseList(ListNode head) {
    ListNode pre = null;
    ListNode cur = head;

    while (cur != null) {
      ListNode next = cur.next;
      cur.next = pre;
      pre = cur;
      cur = next;
    }
    return pre;
  }

}
