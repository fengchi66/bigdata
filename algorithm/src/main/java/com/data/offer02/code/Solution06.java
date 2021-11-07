package com.data.offer02.code;

// 剑指 Offer II 006. 排序数组中两个数字之和
public class Solution06 {

  // 相向双指针
  public int[] twoSum(int[] numbers, int target) {
    int p1 = 0;
    int p2 = numbers.length - 1;

    while (numbers[p1] + numbers[p2] != target) {
      if (numbers[p1] + numbers[p2] < target) {
        p1++;
      } else {
        p2--;
      }
    }
    return new int[]{p1, p2};
  }

}
