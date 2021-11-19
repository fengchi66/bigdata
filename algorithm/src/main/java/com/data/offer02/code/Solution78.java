package com.data.offer02.code;

import com.data.entity.ListNode;

// 剑指 Offer II 078. 合并排序链表
public class Solution78 {

  public ListNode mergeKLists(ListNode[] lists) {
    return mergeLists(lists, 0, lists.length - 1);
  }

  // 合并list[left...right]链表
  private ListNode mergeLists(ListNode[] lists, int left, int right) {
    if (left == right) {
      return lists[left];
    }
    if (left > right) {
      return null;
    }
    int mid = (left + right) / 2;
    ListNode l1 = mergeLists(lists, left, mid);
    ListNode l2 = mergeLists(lists, mid + 1, right);

    return merge(l1, l2);
  }

  // 合并升序链表
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
