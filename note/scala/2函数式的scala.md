# 1 函数式编程简介

## 面向对象编程

**解决问题，分解对象，行为，属性，然后通过对象的关系以及行为的调用来解决问题。**

- 对象：用户
- 行为：登录、连接JDBC、读取数据库
- 属性：用户名、密码

> Scala语言是一个完全面向对象编程语言。万物皆对象
>
> 对象的本质：对数据和行为的一个封装

## 函数式编程

**解决问题时，将问题分解成一个一个的步骤，将每个步骤进行封装（函数），通过调用这些封装好的步骤，解决问题。**

- 例如：请求->用户名、密码->连接JDBC->读取数据库

> Scala语言是一个完全函数式编程语言。万物皆函数。
>
> 函数的本质：函数可以当做一个值进行传递

# 2 函数类型

- 在`Scala`中，函数可以使用`函数字面值(Function Literal)`直接定义。有时候，函数字面值也常常称为`函数`或`函数值`。
- `在Scala中函数是一等公民`，当我们定义一个函数字面量时，实际上定义了一个包含apply方法的Scala对象。Scala对这个方法名有特别的规则，一个有apply方法的对象可以把它当成方法一样调用

```scala
val f1 = (a: String) => a.toLowerCase
```

## 匿名函数

事实上，「函数字面值」本质上是一个「匿名函数(Anonymous Function)」。在`Scala`里，函数被转换为`FunctionN`的实例。上例等价于：

```scala
val f2 = new Function[String, String] {
    override def apply(v1: String): String = v1.toLowerCase
  }
```

其中，`Function1[String, String]`可以简写为`String => String`，因此它又等价于：

```scala
  val f3 = new (String => String) {
    def apply(s: String): String = s.toLowerCase
  }
```

也就是说，「函数字面值」可以看做「匿名函数对象」的一个「语法糖」。

## 函数值

综上述，函数实际上是`FunctionN[A1, A2, ..., An, R]`类型的一个实例而已。例如`(s: String) => s.toLowerCase`是`Function1[String, String]`类型的一个实例。

在函数式编程中，`函数做为一等公民，函数值可以被自由地传递和存储。`例如，它可以赋予一个变量`lower`。

```scala
val lower: String => String = _.toLowerCase
```

假如存在一个`map`的柯里化的函数。

```scala
def map[A, B](a: A)(f: A => B): B = f(a)
```

函数值可以作为参数传递给`map`函数。

```scala
map("HORANCE") { _.toLowerCase }
```

## 有名函数

相对于「匿名函数」，如果将「函数值」赋予`def`，或者`val`，此时函数常称为「有名函数」(Named Function)。

```scala
val lower: String => String = _.toLowerCase
def lower: String => String = _.toLowerCase
```

两者之间存在微妙的差异。前者使用`val`定义的变量直接持有函数值，多次使用`lower`将返回同一个函数值；后者使用`def`定义函数，每次调用`lower`将得到不同的函数值。

## 函数调用

在`Scala`里，「函数调用」实际上等价于在`FunctionN`实例上调用`apply`方法。例如，存在一个有名函数`lower`。

```scala
val lower: String => String = _.toLowerCase
```

当发生如下函数调用时：

```scala
lower("HORANCE")
```

它等价于在`Function1[String, String]`类型的实例上调用`apply`方法。

```scala
lower.apply("HORANCE")
```

# 3 柯里化和闭包

## 函数柯里化(多参数列表)

柯里化(Currying)指的是将原来接受两个参数的函数变成新的接受一个参数的函数的过程。新的函数返回一个以原有第二个参数为参数的函数。

首先我们定义一个函数:

```scala
def add(x:Int,y:Int)=x+y
```

那么我们应用的时候，应该是这样用：add(1,2)

现在我们把这个函数变一下形：

```scala
def add(x:Int)(y:Int) = x + y
```

那么我们应用的时候，应该是这样用：add(1)(2),最后结果都一样是3，这种方式（过程）就叫柯里化。

#### 隐式（IMPLICIT）参数

如果要指定参数列表中的某些参数为隐式（implicit），应该使用多参数列表。例如：

```scala
def execute(arg: Int)(implicit ec: ExecutionContext) = ???
```

## 闭包

**「函数」和「函数内部能访问到的变量」（也叫环境）的总和，就是一个闭包**

```scala
var factor = 3
val adder = (i:Int) => i * factor
```

可能会有同学有疑问：浪尖，这不对啊？我看网上说的闭包构造是： 闭包首先有函数嵌套，内部函数引用外部函数的变量，然后返回的是一个函数。 应该是这个样子的：

```scala
object closure {
 def main(args: Array[String]): Unit = {
   println(makeAdd()(1))
 }
 def makeAdd() = {
   val more = 10
   (x: Int) => x + more
 }
}
```

**为啥要用函数嵌套？** 需要外部函数的作用主要是隐藏变量，限制变量作用的范围。 有些人看到「闭包」这个名字，就一定觉得要用什么包起来才行。其实这是翻译问题，闭包的原文是 Closure，跟「包」没有任何关系。 所以函数套函数只是为了造出一个局部变量，跟闭包无关。

**为啥要return函数呢？** 很明显，不return函数无法使用闭包～～ 那么现在换个脑子吧，我们将more 变成makeAdd的参数，那么就是下面的形式： def makeAdd(more : Int) = (x: Int) => x + more

# 4 高阶函数

高阶函数通常来讲就是`函数的函数`，也就是说函数的`输出参数是函数`或者`函数的返回结果是函数`。在Scala中函数是一等公民。

我们看一下Scala集合类（collections）的高阶函数map：

```scala
val salaries = Seq(20000, 70000, 40000)
val doubleSalary = (x: Int) => x * 2
val newSalaries = salaries.map(doubleSalary) // List(40000, 140000, 80000)
```

map接收一个函数为参数。所以map是一个高阶函数，map也可直接接收一个匿名函数，如下所示：

```scala
val salaries = Seq(20000, 70000, 40000)
val newSalaries = salaries.map(x => x * 2) // List(40000, 140000, 80000)
```

在上面的例子中，我们并没有显示使用x:Int的形式，这是因为编译器可以通过类型推断推断出x的类型，对其更简化的形式是：

```scala
val salaries = Seq(20000, 70000, 40000)
val newSalaries = salaries.map(_ * 2)
```

既然Scala编译器已经知道了参数的类型（一个单独的Int），你可以只给出函数的右半部分，不过需要使用_代替参数名（在上一个例子中是x）

## 函数可以作为值进行传递

```scala
def main(args: Array[String]): Unit = {

    //（1）调用foo函数，把返回值给变量f
    //val f = foo()
    val f = foo
    println(f) // foo... 1

    //（2）在被调用函数foo后面加上 _，相当于把函数foo当成一个整体，传递给变量f1
    val f1 = foo _

    foo() // foo...
    f1() // foo...
    //（3）如果明确变量类型，那么不使用下划线也可以将函数作为整体传递给变量
    var f2: () => Int = foo
  }

  def foo(): Int = {
    println("foo...")
    1
  }
```

## 函数可以作为参数进行传递

```scala
def main(args: Array[String]): Unit = {

    // （1）定义一个函数，函数参数还是一个函数签名；f表示函数名称;(Int,Int)表示输入两个Int参数；Int表示函数返回值
    def f1(f: (Int, Int) => Int): Int = {
      f(2, 4)
    }

    // （2）定义一个函数，参数和返回值类型和f1的输入参数一致
    def add(a: Int, b: Int): Int = a + b

    // （3）将add函数作为参数传递给f1函数，如果能够推断出来不是调用，_可以省略
    println(f1(add))
    println(f1(add _))
    //可以传递匿名函数
    println(f1((a: Int, b: Int) => a + b))
  }
```

## 函数可以作为函数返回值返回

```scala
def f1() = {
    def f2() = {}
    f2 _
  }

  val f = f1()
  // 因为f1函数的返回值依然为函数，所以可以变量f可以作为函数继续调用
  f()
  // 上面的代码可以简化为
  f1()()
```

有一些情况你希望生成一个函数， 比如：

```scala
def urlBuilder(ssl: Boolean, domainName: String): (String, String) => String = {
  val schema = if (ssl) "https://" else "http://"
  (endpoint: String, query: String) => s"$schema$domainName/$endpoint?$query"
}

val domainName = "www.example.com"
def getURL = urlBuilder(ssl=true, domainName)
val endpoint = "users"
val query = "id=1"
val url = getURL(endpoint, query) // "https://www.example.com/users?id=1": String
```

注意urlBuilder的返回类型是`(String, String) => String`，这意味着返回的匿名函数有两个String参数，返回一个String。在这个例子中，返回的匿名函数是`(endpoint: String, query: String) => s"https://www.example.com/$endpoint?$query"`。

# 5 模式匹配

模式匹配是检查某个值（value）是否匹配某一个模式的机制，一个成功的匹配同时会将匹配值解构为其组成部分。它是Java中的`switch`语句的升级版，同样可以用于替代一系列的 if/else 语句。

## 语法

一个模式匹配语句包括一个待匹配的值，`match`关键字，以及至少一个`case`语句。

```scala
import scala.util.Random

val x: Int = Random.nextInt(10)

x match {
  case 0 => "zero"
  case 1 => "one"
  case 2 => "two"
  case _ => "other"
}
```

上述代码中的`val x`是一个0到10之间的随机整数，将它放在`match`运算符的左侧对其进行模式匹配，`match`的右侧是包含4条`case`的表达式，其中最后一个`case _`表示匹配其余所有情况，在这里就是其他可能的整型值。

`match`表达式具有一个结果值

```
def matchTest(x: Int): String = x match {
  case 1 => "one"
  case 2 => "two"
  case _ => "other"
}
matchTest(3)  // other
matchTest(1)  // one
```

这个`match`表达式是String类型的，因为所有的情况（case）均返回String，所以`matchTest`函数的返回值是String类型。

## 案例类（case classes）的匹配

案例类非常适合用于模式匹配。

```scala
abstract class Notification

case class Email(sender: String, title: String, body: String) extends Notification

case class SMS(caller: String, message: String) extends Notification

case class VoiceRecording(contactName: String, link: String) extends Notification
```

`Notification` 是一个虚基类，它有三个具体的子类`Email`, `SMS`和`VoiceRecording`，我们可以在这些案例类(Case Class)上像这样使用模式匹配：

```scala
def showNotification(notification: Notification): String = {
  notification match {
    case Email(sender, title, _) =>
      s"You got an email from $sender with title: $title"
    case SMS(number, message) =>
      s"You got an SMS from $number! Message: $message"
    case VoiceRecording(name, link) =>
      s"you received a Voice Recording from $name! Click the link to hear it: $link"
  }
}
val someSms = SMS("12345", "Are you there?")
val someVoiceRecording = VoiceRecording("Tom", "voicerecording.org/id/123")

println(showNotification(someSms))  // prints You got an SMS from 12345! Message: Are you there?

println(showNotification(someVoiceRecording))  // you received a Voice Recording from Tom! Click the link to hear it: voicerecording.org/id/123
```

`showNotification`函数接受一个抽象类`Notification`对象作为输入参数，然后匹配其具体类型。（也就是判断它是一个`Email`，`SMS`，还是`VoiceRecording`）。在`case Email(sender, title, _)`中，对象的`sender`和`title`属性在返回值中被使用，而`body`属性则被忽略，故使用`_`代替。

还有一个案例是Flink官方文档的demo中，要实现一个`DataStream[Tuple2[String, String]]`类型数据的计数计算，定义了`CountWithTimestamp`类型的状态，基于该状态实现累加计算。

```scala
// the source data stream
val stream: DataStream[Tuple2[String, String]] = ...

// The data type stored in the state
case class CountWithTimestamp(key: String, count: Long, lastModified: Long)

// initialize or retrieve/update the state
val current: CountWithTimestamp = state.value match {
      case null =>
        CountWithTimestamp(value._1, 1, ctx.timestamp)
      case CountWithTimestamp(key, count, lastModified) =>
        CountWithTimestamp(key, count + 1, ctx.timestamp)
    }
```

## 模式守卫（Pattern gaurds）

为了让匹配更加具体，可以使用模式守卫，也就是在模式后面加上`if <boolean expression>`。

```scala
def showImportantNotification(notification: Notification, importantPeopleInfo: Seq[String]): String = {
  notification match {
    case Email(sender, _, _) if importantPeopleInfo.contains(sender) =>
      "You got an email from special someone!"
    case SMS(number, _) if importantPeopleInfo.contains(number) =>
      "You got an SMS from special someone!"
    case other =>
      showNotification(other) // nothing special, delegate to our original showNotification function
  }
}

val importantPeopleInfo = Seq("867-5309", "jenny@gmail.com")

val someSms = SMS("867-5309", "Are you there?")
val someVoiceRecording = VoiceRecording("Tom", "voicerecording.org/id/123")
val importantEmail = Email("jenny@gmail.com", "Drinks tonight?", "I'm free after 5!")
val importantSms = SMS("867-5309", "I'm here! Where are you?")

println(showImportantNotification(someSms, importantPeopleInfo))
println(showImportantNotification(someVoiceRecording, importantPeopleInfo))
println(showImportantNotification(importantEmail, importantPeopleInfo))
println(showImportantNotification(importantSms, importantPeopleInfo))
```

在`case Email(sender, _, _) if importantPeopleInfo.contains(sender)`中，除了要求`notification`是`Email`类型外，还需要`sender`在重要人物列表`importantPeopleInfo`中，才会匹配到该模式。

## 仅匹配类型

也可以仅匹配类型，如下所示：

```scala
abstract class Device
case class Phone(model: String) extends Device {
  def screenOff = "Turning screen off"
}
case class Computer(model: String) extends Device {
  def screenSaverOn = "Turning screen saver on..."
}

def goIdle(device: Device) = device match {
  case p: Phone => p.screenOff
  case c: Computer => c.screenSaverOn
}
```

当不同类型对象需要调用不同方法时，仅匹配类型的模式非常有用，如上代码中`goIdle`函数对不同类型的`Device`有着不同的表现。一般使用类型的首字母作为`case`的标识符，例如上述代码中的`p`和`c`，这是一种惯例。

## 密封类

特质（trait）和类（class）可以用`sealed`标记为密封的，这意味着其所有子类都必须与之定义在相同文件中，从而保证所有子类型都是已知的。

```scala
sealed abstract class Furniture
case class Couch() extends Furniture
case class Chair() extends Furniture

def findPlaceToSit(piece: Furniture): String = piece match {
  case a: Couch => "Lie on the couch"
  case b: Chair => "Sit on the chair"
}
```

这对于模式匹配很有用，因为我们不再需要一个匹配其他任意情况的`case`。

## 备注

Scala的模式匹配语句对于使用[案例类（case classes）](https://docs.scala-lang.org/zh-cn/tour/case-classes.html)表示的类型非常有用，同时也可以利用[提取器对象（extractor objects）](https://docs.scala-lang.org/zh-cn/tour/extractor-objects.html)中的`unapply`方法来定义非案例类对象的匹配。

# 6 偏函数

偏函数也是函数的一种，通过偏函数我们可以方便的对输入参数做更精确的检查。例如该偏函数的输入类型为List[Int]，而我们需要的是第一个元素是0的集合，这就是通过模式匹配实现的。

## 偏函数定义

实现一个偏函数的功能是返回输入的List集合的第二个元素

```scala
val second: PartialFunction[List[Int], Option[Int]] = {
    case x :: y :: _ => Some(y)
}
```

## 偏函数原理

上述代码会被scala编译器翻译成以下代码，与普通函数相比，只是多了一个用于参数检查的函数——isDefinedAt，其返回值类型为Boolean。

```scala
val second = new PartialFunction[List[Int], Option[Int]] {

    //检查输入参数是否合格
    override def isDefinedAt(list: List[Int]): Boolean = list match {
        case x :: y :: _ => true
        case _ => false
    }

    //执行函数逻辑
    override def apply(list: List[Int]): Option[Int] = list match {
        case x :: y :: _ => Some(y)
    }
}
```

## 偏函数使用

偏函数不能像second(List(1,2,3))这样直接使用，因为这样会直接调用apply方法，而应该调用applyOrElse方法，如下

```scala
second.applyOrElse(List(1,2,3), (_: List[Int]) => None)
```

applyOrElse方法的逻辑为 :

```scala
if (ifDefinedAt(list)) apply(list) else default
```

如果输入参数满足条件，即isDefinedAt返回true，则执行apply方法，否则执行defalut方法，default方法为参数不满足要求的处理逻辑。

## 案例需求

**将该List(1,2,3,4,5,6,"test")中的Int类型的元素加一，并去掉字符串。**

方法一：

```scala
List(1,2,3,4,5,6,"test").filter(_.isInstanceOf[Int]).map(_.asInstanceOf[Int] + 1).foreach(println)
```

方法二：

```scala
List(1, 2, 3, 4, 5, 6, "test").collect { case x: Int => x + 1 }.foreach(println)
```

# 7 嵌套函数

在Scala中可以嵌套定义函数，定义在函数内的函数称之为局部函数。例如以下对象提供了一个`factorial`方法来计算给定数值的阶乘：

```scala
 def factorial(x: Int): Int = {
    def fact(x: Int, accumulator: Int): Int = {
      if (x <= 1) accumulator
      else fact(x - 1, x * accumulator)
    }  
    fact(x, 1)
 }

 println("Factorial of 2: " + factorial(2))
 println("Factorial of 3: " + factorial(3))
```

程序的输出为:

```scala
Factorial of 2: 2
Factorial of 3: 6
```