>非原厂，整理自尚硅谷和极客时间课程

## Future和Callable接口

**Future接口定义了操作异步任务执行一些方法**，如获取异步任务的执行结果、取消任务的执行、判断任务是否被取消、判断任务执行是否完毕等。

![img](https://gitee.com/joeyooa/data-images/raw/master/node/2021/DB48CA0A-7460-4F91-A39A-5E06015FCE13.png)

**Callable接口中定义了需要有返回的任务需要实现的方法**，比如主线程让一个子线程去执行任务，子线程可能比较耗时，启动子线程开始执行任务后，主线程就去做其他事情了，过了一会才去获取子任务的执行结果。

## 从FutureTask说起

### Future接口

![img](https://gitee.com/joeyooa/data-images/raw/master/node/2021/05EC5A05-2F91-4EFB-A7B0-355C79983396.png)



### get()阻塞

- 一旦调用get()方法，不管是否计算完成都会导致阻塞

  ```java
  FutureTask futureTask = new FutureTask(() -> {
        System.out.println("-----come in FutureTask");
        try {
          TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        return "" + ThreadLocalRandom.current().nextInt(100);
      });
  
      new Thread(futureTask, "t1").start();
  
      futureTask.get();
  
      System.out.println("main...");
  ```

### isDone()轮询

- 如果想要异步获取结果,通常都会以轮询的方式去获取结果，尽量不要阻塞
- 轮询的方式会耗费无谓的CPU资源，而且也不见得能及时地得到计算结果

```java
FutureTask futureTask = new FutureTask(() -> {
      System.out.println("-----come in FutureTask");
      try {
        TimeUnit.SECONDS.sleep(3);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return "" + ThreadLocalRandom.current().nextInt(100);
    });

    new Thread(futureTask, "t1").start();

    System.out.println(Thread.currentThread().getName() + "\t" + "线程完成任务");

/**
 * 用于阻塞式获取结果,如果想要异步获取结果,通常都会以轮询的方式去获取结果
 */
    while (true) {
      if (futureTask.isDone()) {
        System.out.println(futureTask.get());
        break;
      }
    }
```

### 想完成一些复杂的任务

- 应对Future的完成时间，完成了可以告诉我，也就是我们的回调通知
- 将两个异步计算合成一个异步计算，这两个异步计算互相独立，同时第二个又依赖第一个的结果。
- 当Future集合中某个任务最快结束时，返回结果。
- 等待Future结合中的所有任务都完成。

## 对Future的改进

### CompletableFuture和CompletionStage源码

![img](/private/var/folders/h6/j9ckz3ns7w777cr87s4267p80000gn/T/com.apple.MindMaster/1731e5b167/bin/77C1829D-4474-4882-8D4A-25C72A01D108.png)

![img](/private/var/folders/h6/j9ckz3ns7w777cr87s4267p80000gn/T/com.apple.MindMaster/1731e5b167/bin/C8E7B14A-5233-4528-8E57-7B86F631850C.png)

- 接口**CompletionStage**代表异步计算过程中的某一个阶段，一个阶段完成以后可能会触发另外一个阶段，有些类似Linux系统的管道分隔符传参数。
- **CompletableFuture**它提供了非常强大的 Future 的扩展功能，可以帮助我们简化异步编程的复杂性，并且提供了函数式编程的能力，可以通过回调的方式处理计算结果，也提供了转换和组合 CompletableFuture 的方法。

### CompletableFuture方法

**runAsync 无 返回值**

```java
public static CompletableFuture<Void> runAsync(Runnable runnable)
public static CompletableFuture<Void> runAsync(Runnable runnable,Executor executor)
```

**supplyAsync 有 返回值**

```java
public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier,Executor executor)
```

**上述Executor executor参数说明**

- 没有指定Executor的方法，直接使用默认的ForkJoinPool.commonPool() 作为它的线程池执行异步代码，这个线程池默认创建的线程数是 CPU 的核数
- 如果指定线程池，则使用我们自定义的或者特别指定的线程池执行异步代码

### Code之通用演示，减少阻塞和轮询

- 从Java8开始引入了CompletableFuture，它是Future的功能增强版，可以传入回调对象，当异步任务完成或者发生异常时，自动调用回调对象的回调方法

  ```java
  public static void main(String[] args) {
      CompletableFuture completableFuture = CompletableFuture.supplyAsync(() -> {
        System.out.println(Thread.currentThread().getName() + "\t" + "-----come in");
        int result = ThreadLocalRandom.current().nextInt(10);
  //暂停几秒钟线程
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("-----计算结束耗时1秒钟，result： " + result);
        if (result > 6) {
          int age = 10 / 0;
        }
        return result;
      }).whenComplete((v, e) -> {
        if (e == null) {
          System.out.println("-----result: " + v);
        }
      }).exceptionally(e -> {
        System.out.println("-----exception: " + e.getCause() + "\t" + e.getMessage());
        return -44;
      });
  
  //主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:暂停3秒钟线程
      try {
        TimeUnit.SECONDS.sleep(3);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  ```

### CompletableFuture的优点

- 异步任务结束时，会自动回调某个对象的方法；
- 异步任务出错时，会自动回调某个对象的方法；
- 主线程设置好回调后，不再关心异步任务的执行，异步任务之间可以顺序执行



## CompletableFuture案例需求

### java8函数式接口

| 函数式接口名称 | 方法名称 | 参数    | 返回值   |
| -------------- | -------- | ------- | -------- |
| Runnable       | run      | 无参数  | 无返回值 |
| Function       | apply    | 1个参数 | 有返回值 |
| Consume        | accept   | 1个参数 | 无返回值 |
| Supplier       | get      | 无参数  | 有返回值 |
| BiConsumer     | accept   | 2个参数 | 无返回值 |

- **join**和**get**的区别是join不需要抛出异常，而get需要。
- 至于java8的函数式编程，玩过scala的应该都很简单，注意**不要装逼把全部代码都写在一行**

### 电商比价需求案例

- 同一款产品，同时搜索出同款产品在各大电商的售价
- 同一款产品，同时搜索出本产品在某一个电商平台下，各个入驻门店售价是多少

>出来结果希望是同款产品在不同地方的价格清单列表，返回一个List

**两种实现思路**

- 单线程下依次获取各平台的商品的售价
- 异步执行，返回各平台的商品的售价

```java
public class CFNetMallDemo {

  public static void main(String[] args) {
    List<NetMall> list = Arrays.asList(
        new NetMall("jd"),
        new NetMall("pdd"),
        new NetMall("tmall")
    );

    System.out.println(getPriceByStep(list, "hadoop"));
    System.out.println(getPriceByAsync(list, "hadoop"));
    
  }

  // 迭代的方式
  public static List<String> getPriceByStep(List<NetMall> list, String spuName) {
    return list.stream()
        .map(item -> String
            .format(spuName + " %s and %.2f", item.getMallName(), item.getPrice(spuName)))
        .collect(Collectors.toList());
  }

  // 异步
  public static List<String> getPriceByAsync(List<NetMall> list, String spuName) {
    return list.stream()
        // 相当于有list.size()个异步任务执行，返回结果
        .map(item -> CompletableFuture.supplyAsync(() -> String
            .format(spuName + " %s and %.2f", item.getMallName(), item.getPrice(spuName))))
        .collect(Collectors.toList())
        .stream()
        .map(CompletableFuture::join)
        .collect(Collectors.toList());
  }
}

@Data
class NetMall {

  private String mallName;

  public NetMall(String mallName) {
    this.mallName = mallName;
  }

  // 随机返回一个商品的价格
  public double getPrice(String spuName) {
    try {
      // 检索需要1秒钟
      TimeUnit.SECONDS.sleep(1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ThreadLocalRandom.current().nextDouble() * 2 + spuName.charAt(0);
  }
}
```

## 如何理解 CompletionStage 接口

以站在分工的角度类比一下工作流。任务是有时序关系的，比如有串行关系、并行关系、汇聚关系等。这样说可能有点抽象，这里还举前面烧水泡茶的例子，其中洗水壶和烧开水就是串行关系，洗水壶、烧开水和洗茶壶、洗茶杯这两组任务之间就是并行关系，而烧开水、拿茶叶和泡茶就是汇聚关系。

![image-20210920132529683](https://gitee.com/joeyooa/data-images/raw/master/node/2021/image-20210920132529683.png)

### 描述串行关系

- CompletionStage 接口里面描述串行关系，主要是 thenApply、thenAccept、thenRun 和 thenCompose 这四个系列的接口。

- thenApply 系列函数里参数 fn 的类型是接口 Function，这个接口里与 CompletionStage 相关的方法是 R apply(T t)，这个方法既能接收参数也支持返回值，所以 thenApply 系列方法返回的是CompletionStage。

- 而 thenAccept 系列方法里参数 consumer 的类型是接口Consumer，这个接口里与 CompletionStage 相关的方法是 void accept(T t)，这个方法虽然支持参数，但却不支持回值，所以 thenAccept 系列方法返回的是CompletionStage。

- thenRun 系列方法里 action 的参数是 Runnable，所以 action 既不能接收参数也不支持返回值，所以 thenRun 系列方法返回的也是CompletionStage。

- 这些方法里面 Async 代表的是异步执行 fn、consumer 或者 action。其中，需要你注意的是 thenCompose 系列方法，这个系列的方法会新创建出一个子流程，最终结果和 thenApply 系列是相同的。

```java

CompletionStage<R> thenApply(fn);
CompletionStage<R> thenApplyAsync(fn);
CompletionStage<Void> thenAccept(consumer);
CompletionStage<Void> thenAcceptAsync(consumer);
CompletionStage<Void> thenRun(action);
CompletionStage<Void> thenRunAsync(action);
CompletionStage<R> thenCompose(fn);
CompletionStage<R> thenComposeAsync(fn);
```

通过下面的示例代码，你可以看一下  thenApply() 方法是如何使用的。首先通过 supplyAsync()  启动一个异步流程，之后是两个串行操作，整体看起来还是挺简单的。不过，虽然这是一个异步流程，但任务①②③却是串行执行的，②依赖①的执行结果，③依赖②的执行结果。

```java

CompletableFuture<String> f0 = 
  CompletableFuture.supplyAsync(
    () -> "Hello World")      //①
  .thenApply(s -> s + " QQ")  //②
  .thenApply(String::toUpperCase);//③

System.out.println(f0.join());
//输出结果
HELLO WORLD QQ
```

### 描述 AND 汇聚关系

CompletionStage 接口里面描述 AND 汇聚关系，主要是 thenCombine、thenAcceptBoth 和 runAfterBoth 系列的接口，这些接口的区别也是源自 fn、consumer、action 这三个核心参数不同

```java

CompletionStage<R> thenCombine(other, fn);
CompletionStage<R> thenCombineAsync(other, fn);
CompletionStage<Void> thenAcceptBoth(other, consumer);
CompletionStage<Void> thenAcceptBothAsync(other, consumer);
CompletionStage<Void> runAfterBoth(other, action);
CompletionStage<Void> runAfterBothAsync(other, action);
```

```java

//任务1：洗水壶->烧开水
CompletableFuture<Void> f1 = 
  CompletableFuture.runAsync(()->{
  System.out.println("T1:洗水壶...");
  sleep(1, TimeUnit.SECONDS);

  System.out.println("T1:烧开水...");
  sleep(15, TimeUnit.SECONDS);
});
//任务2：洗茶壶->洗茶杯->拿茶叶
CompletableFuture<String> f2 = 
  CompletableFuture.supplyAsync(()->{
  System.out.println("T2:洗茶壶...");
  sleep(1, TimeUnit.SECONDS);

  System.out.println("T2:洗茶杯...");
  sleep(2, TimeUnit.SECONDS);

  System.out.println("T2:拿茶叶...");
  sleep(1, TimeUnit.SECONDS);
  return "龙井";
});
//任务3：任务1和任务2完成后执行：泡茶
CompletableFuture<String> f3 = 
  f1.thenCombine(f2, (__, tf)->{
    System.out.println("T1:拿到茶叶:" + tf);
    System.out.println("T1:泡茶...");
    return "上茶:" + tf;
  });
//等待任务3执行结果
System.out.println(f3.join());

void sleep(int t, TimeUnit u) {
  try {
    u.sleep(t);
  }catch(InterruptedException e){}
}
// 一次执行结果：
T1:洗水壶...
T2:洗茶壶...
T1:烧开水...
T2:洗茶杯...
T2:拿茶叶...
T1:拿到茶叶:龙井
T1:泡茶...
上茶:龙井
```

### 描述 OR 汇聚关系

CompletionStage 接口里面描述 OR 汇聚关系，主要是 applyToEither、acceptEither 和 runAfterEither 系列的接口，这些接口的区别也是源自 fn、consumer、action 这三个核心参数不同。

```java

CompletionStage applyToEither(other, fn);
CompletionStage applyToEitherAsync(other, fn);
CompletionStage acceptEither(other, consumer);
CompletionStage acceptEitherAsync(other, consumer);
CompletionStage runAfterEither(other, action);
CompletionStage runAfterEitherAsync(other, action);
```

下面的示例代码展示了如何使用 applyToEither() 方法来描述一个 OR 汇聚关系。

```java

CompletableFuture<String> f1 = 
  CompletableFuture.supplyAsync(()->{
    int t = getRandom(5, 10);
    sleep(t, TimeUnit.SECONDS);
    return String.valueOf(t);
});

CompletableFuture<String> f2 = 
  CompletableFuture.supplyAsync(()->{
    int t = getRandom(5, 10);
    sleep(t, TimeUnit.SECONDS);
    return String.valueOf(t);
});

CompletableFuture<String> f3 = 
  f1.applyToEither(f2,s -> s);

System.out.println(f3.join());
```

### 异常处理

- 虽然上面我们提到的  fn、consumer、action 它们的核心方法都不允许抛出可检查异常，但是却无法限制它们抛出运行时异常，例如下面的代码，执行 7/0  就会出现除零错误这个运行时异常。非异步编程里面，我们可以使用 try{}catch{}来捕获并处理异常，那在异步编程里面，异常该如何处理呢？

```java

CompletableFuture<Integer> 
  f0 = CompletableFuture.
    .supplyAsync(()->(7/0))
    .thenApply(r->r*10);
System.out.println(f0.join());
```

- CompletionStage 接口给我们提供的方案非常简单，比 try{}catch{}还要简单，下面是相关的方法，使用这些方法进行异常处理和串行操作是一样的，都支持链式编程方式。

```java

CompletionStage exceptionally(fn);
CompletionStage<R> whenComplete(consumer);
CompletionStage<R> whenCompleteAsync(consumer);
CompletionStage<R> handle(fn);
CompletionStage<R> handleAsync(fn);
```

- exceptionally() 的使用非常类似于 try{}catch{}中的 catch{}，但是由于支持链式编程方式，所以相对更简单
- whenComplete() 和  handle() 系列方法就类似于 try{}finally{}中的 finally{}，无论是否发生异常都会执行 whenComplete() 中的回调函数 consumer 和 handle() 中的回调函数 fn。whenComplete() 和 handle() 的区别在于  whenComplete() 不支持返回结果，而 handle() 是支持返回结果的。

