package com.data.offer02.code;

// 求平方根
public class Solution72 {

  public int mySqrt(int x) {
    // 平方根一定在[1,x]之间
    int left = 1;
    int right = x;

    while (left <= right) {
      int mid = (left + right) / 2;
      if (mid  <= x / mid) { // 首先确定范围mid * mid <= x
        if (mid + 1 > x / (mid + 1)) {
          return mid;
        }
        // 如果mid、mid +1 的平方都小于x，那么mid的范围小了
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }
    return 0;
  }

}
