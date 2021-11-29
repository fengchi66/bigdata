<p>给定一个 N 叉树，返回其节点值的<strong> 前序遍历</strong> 。</p>

<p>N 叉树 在输入中按层序遍历进行序列化表示，每组子节点由空值 <code>null</code> 分隔（请参见示例）。</p>

<div class="original__bRMd">
<div>
<p> </p>

<p><strong>进阶：</strong></p>

<p>递归法很简单，你可以使用迭代法完成此题吗?</p>

<p> </p>

<p><strong>示例 1：</strong></p>

<p><img src="https://assets.leetcode.com/uploads/2018/10/12/narytreeexample.png" style="width: 100%; max-width: 300px;" /></p>

<pre>
<strong>输入：</strong>root = [1,null,3,2,4,null,5,6]
<strong>输出：</strong>[1,3,5,6,2,4]
</pre>
<strong>示例 2：</strong>

<p><img alt="" src="https://assets.leetcode.com/uploads/2019/11/08/sample_4_964.png" style="width: 296px; height: 241px;" /></p>

<pre>
<strong>输入：</strong>root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
<strong>输出：</strong>[1,2,3,6,7,11,14,4,8,12,5,9,13,10]
</pre>

<p> </p>

<p><strong>提示：</strong></p>

<ul>
	<li>N 叉树的高度小于或等于 <code>1000</code></li>
	<li>节点总数在范围 <code>[0, 10^4]</code> 内</li>
</ul>
</div>
</div>
<div><div>Related Topics</div><div><li>栈</li><li>树</li><li>深度优先搜索</li></div></div><br><div><li>👍 194</li><li>👎 0</li></div>