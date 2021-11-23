package com.data.geektime.week_01;

import com.data.entity.ListNode;

// 反转链表
public class Solution206 {

  public ListNode reverseList(ListNode head) {
    ListNode last = null;

    while (head != null) {
      ListNode next = head.next;
      head.next = last;
      last = head;
      head = next;
    }
    return last;
  }
}
