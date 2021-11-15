package com.data.offer02.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import leetcode.editor.cn.TreeNode;

public class DFS {

  // 二叉树的前序遍历
  public List<Integer> preorderTraversal2(TreeNode root) {
    List<Integer> ans = new ArrayList<>();
    if (root == null) {
      return ans;
    }

    Stack<TreeNode> stack = new Stack<>();
    stack.push(root);

    while (!stack.empty()) {
      TreeNode node = stack.pop();
      ans.add(node.val);

      if (node.left != null) {
        stack.push(node.left);
      }
      if (node.right != null) {
        stack.push(node.right);
      }
    }
    return ans;
  }

}
