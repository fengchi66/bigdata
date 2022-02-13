<p>给定两个字符串&nbsp;<code>s</code>&nbsp;和<b>&nbsp;</b><code>p</code>，找到&nbsp;<code>s</code><strong>&nbsp;</strong>中所有 <code>p</code> 的&nbsp;<strong>变位词&nbsp;</strong>的子串，返回这些子串的起始索引。不考虑答案输出的顺序。</p>

<p><strong>变位词 </strong>指字母相同，但排列不同的字符串。</p>

<p>&nbsp;</p>

<p><strong>示例&nbsp;1:</strong></p>

<pre>
<strong>输入: </strong>s = &quot;cbaebabacd&quot;, p = &quot;abc&quot;
<strong>输出: </strong>[0,6]
<strong>解释:</strong>
起始索引等于 0 的子串是 &quot;cba&quot;, 它是 &quot;abc&quot; 的变位词。
起始索引等于 6 的子串是 &quot;bac&quot;, 它是 &quot;abc&quot; 的变位词。
</pre>

<p><strong>&nbsp;示例 2:</strong></p>

<pre>
<strong>输入: </strong>s = &quot;abab&quot;, p = &quot;ab&quot;
<strong>输出: </strong>[0,1,2]
<strong>解释:</strong>
起始索引等于 0 的子串是 &quot;ab&quot;, 它是 &quot;ab&quot; 的变位词。
起始索引等于 1 的子串是 &quot;ba&quot;, 它是 &quot;ab&quot; 的变位词。
起始索引等于 2 的子串是 &quot;ab&quot;, 它是 &quot;ab&quot; 的变位词。
</pre>

<p>&nbsp;</p>

<p><strong>提示:</strong></p>

<ul>
	<li><code>1 &lt;= s.length, p.length &lt;= 3 * 10<sup>4</sup></code></li>
	<li><code>s</code>&nbsp;和 <code>p</code> 仅包含小写字母</li>
</ul>

<p>&nbsp;</p>

<p>注意：本题与主站 438&nbsp;题相同：&nbsp;<a href="https://leetcode-cn.com/problems/find-all-anagrams-in-a-string/" style="background-color: rgb(255, 255, 255);">https://leetcode-cn.com/problems/find-all-anagrams-in-a-string/</a></p>
<div><div>Related Topics</div><div><li>哈希表</li><li>字符串</li><li>滑动窗口</li></div></div><br><div><li>👍 11</li><li>👎 0</li></div>