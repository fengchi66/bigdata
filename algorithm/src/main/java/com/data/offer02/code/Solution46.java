package com.data.offer02.code;

import com.data.entity.TreeNode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 二叉树的右侧视图
public class Solution46 {

  // 每一层遍历完的时候的当前节点
  public List<Integer> rightSideView(TreeNode root) {
    List<Integer> ans = new ArrayList<>();
    if (root == null) {
      return ans;
    }

    Queue<TreeNode> queue1 = new LinkedList<>();
    Queue<TreeNode> queue2 = new LinkedList<>();
    queue1.offer(root);

    while (!queue1.isEmpty()) {
      TreeNode node = queue1.poll();

      if (node.left != null) {
        queue2.offer(node.left);
      }
      if (node.right != null) {
        queue2.offer(node.right);
      }

      // 当前层遍历完的时候
      if (queue1.isEmpty()) {
        ans.add(node.val);
        queue1 = queue2;
        queue2 = new LinkedList<>();
      }
    }
    return ans;
  }
}
