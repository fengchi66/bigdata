package com.data.geektime.week_00;

// 加一
public class Solution66 {

  public int[] plusOne(int[] digits) {
    int len = digits.length;
    for (int i = len - 1; i >= 0; i--) {
      // 判断加1后是不是0
      digits[i] = (digits[i] + 1) % 10;
      if (digits[i] != 0) {
        return digits;
      }
    }
    // 加一后都是0
    digits = new int[len + 1];
    digits[0] = 1;
    return digits;
  }

  public static void main(String[] args) {
    int[] nums = {1, 2, 3};
    nums = new int[4];
    nums[0] = 1;
  }
}

