//计算给定二叉树的所有左叶子之和。 
//
// 示例： 
//
// 
//    3
//   / \
//  9  20
//    /  \
//   15   7
//
//在这个二叉树中，有两个左叶子，分别是 9 和 15，所以返回 24 
//
// 
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 366 👎 0

package leetcode.editor.cn;

import java.util.LinkedList;
import java.util.Queue;

// 404.左叶子之和
public class Code404_SumOfLeftLeaves {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
   * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
   */
  class Solution {

    // 层序遍历，只计算左叶子
    public int sumOfLeftLeaves(TreeNode root) {
      if (root == null) {
        return 0;
      }

      Queue<TreeNode> queue = new LinkedList<TreeNode>();
      queue.offer(root);
      int ans = 0;
      while (!queue.isEmpty()) {
        TreeNode node = queue.poll();
        if (node.left != null) {
          if (isLeafNode(node.left)) {
            ans += node.left.val;
          } else {
            queue.offer(node.left);
          }
        }
        if (node.right != null) {
          if (!isLeafNode(node.right)) {
            queue.offer(node.right);
          }
        }
      }
      return ans;
    }

    public boolean isLeafNode(TreeNode node) {
      return node.left == null && node.right == null;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}