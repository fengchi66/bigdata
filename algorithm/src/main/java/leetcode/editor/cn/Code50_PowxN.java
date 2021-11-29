//实现 pow(x, n) ，即计算 x 的 n 次幂函数（即，x⁴
//
// Related Topics 递归 数学 👍 795 👎 0

package leetcode.editor.cn;

// 50.Pow(x, n)
public class Code50_PowxN {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public double myPow(double x, int n) {
      if (n == 0) {
        return 1;
      }
      if (n < 0) {
        return 1.0 / myPow(x, -n);
      }

      double pow = myPow(x, n / 2);
      double ans = pow * pow;
      if (n % 2 == 1) {
        return ans * x;
      }
      return ans;

    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}