* [基本概念](#基本概念)
* [二叉树的遍历](#二叉树的遍历)
  * [递归解法](#递归解法)
    * [递归序](#递归序)
    * [前序遍历](#前序遍历)
    * [中序遍历](#中序遍历)
    * [后续遍历](#后续遍历)
  * [迭代解法](#迭代解法)
    * [前序遍历](#前序遍历-1)
    * [中序遍历](#中序遍历-1)
    * [后续遍历](#后续遍历-1)
    * [层序遍历](#层序遍历)
* [二叉树序列化与反序列化](#二叉树序列化与反序列化)
  * [先序方式](#先序方式)
    * [序列化](#序列化)
    * [反序列化](#反序列化)
  * [层序方式](#层序方式)
    * [序列化](#序列化-1)
    * [反序列化](#反序列化-1)
* [判断二叉树是否是完全二叉树](#判断二叉树是否是完全二叉树)
    * [层序遍历](#层序遍历-1)
* [递归套路](#递归套路)
  * [判断二叉树是否是平衡二叉树](#判断二叉树是否是平衡二叉树)
  * [判断二叉树是否是搜索二叉树](#判断二叉树是否是搜索二叉树)
  * [二叉树最大距离](#二叉树最大距离)
  * [二叉树中的最大搜索二叉子树](#二叉树中的最大搜索二叉子树)
  * [判断二叉树是否是满二叉树](#判断二叉树是否是满二叉树)

##  基本概念

树是一种非线性数据结构。树结构的基本单位是节点。节点之间的链接，称为分支（branch）。节点与分支形成树状，结构的开端，称为**根（root）**，或根结点。根节点之外的节点，称为**子节点（child）**。没有链接到其他子节点的节点，称为**叶节点（leaf**）。如下图是一个典型的树结构：

![image-20210702164308779](https://gitee.com/joeyooa/data-images/raw/master/note/2021/image-20210702164308779.png)

**每个节点可以用以下数据结构来表示: (二叉树)**

```java
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {}
    TreeNode(int val) {this.val = val;}
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
```

其他重要概念：

- **树的高度**：节点到叶子节点的最大值就是其高度。
- **树的深度**：高度和深度是相反的，高度是从下往上数，深度是从上往下。因此根节点的深度和叶子节点的高度是 0。
- **树的层**：根开始定义，**根为第一层，根的孩子为第二层**。
- **二叉树**，三叉树，。。。 N 叉树，由其子节点最多可以有几个决定，最多有 N 个就是 N 叉树。

## 二叉树的遍历

二叉树是树结构的一种，两个叉就是说每个节点**最多**只有两个子节点，我们习惯称之为左节点和右节点。

遍历二叉树的方法主要有以下几种:

- 前序遍历
- 中序遍历
- 后续遍历
- 层序遍历

![img](https://gitee.com/joeyooa/data-images/raw/master/note/2021/ff26c3f4485c043a17923c3dcab65891f0d32c45e1400c36364e5084462bf2e4.png)

- 如果按照 **根节点 -> 左孩子 -> 右孩子** 的方式遍历，即「前序遍历」，每次先遍历根节点，遍历结果为 1 2 4 5 3 6 7；

- 同理，如果你按照 **左孩子 -> 根节点 -> 右孩子** 的方式遍历，即「中序遍历」，遍历结果为 4 2 5 1 6 3 7；

- 如果你按照 **左孩子 -> 右孩子 -> 根节点** 的方式遍历，即「后序遍历」，遍历结果为 4 5 2 6 7 3 1；

- 最后，层次遍历就是按照每一层从左向右的方式进行遍历，遍历结果为 1 2 3 4 5 6 7。

### 递归解法

#### 递归序

- **理解递归序**
- 前序、中序、后续都可以在递归序的基础上加工出来
- 第一次到达一个节点就打印就是先序、第二次打印即中序，第三次是后续

#### 前序遍历

```java
public static void preOrderRecur(TreeNode head) {
    if (head == null) return;
    System.out.print(head.val + " ");
    preOrderRecur(head.left); 
    preOrderRecur(head.right);
}
```

#### 中序遍历

```java
public static void preOrderRecur(TreeNode head) {
    if (head == null) return;
    preOrderRecur(head.left);
    System.out.print(head.val + " ")
    preOrderRecur(head.right);
}
```

#### 后续遍历

```java
public static void preOrderRecur(TreeNode head) {
    if (head == null) return;
    preOrderRecur(head.left);
    preOrderRecur(head.right);
    System.out.print(head.val + " ")
}
```

**复杂度分析**

- 时间复杂度：O(n)，其中 nn 是二叉树的节点数。每一个节点恰好被遍历一次。

- 空间复杂度：O(n)，为递归过程中栈的开销，平均情况下为 O(log n)，最坏情况下树呈现链状，为 O(n)。

### 迭代解法

本质上是在模拟递归，因为在递归的过程中使用了系统栈，所以在迭代的解法中常用`Stack`来模拟系统栈。

#### 前序遍历

使用栈来进行迭代，过程如下：

- **初始化栈，并将根节点入栈；**
  **当栈不为空时：**

  - **弹出栈顶元素 node，并将值添加到结果中；**
  - **如果 node 的右子树非空，将右子树入栈；**
  - **如果 node 的左子树非空，将左子树入栈；**

  ![中序遍历流程图](https://gitee.com/joeyooa/data-images/raw/master/note/2021/6233a9685447d0b4d7b513f739151ca065e5697e24070bcafc1ee5d28f9155a6.png)

由于栈是“先进后出”的顺序，所以**入栈时先将右子树入栈，这样使得前序遍历结果为 “根->左->右”的顺序**。

```java
 	public List<Integer> preorderTraversal2(TreeNode root) {

        ArrayList<Integer> res = new ArrayList<>();

        if (root  == null) return res;

        // 将root结点加入到res中
        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            res.add(node.val);

            // 有孩子将先右孩子加入栈
            if (node.right != null)
                stack.push(node.right);
            // 有孩子将先右孩子加入栈
            if (node.left != null)
                stack.push(node.left);
        }
        return res;
    }
```

#### 中序遍历

- 当前结点cur，以cur为头的树的整个左边界入栈，直到遇到空。
- 当遇到空的之后，从栈中弹出结点cur打印，让这个结点右孩子成为cur。

```java
	public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while(cur != null || !stack.isEmpty()){
            if(cur != null){ 
                stack.push(cur);
                cur = cur.left; // left
            }else{
                cur = stack.pop();
                res.add(cur.val); // root
                cur = cur.right; // right
            }
        }
        return res;
    }
```

#### 后续遍历

- 前序遍历顺序为：根 -> 左 -> 右
- 后序遍历顺序为：左 -> 右 -> 根
- 所以, 可以**把前序遍历的稍作修改: 根 -> 右 -> 左,  然后结果存放到栈里进行倒序, 之后再遍历结果栈就可以输出后序遍历了**

```java
   public List<Integer> postorderTraversal(TreeNode root) {

        ArrayList<Integer> res = new ArrayList<>();

        if (root == null) return res;

        Stack<TreeNode> s1 = new Stack<>();
        Stack<TreeNode> s2 = new Stack<>();

        // 模拟前序遍历，现将root加入s1中
        s1.push(root);

        while (!s1.isEmpty()) {
            // 从s1中弹出元素加入s2中
            root = s1.pop();
            s2.push(root);

            if (root.left != null)
                s1.push(root.left);
            if (root.right != null)
                s1.push(root.right);
        }
        // 所有元素都按照：头 -> 右 - > 左加入到s2了,从s2取出来就是 左 -> 右 -> 头的关系(后序)
        while (!s2.isEmpty())
            res.add(s2.pop().val);

        return res;
    }
```

#### 层序遍历

- 其实就是宽度优先遍历，用队列
- 可以通过设置flag变量，来发现某一层的结束

```java
 public void levelOrder(TreeNode root) {
        
        if (root == null) return;

        Queue<TreeNode> queue = new LinkedList<>();
        // 首先将头节点加入到queue中
        queue.add(root);
        
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            System.out.println(cur.val);
            
            // cur有左孩子先将左孩子加入队列
            if (cur.left != null)
                queue.add(cur.left);

            // 然后将右孩子加入队列 
            if (cur.right != null)
                queue.add(cur.right);
        }
    }
```

## 二叉树序列化与反序列化

序列化是将数据结构或对象转换为一系列位的过程，以便它可以存储在文件或内存缓冲区中，或通过网络连接链路传输，以便稍后在同一个或另一个计算机环境中重建。

树的结构和字符串一一对应。

### 先序方式

#### 序列化

- 先序遍历，遇到null给一个特殊字符`#`
- 每个结点值之间字符串用`,`分割

```java
public static Queue<String> preSerial(Node root) {
        Queue<String> res = new LinkedList<>();
        pres(root, res);
        
        return res;
    }
    
    // 先序遍历,遇到null时将null加入到队列中
    public static void pres(Node root, Queue<String> res) {
        if (root == null) {
            res.add(null);
        } else { // 不为null时分别遍历左、右子树
            res.add(String.valueOf(root.value));
            pres(root.left, res);
            pres(root.right, res);
        }
    }
```

#### 反序列化

- 以此弹出队列中的元素，按照先序的方式依次建立结点，遇到null则返回null。

```java
public static Node buildByPreQueue(Queue<String> prelist) {
		if (prelist == null || prelist.size() == 0) {
			return null;
		}
		return preb(prelist);
	}

	public static Node preb(Queue<String> prelist) {
		String value = prelist.poll();
		if (value == null) {
			return null;
		}
		Node head = new Node(Integer.valueOf(value));
		head.left = preb(prelist);
		head.right = preb(prelist);
		return head;
	}
```

### 层序方式

#### 序列化

- **和层序遍历差不多，不同的是`不能忽略null`**

```java
    public static Queue<String> levelSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        // 1. 如果为空树，返回null
        if (head == null) {
            ans.add(null);
        } else {
            ans.add(String.valueOf(head.value));
            Queue<Node> queue = new LinkedList<Node>();
            queue.add(head);
            while (!queue.isEmpty()) {
                head = queue.poll(); // head 父   子
                // 2. 和层序遍历一样，分别将left、right加入到队列中；left、right为空时也加到结果queue中
                if (head.left != null) {
                    ans.add(String.valueOf(head.left.value));
                    queue.add(head.left);
                } else {
                    ans.add(null);
                }
                if (head.right != null) {
                    ans.add(String.valueOf(head.right.value));
                    queue.add(head.right);
                } else {
                    ans.add(null);
                }
            }
        }
        return ans;
    }
```

#### 反序列化

```java
public static Node buildByLevelQueue(Queue<String> levelList) {
		if (levelList == null || levelList.size() == 0) {
			return null;
		}
		Node head = generateNode(levelList.poll());
		Queue<Node> queue = new LinkedList<Node>();
		if (head != null) {
			queue.add(head);
		}
		Node node = null;
		while (!queue.isEmpty()) {
			node = queue.poll();
			node.left = generateNode(levelList.poll());
			node.right = generateNode(levelList.poll());
			if (node.left != null) {
				queue.add(node.left);
			}
			if (node.right != null) {
				queue.add(node.right);
			}
		}
		return head;
	}

	public static Node generateNode(String val) {
		if (val == null) {
			return null;
		}
		return new Node(Integer.valueOf(val));
	}
```



## 判断二叉树是否是完全二叉树

完全二叉树是由满二叉树而引出来的，若设二叉树的深度为h，除第 h 层外，其它各层 (1～h-1) 的结点数都达到最大个数(即1~h-1层为一个满二叉树)，第 h 层所有的结点都连续集中在最左边，这就是完全二叉树。 

- 按层遍历二叉树，从每层的左边向右边依次遍历所有的节点。
- **如果当前节点有右孩子节点，但没有左孩子节点，则直接返回false。**
- **如果当前节点并不是左右孩子节点全有，那么之后的节点必须都为叶节点，否则返回false。**
- 遍历过程中如果不返回false，则遍历结束后返回true。

 #### 层序遍历

```java
public static boolean isCBT1(Node head) {
        if (head == null) {
            return true;
        }
        LinkedList<Node> queue = new LinkedList<>();
        // 是否遇到过左右两个孩子不双全的节点
        boolean leaf = false;
        
        queue.add(head);
        while (!queue.isEmpty()) {
            head = queue.poll();
            Node l = head.left;
            Node r = head.right;
            // 如果遇到了不双全的节点之后，又发现当前节点不是叶节点
            if ((leaf && (l != null || r != null)) || (l == null && r != null))
                return false;
            if (l != null)
                queue.add(l);
            if (r != null)
                queue.add(r);
            if (l == null || r == null)
                leaf = true;
        }
        return true;
    }
```

## 递归套路

- 可以解决面试中绝大多数的二叉树问题尤其是树型dp问题
- 本质是利用递归遍历二叉树的便利性

**二叉树的递归，主要可以分为以下几个步骤：**

- 假设以X结点为头，假设可以向X左树和X右树要任何信息
- 在上一步的假设下，讨论以X为头结点的树，得到**答案的可能性**(最重要)
- 列出所有可能性后，确定到底需要向左树和右树传递什么**信息**
- 把左树信息和右树信息求全集，就是任何子树都需要返回的信息S
- **递归函数都返回S，每一颗子树都这么要求**
- **写代码，在代码中考虑如何把左树信息和右树的信息整合出整棵树的信息**

### 判断二叉树是否是平衡二叉树

平衡二叉树：每一颗子树的左树高度和右树高度差绝对值不超过1.

 以X节点为头的二叉树，判断是否为平衡二叉树。分析X满足平衡二叉树的条件：

- **X的左树为平衡二叉树**
- **X的右树为平衡二叉树**
- **|左子树高度 - 右子树高度| <= 1**

按照递归套路，此时X需要向它的左子树要两个信息：**高度、是否平**；需要向它的右树也是要两个信息：**高度、是否平**，那么就可以得到正确的答案。

```java
   // 函数，判断二叉树是否是平衡二叉树
    public static boolean isBalanced(Node node) {
        return process(node).isBalanced;

    }

    // 递归函数, 返回Info
    public static Info process(Node x) {
        if (x == null)
            return new Info(true, 0);

        // 默认能向左树和右树拿到信息
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        // 左右树信息求全集，此时得到以X为头的树是否是平的以及高度的信息

        // 高度：左树和右树的最大高度 + 1
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        // 判断是不是平的,先假设自己是平的，当左树或者右树不是平的时候，自己也不是平的了
        boolean isBalanced = true;
        if (!leftInfo.isBalanced)
            isBalanced = false;
        if (!rightInfo.isBalanced)
            isBalanced = false;
        // 左右子树高度差大于1，也不平
        if (Math.abs(leftInfo.height - rightInfo.height) > 1)
            isBalanced = false;

        // 返回自己的Info
        return new Info(isBalanced, height);
    }


   // 实体类，定义递归函数的返回结果，也就是x结点要想左、又子树拿到的信息
    public static class Info {
        public boolean isBalanced; // 是否平
        public int height; // 子树的高度

        public Info(boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }
```

### 判断二叉树是否是搜索二叉树

二叉搜索树，也称二叉搜索树、有序二叉树（Ordered Binary Tree）、 排序二叉树（Sorted Binary Tree），是指一棵空树或者具有下列性质的 二叉树： 

- **左子树上所有结点的值均小于它的根结点的值；**
- **右子树上所有结点的值均大于它的根结点的值；**
- **以此类推：所有左子树和右子树自身也必须是二叉搜索树。** （这就是 重复性！） 中序遍历：升序排列

递归套路，以X为头的二叉树，具有以下特征：

- **X的左树是一颗搜索树**
- **X的右树是一颗搜索树**
- **且左树上结点值的最大值小于X**
- **右树上 结点的最小值大于X**。

我们对左树提要求：是否是搜索二叉树，左树上结点的最大值；对右树提要求：是否是搜索二叉树，右树上结点的最小值。但对左树和右树的要求不一样时，就求全集：左树也要最小时，右树也要结点的最大值，如此就可以统一递归函数的返回结果，以为递归对所有结点一视同仁，不需要去定制左树和右树的信息。因此，对左树和右树的要求是：

- **是否是二叉搜索树**
- **子树结点的最小值**
- **子树结点的最大值**

**代码实现：**

```java
    // 判断二叉树是否是搜索二叉树
    public static boolean isBST(Node node) {
      	if (root == null) return true;
        return process(node).isBST;
    }

    // 递归函数
    public static Info process(Node x) {
        // base case
        if (x == null) // 不知道如何初始化就返回null，在上游去处理null
            return null;

        // 向左树和右树要信息
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        // 先假设自己的值是最大值,然后依次去和左、右树比较
        int max = x.value;
        if (leftInfo != null)
            max = Math.max(max, leftInfo.max);
        if (rightInfo != null)
            max = Math.max(max, rightInfo.max);

        // 同理，最小值取值
        int min = x.value;
        if (leftInfo != null)
            min = Math.min(min, leftInfo.min);
        if (rightInfo != null)
            min = Math.max(min, rightInfo.min);

        // 先认为自己是搜索二叉树，看那些情况下会违反
        boolean isBST = true;
        // 1. 左树不是搜索二叉树
        if (leftInfo != null && !leftInfo.isBST)
            isBST = false;
        // 2. 右树不是搜索二叉树
        if (rightInfo != null && !rightInfo.isBST)
            isBST = false;
        // 3. 左树不为空且左树最大值不小于当前X的值
        if (leftInfo != null && leftInfo.max >= x.value)
            isBST = false;
        // 4. 右树不为空且右树最大值不大于当前X的值
        if (rightInfo != null && rightInfo.min <= x.value)
            isBST = false;

        return new Info(isBST, max,min);

    }

    public static class Info {
        public boolean isBST; // 是否是搜索二叉树
        public int max; // 最大值
        public int min; // 最小值

        public Info(boolean isBST, int max, int min) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
        }
    }

```

### 二叉树最大距离

**题目描述**

从二叉树的节点A出发，可以向上或者向下走，但沿途的节点只能经过一次，当到达节点B时，路径上的节点数叫作A到B的距离。

比如，图所示的二叉树，节点4和节点2的距离为2，节点5和节点6的距离为5。给定一棵二叉树的头节点head，求整棵树上节点间的最大距离。

![image-20210704165518871](https://gitee.com/joeyooa/data-images/raw/master/note/2021/image-20210704165518871.png)

**分析答案的可能性**

任意两个结点之间的距离，存在两种情况：

- 与x无关，不经过x
  - 左树上的最大距离
  - 右树上的最大距离
- 与x有关，经过x
  - 此时的最大距离是左树上最远（左树的高度）到右树上最远（右树的高度）的距离 + 1

**根据可能性，列出所有需要的信息**

左子树和右子树都需要知道自己这棵子树上的**最大距离**，以及**高度**这两个信息。

**根据第二步信息汇总。定义信息类**

**递归函数设计**

递归函数是处理以X为头节点的情况下的答案，包括设计递归的base case、默认直接得到左树和右树的所有信息，以及把可能性做整合，并且也要返回第三步的信息结构这四个小步骤。

```java
public static int maxDistance(Node head) {
        return process(head).maxDistance;
    }

    // 递归函数
    public static Info process(Node x) {
        // 当x为null时，最大距离和高度设置为0即可
        if (x == null) return new Info(0, 0);

        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        int height = Math.max(leftInfo.height , rightInfo.height) + 1;

        // 最大距离，3种情况取最大值
        int p1 = leftInfo.maxDistance;
        int p2 = rightInfo.maxDistance;
        int p3 = leftInfo.height + rightInfo.height + 1;

        int maxDistance = Math.max(Math.max(p1, p2), p3);

        return new Info(maxDistance, height);

    }


    public static class Info {
        public int maxDistance; // 最大距离
        public int height; // 高度

        public Info(int maxDistance, int height) {
            this.maxDistance = maxDistance;
            this.height = height;
        }
    }
```

### 二叉树中的最大搜索二叉子树

给定一棵二叉树的头节点head，已知其中所有节点的值都不一样，找到含有节点最多的搜索二叉子树，并返回这棵子树的头节点。

例如，二叉树如图3-17所示。这棵树中的最大搜索二叉子树如图3-18所示：

![image-20210704173831644](https://gitee.com/joeyooa/data-images/raw/master/note/2021/image-20210704173831644.png)

**分析答案的可能性**

- 答案来自左子树
- 答案来自右子树
- 答案来自左子树 + 右子树的全部，并且X的值大于X左子树所有节点的最大值，但小于X右子树所有节点的最小值。也就是整个x。

**需要从子树中拿到的信息**

- 子树的头
- 子树的大小
- 左子树结点的最大值
- 右子树结点的最小值

[代码](https://github.com/algorithmzuo/algorithmbasic2020/blob/master/src/class12/Code05_MaxSubBSTSize.java)

### 判断二叉树是否是满二叉树

**分析答案的可能性**

满二叉树的特征是：**节点数量 n 与树的高度 h 关系是 n = 2 ^ h - 1**。满足该条件的即是满二叉树。

**需要从子树中拿到的信息**

- 树的高度
- 节点数量

```java
public static boolean isFull1(Node head) {
        if (head == null) {
            return true;
        }
        Info all = process(head);
        return (1 << all.height) - 1 == all.nodes;
    }

    // 递归函数
    public static Info process(Node head) {
        if (head == null) {
            return new Info(0, 0);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int nodes = leftInfo.nodes + rightInfo.nodes + 1;
        return new Info(height, nodes);
    }

    public static class Info {
        public int height;
        public int nodes;

        public Info(int h, int n) {
            height = h;
            nodes = n;
        }
    }
```

