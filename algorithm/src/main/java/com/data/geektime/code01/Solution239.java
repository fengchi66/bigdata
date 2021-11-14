package com.data.geektime.code01;

import java.util.LinkedList;

// 滑动窗口最大值
public class Solution239 {

  public int[] maxSlidingWindow(int[] nums, int k) {
    if (nums == null || k < 1 || nums.length < k) {
      return null;
    }
    // qmax 窗口最大值的更新结构
    // 放下标
    LinkedList<Integer> qmax = new LinkedList<Integer>();
    int[] res = new int[nums.length - k + 1];
    int index = 0;
    // 遍历数组：每前进一次就是一个窗口
    for (int R = 0; R < nums.length; R++) {
      while (!qmax.isEmpty() && nums[qmax.peekLast()] <= nums[R]) {
        qmax.pollLast();
      }
      qmax.addLast(R);
      // 移除过期的下标
      if (qmax.peekFirst() == R - k) {
        qmax.pollFirst();
      }
      // 是否形成一个有效的窗口
      if (R >= k - 1) {
        res[index++] = nums[qmax.peekFirst()];
      }
    }
    return res;
  }

}