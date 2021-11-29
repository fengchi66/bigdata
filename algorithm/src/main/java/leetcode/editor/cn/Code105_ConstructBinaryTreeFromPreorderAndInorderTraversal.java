//给定一棵树的前序遍历 preorder 与中序遍历 inorder。请构造二叉树并返回其根节点。 
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
// 提示: 
//
// 
// 1 <= preorder.length <= 3000 
// inorder.length == preorder.length 
// -3000 <= preorder[i], inorder[i] <= 3000 
// preorder 和 inorder 均无重复元素 
// inorder 均出现在 preorder 
// preorder 保证为二叉树的前序遍历序列 
// inorder 保证为二叉树的中序遍历序列 
// 
// Related Topics 树 数组 哈希表 分治 二叉树 👍 1311 👎 0

package leetcode.editor.cn;

// 105.从前序与中序遍历序列构造二叉树
public class Code105_ConstructBinaryTreeFromPreorderAndInorderTraversal {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
   * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
   */
  class Solution {

    int[] preorder;
    int[] inorder;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
      this.preorder = preorder;
      this.inorder = inorder;

      return build(0, preorder.length - 1, 0, inorder.length - 1);
    }

    // 用preorder[l1...r1]和inorder[l2,r2]来还原二叉树
    private TreeNode build(int l1, int r1, int l2, int r2) {
      if (l1 > r1) {
        return null;
      }
      TreeNode root = new TreeNode(preorder[l1]);
      // mid是root在inorder中的位置
      int mid = l2;
      while (inorder[mid] != root.val) {
        mid++;
      }
      // 构建左右子树
      root.left = build(l1 + 1, l1 + (mid - l2), l2, mid - 1);
      root.right = build(l1 + (mid - l2) +1, r1, mid + 1, r2);
      return root;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}