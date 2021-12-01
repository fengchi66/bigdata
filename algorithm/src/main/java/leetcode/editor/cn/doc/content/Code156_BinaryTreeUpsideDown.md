<p>给你一个二叉树的根节点 <code>root</code> ，请你将此二叉树上下翻转，并返回新的根节点。</p>

<p>你可以按下面的步骤翻转一棵二叉树：</p>

<ol>
	<li>原来的左子节点变成新的根节点</li>
	<li>原来的根节点变成新的右子节点</li>
	<li>原来的右子节点变成新的左子节点</li>
</ol>
<img alt="" src="https://assets.leetcode.com/uploads/2020/08/29/main.jpg" style="width: 600px; height: 95px;" />
<p>上面的步骤逐层进行。题目数据保证每个右节点都有一个同级节点（即共享同一父节点的左节点）且不存在子节点。</p>

<p>&nbsp;</p>

<p><strong>示例 1：</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2020/08/29/updown.jpg" style="width: 800px; height: 161px;" />
<pre>
<strong>输入：</strong>root = [1,2,3,4,5]
<strong>输出：</strong>[4,5,2,null,null,3,1]
</pre>

<p><strong>示例 2：</strong></p>

<pre>
<strong>输入：</strong>root = []
<strong>输出：</strong>[]
</pre>

<p><strong>示例 3：</strong></p>

<pre>
<strong>输入：</strong>root = [1]
<strong>输出：</strong>[1]
</pre>

<p>&nbsp;</p>

<p><strong>提示：</strong></p>

<ul>
	<li>树中节点数目在范围 <code>[0, 10]</code> 内</li>
	<li><code>1 &lt;= Node.val &lt;= 10</code></li>
	<li>树中的每个右节点都有一个同级节点（即共享同一父节点的左节点）</li>
	<li>树中的每个右节点都没有子节点</li>
</ul>
<div><div>Related Topics</div><div><li>树</li><li>深度优先搜索</li><li>二叉树</li></div></div><br><div><li>👍 83</li><li>👎 0</li></div>