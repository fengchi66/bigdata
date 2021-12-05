//输入一个整数 n ，求1～n这n个整数的十进制表示中1出现的次数。 
//
// 例如，输入12，1～12这些整数中包含1 的数字有1、10、11和12，1一共出现了5次。 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 12
//输出：5
// 
//
// 示例 2： 
//
// 
//输入：n = 13
//输出：6 
//
// 
//
// 限制： 
//
// 
// 1 <= n < 2^31 
// 
//
// 注意：本题与主站 233 题相同：https://leetcode-cn.com/problems/number-of-digit-one/ 
// Related Topics 递归 数学 动态规划 👍 250 👎 0

package leetcode.editor.cn;

// 剑指 Offer 43.1～n 整数中 1 出现的次数
class Code_Offer_OneNzhengShuZhong1chuXianDeCiShuLcof {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int countDigitOne(int n) {
      // mulk 表示 10^k
      // 在下面的代码中，可以发现 k 并没有被直接使用到（都是使用 10^k）
      // 但为了让代码看起来更加直观，这里保留了 k
      long mulk = 1;
      int ans = 0;
      for (int k = 0; n >= mulk; ++k) {
        ans += (n / (mulk * 10)) * mulk + Math.min(Math.max(n % (mulk * 10) - mulk + 1, 0), mulk);
        mulk *= 10;
      }
      return ans;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}