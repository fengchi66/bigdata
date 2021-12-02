//输入某二叉树的前序遍历和中序遍历的结果，请构建该二叉树并返回其根节点。 
//
// 假设输入的前序遍历和中序遍历的结果中都不含重复的数字。 
//
// 
//
// 示例 1: 
//
// 
//Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
//Output: [3,9,20,null,null,15,7]
// 
//
// 示例 2: 
//
// 
//Input: preorder = [-1], inorder = [-1]
//Output: [-1]
// 
//
// 
//
// 限制： 
//
// 0 <= 节点个数 <= 5000 
//
// 
//
// 注意：本题与主站 105 题重复：https://leetcode-cn.com/problems/construct-binary-tree-from-
//preorder-and-inorder-traversal/ 
// Related Topics 树 数组 哈希表 分治 二叉树 👍 622 👎 0

package leetcode.editor.cn;

import javax.swing.ImageIcon;

// 剑指 Offer 07.重建二叉树
class Code_Offer_ZhongJianErChaShuLcof {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode(int x) { val = x; } }
   */
  class Solution {

    int[] preorder;
    int[] inorder;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
      this.inorder = inorder;
      this.preorder = preorder;
      return build(0, preorder.length - 1, 0, inorder.length - 1);

    }

    // 由于preorder[l1...r1],inorder[l2...r2]来构建二叉树
    private TreeNode build(int l1, int r1, int l2, int r2) {

      if (l1 > r1) {
        return null;
      }

      TreeNode root = new TreeNode(preorder[l1]);

      // 找到root在inorder中的位置
      int mid = l2;
      while (inorder[mid] != root.val) {
        mid++;
      }

      root.left = build(l1 + 1, l1 + (mid - l2), l2, mid - 1);
      root.right = build(l1 + (mid - l2) + 1, r1, mid + 1, r2);

      return root;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}