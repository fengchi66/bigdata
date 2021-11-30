<p>请编写一个函数，用于 <strong>删除单链表中某个特定节点</strong> 。在设计函数时需要注意，你无法访问链表的头节点&nbsp;<code>head</code> ，只能直接访问 <strong>要被删除的节点</strong> 。</p>

<p>题目数据保证需要删除的节点 <strong>不是末尾节点</strong> 。</p>

<p>&nbsp;</p>

<p><strong>示例 1：</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2020/09/01/node1.jpg" style="width: 450px; height: 322px;" />
<pre>
<strong>输入：</strong>head = [4,5,1,9], node = 5
<strong>输出：</strong>[4,1,9]
<strong>解释：</strong>指定链表中值为&nbsp;5&nbsp;的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -&gt; 1 -&gt; 9
</pre>

<p><strong>示例 2：</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2020/09/01/node2.jpg" style="width: 450px; height: 354px;" />
<pre>
<strong>输入：</strong>head = [4,5,1,9], node = 1
<strong>输出：</strong>[4,5,9]
<strong>解释：</strong>指定链表中值为&nbsp;1&nbsp;的第三个节点，那么在调用了你的函数之后，该链表应变为 4 -&gt; 5 -&gt; 9</pre>

<p><strong>示例 3：</strong></p>

<pre>
<strong>输入：</strong>head = [1,2,3,4], node = 3
<strong>输出：</strong>[1,2,4]
</pre>

<p><strong>示例 4：</strong></p>

<pre>
<strong>输入：</strong>head = [0,1], node = 0
<strong>输出：</strong>[1]
</pre>

<p><strong>示例 5：</strong></p>

<pre>
<strong>输入：</strong>head = [-3,5,-99], node = -3
<strong>输出：</strong>[5,-99]
</pre>

<p>&nbsp;</p>

<p><strong>提示：</strong></p>

<ul>
	<li>链表中节点的数目范围是 <code>[2, 1000]</code></li>
	<li><code>-1000 &lt;= Node.val &lt;= 1000</code></li>
	<li>链表中每个节点的值都是唯一的</li>
	<li>需要删除的节点 <code>node</code> 是 <strong>链表中的一个有效节点</strong> ，且 <strong>不是末尾节点</strong></li>
</ul>
<div><div>Related Topics</div><div><li>链表</li></div></div><br><div><li>👍 1065</li><li>👎 0</li></div>