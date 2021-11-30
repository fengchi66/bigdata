package com.data.offer02.demo;

import leetcode.editor.cn.TreeNode;

public class SearchBST {

  // 二叉搜索树查找
  public TreeNode search(TreeNode root, int val) {
    TreeNode cur = root;
    while (cur != null) {
      if (cur.val == val) {
        break;
      }
      if (cur.val < val) {
        cur = cur.right;
      } else {
        cur = cur.left;
      }
    }
    return cur;
  }

}
