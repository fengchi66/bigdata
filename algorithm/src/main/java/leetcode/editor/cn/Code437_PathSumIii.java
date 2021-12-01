//给定一个二叉树的根节点 root ，和一个整数 targetSum ，求该二叉树里节点值之和等于 targetSum 的 路径 的数目。 
//
// 路径 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。 
//
// 
//
// 示例 1： 
//
// 
//
// 
//输入：root = [10,5,-3,3,2,null,11,3,-2,null,1], targetSum = 8
//输出：3
//解释：和等于 8 的路径有 3 条，如图所示。
// 
//
// 示例 2： 
//
// 
//输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
//输出：3
// 
//
// 
//
// 提示: 
//
// 
// 二叉树的节点个数的范围是 [0,1000] 
// -10⁹ <= Node.val <= 10⁹ 
// -1000 <= targetSum <= 1000 
// 
// Related Topics 树 深度优先搜索 二叉树 👍 1151 👎 0

package leetcode.editor.cn;

import java.util.HashMap;
import java.util.Map;

// 437.路径总和 III
public class Code437_PathSumIii {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
   * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
   */
  class Solution {

    // 先序遍历 + 前缀和
    public int pathSum(TreeNode root, int targetSum) {
      // 前缀和数组，存储path以及次数
      HashMap<Integer, Integer> map = new HashMap<>();
      map.put(0, 1);

      return dfs(root, targetSum, 0, map);
    }

    private int dfs(TreeNode root, int sum, int path, Map<Integer, Integer> map) {
      // base case
      if (root == null) {
        return 0;
      }

      path += root.val;
      // 和为sum的路径有多少个，那么和为sum - count的就有多少个
      int count = map.getOrDefault(path - sum, 0);
      // 更新前缀和数组
      map.put(path, map.getOrDefault(path, 0) + 1);

      count += dfs(root.left, sum, path, map);
      count += dfs(root.right, sum, path, map);

      // 递归计算root的left和right后，此时程序回到root
      map.put(path, map.get(path) - 1);
      return count;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}