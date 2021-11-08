package com.data.offer02.code;

// 剑指 Offer II 013. 二维子矩阵的和
public class Solution13 {

  int[][] sums;

  public Solution13(int[][] matrix) {
    sums = new int[matrix.length][matrix[0].length];

    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        sums[i + 1][j + 1] = sums[i][j + 1] + sums[i + 1][j] + sums[i][j] + matrix[i][j];
      }
    }
  }

  public int sumRegion(int row1, int col1, int row2, int col2) {
    return sums[row2 + 1][col2 + 1] - sums[row1][col2 + 1] - sums[row2 + 1][col1]
        + sums[row1][col1];
  }
}
