package com.data.offer02.code;

import com.data.entity.TreeNode;

// 节点之和的最大路径
public class Solution51 {

  int maxSum = Integer.MIN_VALUE;

  public int maxPathSum(TreeNode root) {
    dfs(root);
    return maxSum;
  }

  // 计算该子树的最大贡献值: 后序遍历
  private int dfs(TreeNode root) {
    if (root == null) {
      return 0;
    }

    // 左右子树
    int leftValue = Math.max(dfs(root.left), 0);
    int rightValue = Math.max(dfs(root.right), 0);

    // 更新maxSum
    maxSum = Math.max(leftValue + rightValue + root.val, maxSum);

    return root.val + Math.max(leftValue, rightValue);
  }
}
