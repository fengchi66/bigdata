## 区间DP

- **dp[L...R]表示在区间[L...R]范围内的方案数等**

### [516. 最长回文子序列](https://leetcode-cn.com/problems/longest-palindromic-subsequence/)

给你一个字符串 s ，找出其中最长的回文子序列，并返回该序列的长度。

子序列定义为：不改变剩余字符顺序的情况下，删除某些字符或者不删除任何字符形成的一个序列。

```java
public int longestPalindromeSubseq(String s) {
      int n = s.length();
      // dp[i...j]表示在s[i...j]范围内最长回文子序列的长度
      int[][] dp = new int[n][n];

      // 填dp表
      dp[n - 1][n - 1] = 1;

      for (int i = 0; i < n - 1; i++) {
        dp[i][i] = 1;
        dp[i][i + 1] = s.charAt(i) == s.charAt(i + 1) ? 2 : 1;
      }

      // 填dp表中剩余的格子
      for (int i = n - 3; i >= 0; i--) {
        for (int j = i + 2; j < n; j++) {
          if (s.charAt(i) == s.charAt(j)) {
            dp[i][j] = 2 + dp[i + 1][j - 1];
          } else {
            dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
          }
        }
      }
      return dp[0][n - 1];
    }
```

### [5. 最长回文子串](https://leetcode-cn.com/problems/longest-palindromic-substring/)

```java
 // 在s[L...R]范围内寻找最长回文子串
    public String longestPalindrome(String s) {

      if (s == null || s.length() == 0) {
        return s;
      }

      // 最长子串开始的位置以及跨越的长度
      int start = 0;
      int step = 1;

      int n = s.length();
      // dp[i][j]表示字符串在[i...j]范围内是否是回文字符串
      boolean[][] dp = new boolean[n][n];

      // base case:i==j时，dp为true
      for (int i = 0; i < n; i++) {
        dp[i][i] = true;
      }

      // base case，j==i+1时，看这两个字符是否相等
      for (int i = 0; i < n - 1; i++) {
        dp[i][i + 1] = s.charAt(i) == s.charAt(i + 1);
        if (dp[i][i + 1]) {
          start = i;
          step = 2;
        }
      }

      // 普遍情况：填dp表格
      for (int i = n - 3; i >= 0; i--) {
        for (int j = i + 2; j < n; j++) {
          // dp[i][j]是否为回文，需要dp[i + 1][j - 1]是回文，且i、j位置的字符相等
          dp[i][j] = dp[i + 1][j - 1] && s.charAt(i) == s.charAt(j);
          if (dp[i][j] && j - i + 1 > step) {
            start = i;
            step = j - i + 1;
          }
        }
      }
      return s.substring(start, start + step);
    }
```

## 树形DP

- 树形动态规划与线性动态规划没有本质区别。
- 其实是套在深度优先遍历里的动态规划(在DFS的过程中实现DP)

- **子问题就是"一颗子树"，状态一般表示为"以x为根的子树"，决策就是"x的子节点"**

### [337. 打家劫舍 III](https://leetcode-cn.com/problems/house-robber-iii/)

小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为 root 。

除了 root 之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。 如果 两个直接相连的房子在同一天晚上被打劫 ，房屋将自动报警。

给定二叉树的 root 。返回 在不触动警报的情况下 ，小偷能够盗取的最高金额 。

```java
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
```

### [124. 二叉树中的最大路径和](https://leetcode-cn.com/problems/binary-tree-maximum-path-sum/)

```java
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
```

### 总结

- 树形DP其实是一种思想，一般使用后续遍历的思想：对于节点root，先遍历左右子树，需要从左右子树中得到什么信息从而丰富root的答案。**想要得到的信息就是状态，从左右子树的状态得到root的信息就是状态转移，base case就是需要考虑数的边界问题(叶子节点和一些特殊条件)**