package com.data.offer02.code;

import com.data.entity.TreeNode;
import java.util.LinkedList;
import java.util.Queue;

// 往完全二叉树添加节点
public class Solution43 {

  Queue<TreeNode> queue;
  TreeNode root;

  public Solution43(TreeNode root) {
    this.queue = new LinkedList<>();
    this.root = root;

    // 目的是找到节点左子树或者右子树为null的节点，因此当一个节点左右子树都不为null的时候，将其在队列中移除,
    // 队列中只存左子树或右子树为null的节点
    queue.offer(root);
    while (queue.peek().left != null && queue.peek().right != null) {
      TreeNode node = queue.poll();
      queue.offer(node.left);
      queue.offer(node.right);
    }
  }

  // 插入一个节点，并返回插入节点的父节点
  public int insert(int v) {
    TreeNode newNode = new TreeNode(v);
    // 在构造函数中，queue中的节点一定是左孩子或者右孩子为null
    TreeNode node = queue.peek();

    if (node.left == null) {// 左孩子为null
      node.left = newNode;
    } else { // 右孩子为null
      node.right = newNode;
      // 此时node的左右孩子都不为null了，将node从队列中移除,同时将左右孩子加入队列
      queue.poll();
      queue.offer(node.left);
      queue.offer(node.right);
    }
    return node.val;
  }

  public TreeNode get_root() {
    return this.root;
  }

}
