package com.data.geektime.week_01;

// 26. 删除有序数组中的重复项
public class Solution26 {

  // 过滤器思想：遍历数组，在满足条件的情况下保留这个元素
  public int removeDuplicates(int[] nums) {
    int n = 0;
    for (int i = 0; i < nums.length; i++) {
      if (i == 0 || nums[i] != nums[i - 1]) { // 当前元素 != 上一个元素，我们就保留
        nums[n] = nums[i];
        n++; // n++方便下一次需要保留元素的时候直接赋值
      }
    }
    return n;
  }
}
