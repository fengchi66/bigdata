//小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为 root 。 
//
// 除了 root 之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。 如果 两个直接
//相连的房子在同一天晚上被打劫 ，房屋将自动报警。 
//
// 给定二叉树的 root 。返回 在不触动警报的情况下 ，小偷能够盗取的最高金额 。 
//
// 
//
// 示例 1: 
//
// 
//
// 
//输入: root = [3,2,3,null,3,null,1]
//输出: 7 
//解释: 小偷一晚能够盗取的最高金额 3 + 3 + 1 = 7 
//
// 示例 2: 
//
// 
//
// 
//输入: root = [3,4,5,1,3,null,1]
//输出: 9
//解释: 小偷一晚能够盗取的最高金额 4 + 5 = 9
// 
//
// 
//
// 提示： 
//
// 
//
// 
// 树的节点数在 [1, 10⁴] 范围内 
// 0 <= Node.val <= 10⁴ 
// 
// Related Topics 树 深度优先搜索 动态规划 二叉树 👍 1127 👎 0

package leetcode.editor.cn;

import java.util.HashMap;

// 337.打家劫舍 III
public class Code337_HouseRobberIii {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
   * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
   */
  class Solution {

    // dp存储的是每个节点在偷/不偷两种情况下能偷到的最大值
    HashMap<TreeNode, int[]> dp = new HashMap<>();

    public int rob(TreeNode root) {
      dp.put(null, new int[]{0, 0});
      dfs(root);

      return Math.max(dp.get(root)[0], dp.get(root)[1]);
    }

    private void dfs(TreeNode root) {
      if (root == null) return;
      dp.put(root, new int[2]);

      dfs(root.left);
      dfs(root.right);

      // 当root不偷的情况下，left和right偷或者不偷都可以
      dp.get(root)[0] = Math.max(dp.get(root.left)[0], dp.get(root.left)[1]) +
          Math.max(dp.get(root.right)[0], dp.get(root.right)[1]);
      // 当root偷的情况下，root的left和right就都不能偷了
      dp.get(root)[1] = dp.get(root.left)[0] + dp.get(root.right)[0] + root.val;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}