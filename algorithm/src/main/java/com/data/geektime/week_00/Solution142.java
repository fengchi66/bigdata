package com.data.geektime.week_00;

import com.data.entity.ListNode;

// 环形链表 II 找到入环节点
public class Solution142 {

  // 从相遇点到入环点的距离加上 n−1 圈的环长，恰好等于从链表头部到入环点的距离。
  public ListNode detectCycle(ListNode head) {
    // 双指针一开始都在head
    ListNode fast = head, slow = head;

    while (fast != null && fast.next != null) {
      // 快指针一次跑2补，慢指针一次跑一步
      fast = fast.next.next;
      slow = slow.next;

      // 第一次在环中相遇
      if (slow == fast) {
        // 重置fast为head
        fast = head;
        while (fast != slow) {
          fast = fast.next;
          slow = slow.next;
        }
        // 第二次相遇时即是入环节点
        return fast;
      }
    }
    return null;
  }
}

