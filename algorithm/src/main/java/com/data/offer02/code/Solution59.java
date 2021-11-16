package com.data.offer02.code;

import java.util.PriorityQueue;

// 数据流的第k大数字
public class Solution59 {

  private PriorityQueue<Integer> minHeap;
  private int size;

  public Solution59(int k, int[] nums) {
    minHeap = new PriorityQueue<>();
    size = k;
    for (int num : nums) {
      add(num);
    }
  }

  public int add(int val) {
    // 当小根堆的长度小于k，那么直接offer
    if (minHeap.size() < size) {
      minHeap.offer(val);
      // 在小根堆的长度=k的情况下，判断当前值是否小于小根堆中堆顶的值
    } else if (val > minHeap.peek()) {
      minHeap.poll();
      minHeap.offer(val);
    }
    return minHeap.peek();
  }

}
