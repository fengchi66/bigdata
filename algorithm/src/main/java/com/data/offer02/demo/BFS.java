package com.data.offer02.demo;

import com.data.entity.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// 二叉树的层序遍历
public class BFS {

  public List<Integer> bfs(TreeNode root) {
    LinkedList<TreeNode> queue = new LinkedList<>();
    ArrayList<Integer> ans = new ArrayList<>();

    if (root == null) {
      return ans;
    }

    // 先将root加入到queue中
    queue.offer(root);
    while (!queue.isEmpty()) {
      // 将队头的元素取出
      TreeNode node = queue.poll();
      ans.add(node.val);

      // 如果左右子树不为空，则加入到队列中
      if (node.left != null) {
        queue.offer(node.left);
      }
      if (node.right != null) {
        queue.offer(node.right);
      }
    }
    return ans;
  }
}


