//请实现一个函数，用来判断一棵二叉树是不是对称的。如果一棵二叉树和它的镜像一样，那么它是对称的。 
//
// 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。 
//
// 1 
// / \ 
// 2 2 
// / \ / \ 
//3 4 4 3 
//但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的: 
//
// 1 
// / \ 
// 2 2 
// \ \ 
// 3 3 
//
// 
//
// 示例 1： 
//
// 输入：root = [1,2,2,3,4,4,3]
//输出：true
// 
//
// 示例 2： 
//
// 输入：root = [1,2,2,null,3,null,3]
//输出：false 
//
// 
//
// 限制： 
//
// 0 <= 节点个数 <= 1000 
//
// 注意：本题与主站 101 题相同：https://leetcode-cn.com/problems/symmetric-tree/ 
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 265 👎 0

package leetcode.editor.cn;

// 剑指 Offer 28.对称的二叉树
class Code_Offer_DuiChengDeErChaShuLcof {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode(int x) { val = x; } }
   */
  class Solution {

    // dfs
    public boolean isSymmetric(TreeNode root) {
      return dfs(root, root);
    }

    // root1于root2是否镜像对称
    private boolean dfs(TreeNode root1, TreeNode root2) {
      if (root1 == null && root2 == null) {
        return true;
      }
      if (root1 == null || root2 == null) {
        return false;
      }

      // root1与root2值相等，且左右子树分别对称
      return root1.val == root2.val && dfs(root1.left, root2.right) && dfs(root1.right, root2.left);

    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}