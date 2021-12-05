//输入一棵二叉树的根节点，判断该树是不是平衡二叉树。如果某二叉树中任意节点的左右子树的深度相差不超过1，那么它就是一棵平衡二叉树。 
//
// 
//
// 示例 1: 
//
// 给定二叉树 [3,9,20,null,null,15,7] 
//
// 
//    3
//   / \
//  9  20
//    /  \
//   15   7 
//
// 返回 true 。 
// 
//示例 2: 
//
// 给定二叉树 [1,2,2,3,3,null,null,4,4] 
//
// 
//       1
//      / \
//     2   2
//    / \
//   3   3
//  / \
// 4   4
// 
//
// 返回 false 。 
//
// 
//
// 限制： 
//
// 
// 0 <= 树的结点个数 <= 10000 
// 
//
// 注意：本题与主站 110 题相同：https://leetcode-cn.com/problems/balanced-binary-tree/ 
//
// 
// Related Topics 树 深度优先搜索 二叉树 👍 204 👎 0

package leetcode.editor.cn;

// 剑指 Offer 55 - II.平衡二叉树
class Code_Offer_PingHengErChaShuLcof {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode(int x) { val = x; } }
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