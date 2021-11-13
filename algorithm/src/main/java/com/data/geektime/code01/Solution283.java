package com.data.geektime.code01;

// 移动0
public class Solution283 {

  // 和26题一样，过滤器思想
  public void moveZeroes(int[] nums) {
    int n = 0;
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] != 0) { // 当前元素 != 0，我们就保留
        nums[n] = nums[i];
        n++; // n++方便下一次需要保留元素的时候直接赋值
      }
    }

    // 把数组后面的元素补为0，相当于把0移动到了后面来
    while (n < nums.length) {
      nums[n] = 0;
      n++;
    }
  }
}
