package com.data.offer02.code;

import com.data.entity.ListNode;

// 链表排序:升序排序
public class Solution77 {

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
    ListNode fast = head;

    while (slow != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }
    ListNode node = slow.next;
    slow.next = null;
    return node;
  }

  // 合并链表
  private ListNode merge(ListNode l1, ListNode l2) {
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
