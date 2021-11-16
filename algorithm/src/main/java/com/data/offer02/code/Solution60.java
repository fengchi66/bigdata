package com.data.offer02.code;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;

// 出现频率最高的 k 个数字
public class Solution60 {

  public int[] topKFrequent(int[] nums, int k) {
    HashMap<Integer, Integer> map = new HashMap<>();
    for (int num : nums) {
      map.put(num, map.getOrDefault(num, 0) + 1);
    }

    PriorityQueue<Entry<Integer, Integer>> minHeap = new PriorityQueue<>((e1, e2) -> e1.getValue() - e2.getValue());

    for (Entry<Integer, Integer> entry : map.entrySet()) {
      if (minHeap.size() < k) {
        minHeap.offer(entry);
      } else if (entry.getValue() > minHeap.peek().getValue()) {
        minHeap.poll();
        minHeap.offer(entry);
      }
    }

    int[] ans = new int[k];
    int i = 0;

    for (Entry<Integer, Integer> entry : minHeap) {
      ans[i++] = entry.getKey();
    }
    return ans;
  }

}
