//给定一个二叉树，检查它是否是镜像对称的。 
//
// 
//
// 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。 
//
//     1
//   / \
//  2   2
// / \ / \
//3  4 4  3
// 
//
// 
//
// 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的: 
//
//     1
//   / \
//  2   2
//   \   \
//   3    3
// 
//
// 
//
// 进阶： 
//
// 你可以运用递归和迭代两种方法解决这个问题吗？ 
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 1617 👎 0

package leetcode.editor.cn;

// 101.对称二叉树
public class Code101_SymmetricTree {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
   * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
   */
  class Solution {

    public boolean isSymmetric(TreeNode root) {
      return dfs(root, root);
    }

    // 判断两棵树是否是对称二叉树
    private boolean dfs(TreeNode root1, TreeNode root2) {
      // base case
      if (root1 == null && root2 == null) {
        return true;
      }
      if (root1 == null || root2 == null) {
        return false;
      }

      // 当前节点值相等且左右树分别对称
      return root1.val == root2.val
          && dfs(root1.left, root2.right)
          && dfs(root1.right, root2.left);
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}