package com.data.offer02.code;

import java.util.LinkedList;
import java.util.Queue;

// 滑动窗口的平均值
public class Solution41 {

  Queue<Integer> nums;
  int capacity;
  int sum;

  public Solution41(int size) {
    nums = new LinkedList<>();
    capacity = size;
  }

  // 添加一个元素，并返回平均值
  public double next(int val) {
    nums.offer(val);
    sum += val;

    if (nums.size() > capacity) {
      sum -= nums.poll();
    }
    return (double) sum / nums.size();
  }

  public static void main(String[] args) {
    Solution41 solution = new Solution41(3);
    System.out.println(solution.next(1));
    System.out.println(solution.next(2));
    System.out.println(solution.next(3));
    System.out.println(solution.next(4));
    System.out.println(solution.next(111));
  }

}
