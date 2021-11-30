//给定一个二叉树，判断它是否是高度平衡的二叉树。 
//
// 本题中，一棵高度平衡二叉树定义为： 
//
// 
// 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1 。 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：root = [3,9,20,null,null,15,7]
//输出：true
// 
//
// 示例 2： 
//
// 
//输入：root = [1,2,2,3,3,null,null,4,4]
//输出：false
// 
//
// 示例 3： 
//
// 
//输入：root = []
//输出：true
// 
//
// 
//
// 提示： 
//
// 
// 树中的节点数在范围 [0, 5000] 内 
// -10⁴ <= Node.val <= 10⁴ 
// 
// Related Topics 树 深度优先搜索 二叉树 👍 819 👎 0

package leetcode.editor.cn;

// 110.平衡二叉树
public class Code110_BalancedBinaryTree {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
   * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
   */
  class Solution {

    public boolean isBalanced(TreeNode root) {
      return dfs(root).isBalanced;

    }

    private Info dfs(TreeNode root) {
      if (root == null) {
        return new Info(true, 0);
      }

      // 默认能向左树和右树拿到信息
      Info leftInfo = dfs(root.left);
      Info rightInfo = dfs(root.right);

      // 左右树信息求全集，此时得到以X为头的树是否是平的以及高度的信息

      // 高度：左树和右树的最大高度 + 1
      int height = Math.max(leftInfo.height, rightInfo.height) + 1;

      // 判断是不是平的,先假设自己是平的，当左树或者右树不是平的时候，自己也不是平的了
      boolean isBalanced = true;
      if (!leftInfo.isBalanced) {
        isBalanced = false;
      }
      if (!rightInfo.isBalanced) {
        isBalanced = false;
      }
      // 左右子树高度差大于1，也不平
      if (Math.abs(leftInfo.height - rightInfo.height) > 1) {
        isBalanced = false;
      }

      // 返回自己的Info
      return new Info(isBalanced, height);
    }

    private class Info {

      public boolean isBalanced; // 是否平
      public int height; // 子树的高度

      public Info(boolean isBalanced, int height) {
        this.isBalanced = isBalanced;
        this.height = height;
      }
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}