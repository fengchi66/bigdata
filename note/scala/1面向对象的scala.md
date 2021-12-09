鉴于[一切值都是对象](https://docs.scala-lang.org/zh-cn/tour/unified-types.html)，可以说Scala是一门纯面向对象的语言。对象的类型和行为是由[类](https://docs.scala-lang.org/zh-cn/tour/classes.html)和[特质](https://docs.scala-lang.org/zh-cn/tour/traits.html)来描述的。类可以由子类化和一种灵活的、基于mixin的`组合机制`（它可作为多重继承的简单替代方案，后面学习设计模式的时候再详细说）来扩展。

# 1 类与对象

Classes就是类，和java中的类相似，它里面可以包含方法、常量、变量、类型、对象、特质、类等。

一个最简的类的定义就是关键字class+标识符，类名首字母应大写。如下所示：

```scala
class Family

val family = new Family
```

new关键字是用来创建类的实例。在上面的例子中，Family没有定义构造器，所以默认带有一个无参的默认的构造器。

- 构造器

那么怎么给类加一个构造器呢？

```scala
class Point(var x: Int, var y: Int) {

  override def toString: String =
    s"($x, $y)"
}

val point1 = new Point(2, 3)
point1.x  // 2
println(point1)  // prints (2, 3)
```

和其他的编程语言不同的是，Scala的类构造器定义在类的签名中：(var x: Int, var y: Int)。 这里我们还重写了AnyRef里面的toString方法。

构造器也可以拥有默认值：

```scala
class Point(var x: Int = 0, var y: Int = 0)

val origin = new Point  // x and y are both set to 0
val point1 = new Point(1)
println(point1.x)  // prints 1
```

主构造方法中带有val和var的参数是公有的。然而由于val是不可变的，所以不能像下面这样去使用。

```scala
class Point(val x: Int, val y: Int)
val point = new Point(1, 2)
point.x = 3  // <-- does not compile
```

不带val或var的参数是私有的，仅在类中可见。

```scala
class Point(x: Int, y: Int)
val point = new Point(1, 2)
point.x  // <-- does not compile
```

- 私有成员和Getter/Setter语法

Scala的成员默认是public的。如果想让其变成私有的，可以加上private修饰符，Scala的getter和setter语法和java不太一样，下面我们来举个例子：

```scala
class Point {

  private var _x = 0
  private var _y = 0
  private val bound = 100

  def x = _x
  def x_= (newValue: Int): Unit = {
    if (newValue < bound) _x = newValue else printWarning
  }

  def y = _y
  def y_= (newValue: Int): Unit = {
    if (newValue < bound) _y = newValue else printWarning
  }

  private def printWarning = println("WARNING: Out of bounds")
}

object Point {
  def main(args: Array[String]): Unit = {
    val point1 = new Point
    point1.x = 99
    point1.y = 101 // prints the warning
  }
}
```

我们定义了两个私有变量_x, *y, 同时还定义了他们的get方法x和y，那么相应的他们的set方法就是x* 和y_, 在get方法后面加上下划线就可以了。

# 2 特质

特质 (Traits) 用于在类 (Class)之间共享程序接口 (Interface)和字段 (Fields)。 它们类似于Java 8的接口。 类和对象 (Objects)可以扩展特质，但是特质不能被实例化，因此特质没有参数。

## 定义一个特质

最简化的特质就是关键字trait+标识符：

```scala
trait HairColor
```

特征作为泛型类型和抽象方法非常有用。

```scala
trait Iterator[A] {
  def hasNext: Boolean
  def next(): A
}
```

扩展 `trait Iterator [A]` 需要一个类型 `A` 和实现方法`hasNext`和`next`。

## 使用特质

使用 `extends` 关键字来扩展特征。然后使用 `override` 关键字来实现trait里面的任何抽象成员：

```scala
trait Iterator[A] {
  def hasNext: Boolean
  def next(): A
}

class IntIterator(to: Int) extends Iterator[Int] {
  private var current = 0
  override def hasNext: Boolean = current < to
  override def next(): Int =  {
    if (hasNext) {
      val t = current
      current += 1
      t
    } else 0
  }
}


val iterator = new IntIterator(10)
iterator.next()  // returns 0
iterator.next()  // returns 1
```

这个类 `IntIterator` 将参数 `to` 作为上限。它扩展了 `Iterator [Int]`，这意味着方法 `next` 必须返回一个Int。

## 子类型

凡是需要特质的地方，都可以由该特质的子类型来替换。

```scala
import scala.collection.mutable.ArrayBuffer

trait Pet {
  val name: String
}

class Cat(val name: String) extends Pet
class Dog(val name: String) extends Pet

val dog = new Dog("Harry")
val cat = new Cat("Sally")

val animals = ArrayBuffer.empty[Pet]
animals.append(dog)
animals.append(cat)
animals.foreach(pet => println(pet.name))  // Prints Harry Sally
```

在这里 `trait Pet` 有一个抽象字段 `name` ，`name` 由Cat和Dog的构造函数中实现。最后一行，我们能调用`pet.name`的前提是它必须在特质Pet的子类型中得到了实现。

# 3 封装

`封装`就是把抽象出的数据和对数据的操作封装在一起，数据被保护在内部，程序的其它部分只有通过被授权的操作（成员方法），才能对数据进行操作。**Java封装操作如下：**

1. **将属性进行私有化**
2. **提供一个公共的set方法，用于对属性赋值**
3. **提供一个公共的get方法，用于获取属性的值**

Scala中的public属性，底层实际为private，并通过get方法`（obj.field()）`和set方法`（obj.field_=(value)）`对其进行操作。所以Scala并不推荐将属性设为private，再为其设置public的get和set方法的做法。但由于很多Java框架都利用反射调用getXXX和setXXX方法，有时候为了和这些框架兼容，也会为Scala的属性设置getXXX和setXXX方法（通过@BeanProperty注解实现）

## 访问权限

在Java中，访问权限分为：public，private，protected和默认。在Scala中，你可以通过类似的修饰符达到同样的效果。但是使用上有区别。