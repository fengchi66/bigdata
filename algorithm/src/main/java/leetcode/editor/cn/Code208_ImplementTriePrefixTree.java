//Trie（发音类似 "try"）或者说 前缀树 是一种树形数据结构，用于高效地存储和检索字符串数据集中的键。这一数据结构有相当多的应用情景，例如自动补完和拼
//写检查。 
//
// 请你实现 Trie 类： 
//
// 
// Trie() 初始化前缀树对象。 
// void insert(String word) 向前缀树中插入字符串 word 。 
// boolean search(String word) 如果字符串 word 在前缀树中，返回 true（即，在检索之前已经插入）；否则，返回 
//false 。 
// boolean startsWith(String prefix) 如果之前已经插入的字符串 word 的前缀之一为 prefix ，返回 true ；否
//则，返回 false 。 
// 
//
// 
//
// 示例： 
//
// 
//输入
//["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
//[[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
//输出
//[null, null, true, false, true, null, true]
//
//解释
//Trie trie = new Trie();
//trie.insert("apple");
//trie.search("apple");   // 返回 True
//trie.search("app");     // 返回 False
//trie.startsWith("app"); // 返回 True
//trie.insert("app");
//trie.search("app");     // 返回 True
// 
//
// 
//
// 提示： 
//
// 
// 1 <= word.length, prefix.length <= 2000 
// word 和 prefix 仅由小写英文字母组成 
// insert、search 和 startsWith 调用次数 总计 不超过 3 * 10⁴ 次 
// 
// Related Topics 设计 字典树 哈希表 字符串 👍 1049 👎 0

package leetcode.editor.cn;

// 208.实现 Trie (前缀树):以下三种操作的时间复杂度都是O(n)
public class Code208_ImplementTriePrefixTree {

  //leetcode submit region begin(Prohibit modification and deletion)
  // 实现 Trie (前缀树):以下三种操作的时间复杂度都是O(n)
  class Trie {

    class TrieNode {

      TrieNode child[]; // 前缀树的节点可能有26个子节点
      boolean isWorld; // 到达该节点的路径对应的字符串是否为字典中的完整单词

      TrieNode() {
        child = new TrieNode[26];
      }
    }

    private TrieNode root;

    public Trie() {
      root = new TrieNode();
    }

    public void insert(String word) {
      // 1. 首先前往前缀树的根节点
      TrieNode node = root;
      // 2. 确定该根节点是否存在一个子节点与第一个字符对应，如果存在则前往，如果不存在则添加后前往
      for (char ch : word.toCharArray()) {
        if (node.child[ch - 'a'] == null) {
          node.child[ch - 'a'] = new TrieNode();
        }
        // 3. 以此类推将字符串中的其他字符添加到前缀树中
        node = node.child[ch - 'a'];
      }
      // 4. 全部添加进去后，将最后一个节点的标识设置为true
      node.isWorld = true;
    }

    // 思路和insert类似
    public boolean search(String word) {
      // 1. 从根节点开始查找，如果子节点没有与字符相等，返回false，否则继续往下查找，直到最后一个字符
      TrieNode node = root;
      for (char ch : word.toCharArray()) {
        if (node.child[ch - 'a'] == null) {
          return false;
        }
        node = node.child[ch - 'a'];
      }
      return node.isWorld;
    }

    public boolean startsWith(String prefix) {
      TrieNode node = root;
      for (char ch : prefix.toCharArray()) {
        if (node.child[ch - 'a'] == null) {
          return false;
        }
        node = node.child[ch - 'a'];
      }
      // 能查完prefix的全部字符，说明一定startsWith
      return true;
    }
  }

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
//leetcode submit region end(Prohibit modification and deletion)

}