//路径 被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不
//一定经过根节点。 
//
// 路径和 是路径中各节点值的总和。 
//
// 给你一个二叉树的根节点 root ，返回其 最大路径和 。 
//
// 
//
// 示例 1： 
//
// 
//输入：root = [1,2,3]
//输出：6
//解释：最优路径是 2 -> 1 -> 3 ，路径和为 2 + 1 + 3 = 6 
//
// 示例 2： 
//
// 
//输入：root = [-10,9,20,null,null,15,7]
//输出：42
//解释：最优路径是 15 -> 20 -> 7 ，路径和为 15 + 20 + 7 = 42
// 
//
// 
//
// 提示： 
//
// 
// 树中节点数目范围是 [1, 3 * 10⁴] 
// -1000 <= Node.val <= 1000 
// 
// Related Topics 树 深度优先搜索 动态规划 二叉树 👍 1303 👎 0

package leetcode.editor.cn;

// 124.二叉树中的最大路径和
public class Code124_BinaryTreeMaximumPathSum {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
   * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
   */
  class Solution {

    int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
      dfs(root);
      return maxSum;
    }

    // 计算子树对路径和的贡献值
    private int dfs(TreeNode root) {
      // base case
      if (root == null) {
        return 0;
      }
      // 计算左右子树的贡献值,如果贡献值小于0，就不要
      int leftValue = Math.max(dfs(root.left), 0);
      int rightValue = Math.max(dfs(root.right), 0);

      // 更新maxSum
      maxSum = Math.max(maxSum, leftValue + rightValue + root.val);

      // 当前节点root的贡献值
      return Math.max(leftValue, rightValue) + root.val;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}