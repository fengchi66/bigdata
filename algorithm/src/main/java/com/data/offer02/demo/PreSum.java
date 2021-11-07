package com.data.offer02.demo;


/**
 * 前缀和数组
 */
public class PreSum {

  // 前缀和数组
  int[] preSum;

  // 生成前缀和数组
  public PreSum(int[] arr) {
    int N = arr.length;
    preSum = new int[N];
    preSum[0] = arr[0];

    // 前缀和当前位置的数等于前一位置 + 当前数组位置
    for (int i = 1; i < N; i++) {
      preSum[i] = preSum[i - 1] + arr[i];
    }
  }

  // arr[L...R]位置的和
  public int rangeSum(int L, int R) {
    return L == 0 ? preSum[R] : preSum[R] - preSum[L - 1];
  }


}
