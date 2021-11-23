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

  // 二叉树的层序遍历：分层
  public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> ans = new LinkedList<>();
    if (root == null) {
      return ans;
    }

    // 存每一层的节点
    List<Integer> mid = new ArrayList<>();
    LinkedList<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    int current = 1;
    int next = 0;

    while (!queue.isEmpty()) {
      TreeNode node = queue.poll();
      mid.add(node.val);
      current--;

      if (node.left != null) {
        queue.offer(node.left);
        next++;
      }
      if (node.right != null) {
        queue.offer(node.right);
        next++;
      }

      // 当前层遍历完了
      if (current == 0) {
        current = next;
        next = 0;
        ans.add(mid);
        mid = new ArrayList<>();
      }
    }
    return ans;
  }
}


