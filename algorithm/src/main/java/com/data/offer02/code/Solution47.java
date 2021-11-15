package com.data.offer02.code;

import com.data.entity.TreeNode;

public class Solution47 {

  public TreeNode pruneTree(TreeNode root) {
    if (root == null) {
      return null;
    }

    TreeNode leftNode = pruneTree(root.left);
    TreeNode rightNode = pruneTree(root.right);
    root.left = leftNode;
    root.right = rightNode;
    // 由左右子树的结果来更新自己的答案
    if (leftNode == null && rightNode == null && root.val == 0) {
      return null;
    }

    return root;
  }
}
