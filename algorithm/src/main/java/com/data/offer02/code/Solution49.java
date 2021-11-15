package com.data.offer02.code;

import com.data.entity.TreeNode;

// 从根节点到叶节点的路径数字之和
public class Solution49 {

  // 思路：先序遍历
  public int sumNumbers(TreeNode root) {
    return dfs(root, 0);
  }

  // 先序遍历
  private int dfs(TreeNode root, int path) {
    // base case
    if (root == null) {
      return 0;
    }

    path = path * 10 + root.val;
    if (root.left == null && root.right == null) {
      return path;
    }

    return dfs(root.left, path) + dfs(root.right, path);
  }

}
