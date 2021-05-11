https://www.nowcoder.com/discuss/586326?channel=-1&source_id=profile_follow_post_nctrack

算法；CS-Stack基础；项目；模板部分

**算法**：`001 合集 数据结构与算法之美 --王争.pdf                   leetcode刷题`

**CS-Stack最重要的基本功**：

-   Java的基础、容器、JVM等        [cyc2018](http://www.cyc2018.xyz/)   [JavaGuide](https://www.yuque.com/docs/share/f37fc804-bfe6-4b0d-b373-9c462188fec7)

    类加载 `E:\001 java\JavaGuide\docs\java\jvm`

-   并发JUC

    `002b Java多线程和并发知识点详细总结 153页.pdf             cyc2018`

    

-   计算机网络  `cyc2018`

-   操作系统与Linux    `cyc2018`

-   数据库    `MySQL课堂笔记(动力节点)` 

    Redis JavaGuide

    分库分表 `E:\001 java\JavaGuide\docs\system-design\读写分离&分库分表.md`

    

-   算法、数据结构等。

-   设计模式   `菜鸟教程   004 [荐]深入浅出设计模式.pdf  cyc2018`

-   分布式 Dubbo(RPC的一种)、rabbitMQ和kafka\rocketMQ消息队列、zookeeper

-   框架

    mybatis 学习来自 `E:\001 java\SSM\MyBatis`

    mybatis 面经来自 `E:\001 java\JavaGuide\docs\system-design\framework\mybatis`

    spring `E:\001 java\JavaGuide\docs\system-design\framework\spring`    Spring常见问题总结.md、Spring-Design-Patterns.md

**项目**

这部分一方面是学校里的课设、项目，另外的就是在9月份临时看的JavaGuide里写的RPC框架了。借用声哥的总结： 

>    项目经验，可以放上一些自己的课设或者毕设之类的，尽量与应聘的岗位有关最好。尽量不要写一些被大家写滥的[项目]()，如秒杀。我做过一个 RPC 项目，[链接在这](http://github.com/CN-GuoZiyang/My-RPC-Framework)，还写了一份配套的[教程](http://blog.csdn.net/qq_40856284/category_10138756.html)，希望明年不是人均RPC就好hhh。 
>
>    by [何人听我楚狂声](https://www.nowcoder.com/profile/6796629) 

   整理了10个可以写到简历上的C++项目：https://www.nowcoder.com/discuss/578948 by [Ckyle](https://www.nowcoder.com/profile/2119497)  

   秋招总结分享：C++后端项目的进阶之路：   https://www.nowcoder.com/discuss/596700 by   [fibonaccii](https://www.nowcoder.com/profile/803810629)





**模板部分**

**1、简历**：看经验 [程序员的简历之道](https://github.com/Jiezhi/JavaGuide/blob/master/EssentialContentForInterview/PreparingForInterview/程序员的简历之道.md)，用Markdown框架生成pdf [冷熊简历](http://cv.ftqq.com/?fr=github#) 

 **2、自我介绍**：学校+所学知识+项目+实习+看的书+博客+github等等。由于大部分是线上面试，可以用Snipaste\qq截图这种软件把自我介绍“钉”在屏幕上对着念（其他办法：打印出来放在屏幕下方，用手机常亮显示放在屏幕下方对着念）不过念多了之后就可以背了hh。根据自己的所学、面试反馈，不断地迭代。 

 **3、反问环节：**反问是一个很好的了解部门、工作内容的机会，面试是双向的选择，这些了解的点有助于之后选择offer，千万不要浪费。我到最后一场面试才思考到这点，因为前期没offer太乖了。。 

>   1.   XX的培养机制？ 
>   2.   对应届生的期望是什么？ 
>   3.   XX团队规模有多大？组内做的工作是什么？ 
>   4.   到了您这个岗位，在XX部门里主要是负责什么事？ 
>   5.   自己不懂的技术问题、推荐书 等等





## 1.B树与B+树区别

[B树、B+树详解 - Assassinの - 博客园 (cnblogs.com)](https://www.cnblogs.com/lianzhilei/p/11250589.html)

(1)**B树的定义**

B树是一种平衡的多分树，通常我们说m阶的B树，它必须满足如下条件： 

-   每个节点最多只有m个子节点。

-   每个非叶子节点（除了根）具有至少⌈ m/2⌉子节点。

-   如果根不是叶节点，则根至少有两个子节点。

-   具有*k*个子节点的非叶节点包含*k* -1个键。

-   所有叶子都出现在同一水平，没有任何信息（高度一致）。

    

    

(2)**B+树的特征：**

-   有m个子树的中间节点包含有m个元素（B树中是k-1个元素），每个元素不保存数据，只用来索引；
-   所有的叶子结点中包含了全部关键字的信息，及指向含有这些关键字记录的指针，且叶子结点本身依关键字的大小自小而大的顺序链接。 (而B 树的叶子节点并没有包括全部需要查找的信息)；
-   所有的非终端结点可以看成是索引部分，结点中仅含有其子树根结点中最大（或最小）关键字。 (而B 树的非终节点也包含需要查找的有效信息)；

 B+树是应文件系统所需而产生的B树的变形树，那么可能一定会想到，既然有了B树，又出一个B+树，那B+树必然是有很多优点的

**为什么说B+树比B树更适合数据库索引？**

1）**B+树的磁盘读写代价更低**

　　B+树的内部结点并没有指向关键字具体信息的指针。因此其内部结点相对B 树更小。如果把所有同一内部结点的关键字存放在同一盘块中，那么盘块所能容纳的关键字数量也越多。一次性读入内存中的需要查找的关键字也就越多。相对来说IO读写次数也就降低了；

2）**B+树查询效率更加稳定**

　　由于非终结点并不是最终指向文件内容的结点，而只是叶子结点中关键字的索引。所以任何关键字的查找必须走一条从根结点到叶子结点的路。所有关键字查询的路径长度相同，导致每一个数据的查询效率相当；

3）**B+树便于范围查询**（最重要的原因，范围查找是数据库的常态）

　　B树在提高了IO性能的同时并没有解决元素遍历的我效率低下的问题，正是为了解决这个问题，B+树应用而生。B+树只需要去遍历叶子节点就可以实现整棵树的遍历。而且在数据库中基于范围的查询是非常频繁的，而B树不支持这样的操作或者说效率太低.B树的范围查找用的是中序遍历，而B+树用的是在链表上遍历；

## 2.红黑树(红黑树是一种平衡二叉搜索(查找)树)

所有平衡二叉搜索树在维护平衡时(插入和删除数据时)都要左旋右旋。平衡二叉搜索树不支持范围查找或者查找效率很低。

完全二叉树使用数组存储,其他的二叉树使用链表存储

## 3.跳表

Redis使用跳表实现有序集合(SortedSet)这个数据类型,这个数据类型的名称为ZSet

[深入理解跳表在Redis中的应用 - 知乎 (zhihu.com)

## 4.数据库

**4.1数据库引擎**

**InnoDB的特点**

​	优点：支持事务、行级锁、外键等。这种存储引擎数据的安全得到保障。

​			数据存储在tablespace这样的表空间中（逻辑概念），无法被压缩，无法转换成只读。
​			这种InnoDB存储引擎在MySQL数据库崩溃之后提供自动恢复机制。
​			InnoDB支持级联删除和级联更新。

**MyISAM的特点**

​	优点：可被压缩，节省存储空间。**并且可以转换为只读表，提高检索效率。**
​	缺点：不支持事务。

事务（Transaction）TCL(Transaction Control Language)语句

**什么是事务**？

​	一个事务是一个完整的业务逻辑单元，不可再分。

DML语句同时成功或者同时失败，那么就需要使用数据库的“事务机制”。

**和事务相关的语句只有**：DML语句。（insert delete update）
	为什么？因为它们这三个语句都是和数据库表当中的“数据”相关的。
	事务的存在是为了保证数据的完整性，安全性。

**假设所有的业务都能使用**1条DML语句搞定，还需要事务机制吗？
	不需要事务。
	但实际情况不是这样的，通常一个“事儿（事务【业务】）”需要多条DML语句共同联合完成。
	事务就是多条DML语句捆绑在一起执行。提交事务(commit)会把事务历史清空，并把执行后的结果同步到硬盘中；回滚事务(rollback)也会清空事务历史，但不会将执行结果同步到硬盘中。rollback类似于goto语句或者游戏建立存档。
	

	事务结构：
		开始事务机制(开始)
		    执行多条DML语句
		提交事务或回滚事务(结束)

## 

**4.2mysql数据库索引的匹配原则**

InnoDB引擎用的索引底层是B+树

https://blog.csdn.net/sinat_41917109/article/details/88944290

E:\001 java\JavaGuide\docs\database

最左匹配原则: 最左优先，以最左边的为起点任何连续的索引都能匹配上。同时遇到范围查询(>、<、between、like、模糊查询等)时,非最左的索引值的匹配就会停止匹配



索引类型:聚簇索引和哈希索引

聚簇索引：[聚簇索引与非聚簇索引（也叫二级索引）--最清楚的一篇讲解 - 云+社区 - 腾讯云 (tencent.com)](https://cloud.tencent.com/developer/article/1541265)

https://zhuanlan.zhihu.com/p/86189418

**4.3何为 ACID 特性呢？**

1. **原子性**（`Atomicity`） ： 事务是最小的执行单位，不允许分割。事务的原子性确保动作要么全部完成，要么完全不起作用；
2. **一致性**（`Consistency`）： 执行事务前后，数据保持一致，例如转账业务中，无论事务是否成功，转账者和收款人的总额应该是不变的；
3. **隔离性**（`Isolation`）： 并发访问数据库时，一个用户的事务不被其他事务所干扰，各并发事务之间数据库是s独立的；
4. **持久性**（`Durabilily`）： 一个事务被提交之后。它对数据库中数据的改变是持久的，即使数据库发生故障也不应该对其有任何影响。

**4.4数据事务的实现原理呢？事务的两阶段提交**

[两阶段提交 two-phase commit 分布式事务 JTA | 技术世界 | 分布式事务,两阶段提交,two-phase commit,技术世界,郭俊 Jason,大数据架构 (jasongj.com)](http://www.jasongj.com/big_data/two_phase_commit/#:~:text=二阶段提交的算法,送相同的指令）。)

我们这里以 MySQL 的 InnoDB 引擎为例来简单说一下。

MySQL InnoDB 引擎使用 **redo log(重做日志)** 保证事务的**持久性**，使用 **undo log(回滚日志)** 来保证事务的**原子性**。

MySQL InnoDB 引擎通过 **锁机制**、**MVCC** 等手段来保证事务的隔离性（ 默认支持的隔离级别是 **`REPEATABLE-READ`** ）。

保证了事务的持久性、原子性、隔离性之后，一致性才能得到保障。

		oracle数据库默认的隔离级别是：读已提交。
		mysql数据库默认的隔离级别是：可重复读。事务（Transaction）TCL(Transaction Control Language)语句

4.5**主从复制与读写分离**

E:\001 java\JavaGuide\docs\system-design\读写分离&分库分表

**主从复制**:数据保存在主服务器和若干从服务器中,要保证它们的数据一致性，就要进行主从复制

主从复制用到是MySql的bin log(binary log，二进制日志文件) 

**主从复制的步骤**:

1. 主库将数据库中数据的变化写入到 binlog
2. 从库连接主库
3. 从库会创建一个 **I/O 线程**向主库请求更新的 binlog
4. 主库会创建一个 **binlog dump 线程**来发送 binlog ，从库中的 I/O 线程负责接收
5. 从库的 I/O 线程将接收的 binlog 写入到 relay log 中。
6. 从库的 **SQL 线程**读取 relay log 同步数据本地（也就是再执行一遍 SQL ）。

主要涉及三个线程：binlog 线程、I/O 线程和 SQL 线程。

-   **binlog 线程**  ：负责将主服务器上的数据更改写入二进制日志（Binary log）中。
-   **I/O 线程**  ：负责从主服务器上读取二进制日志，并写入从服务器的中继日志（Relay log）。
-   **SQL 线程**  ：负责读取中继日志，解析出主服务器已经执行的数据更改并在从服务器中重放（Replay）。

**读写分离**:读写分离主要是为了将对数据库的读写操作分散到不同的数据库节点上(其实就是主从复制的主从服务器上)

MySQL和Redis都是通过主从复制来实现读写分离

读写分离解决的是数据库读并发问题，而数据库的存储问题需要使用分库分表(一张数据表数据量特别大，那么就要分库分表)

**分库分表**

ShardingSphere 可用于分库分表、读写分离、分布式事务、数据库治理

## 5.反射

[深入解析Java反射（1） - 基础 | 「浮生若梦」 - sczyh30's blog](https://www.sczyh30.com/posts/Java/java-reflection-1/)

程序中一般的对象的类型都是在编译期就确定下来的，而 **Java 反射机制可以动态地创建对象并调用其属性**，这样的对象的类型在编译期是未知的。所以我们可以通过反射机制直接创建对象，即使这个对象的类型在编译期是未知的。

反射的核心是 JVM 在运行时才动态加载类或调用方法/访问属性，它不需要事先（写代码的时候或编译期）知道运行对象是谁。

**反射最重要的用途就是开发各种通用框架。**很多框架（比如 Spring）都是配置化的（比如通过 XML 文件配置 Bean），为了保证框架的通用性，它们可能需要根据配置文件加载不同的对象或类，调用不同的方法，这个时候就必须用到反射，运行时动态加载需要加载的对象。

## 6.RESTful

## 7.Java容器

https://www.nowcoder.com/discuss/631980?channel=-1&source_id=profile_follow_post_nctrack

## 8.关键字local、volatile、final、static、synchronized

volatile:保证可见性、有序性，但不保证原子性，需要synchronized修饰来保证原子性

[Java - volatile - 简书](https://www.jianshu.com/p/3893fb35240f)

## 9.锁的类型

JAVA读写锁

锁接口和类:002b Java多线程和并发知识点详细总结.pdf 章节14

什么是死锁，什么情况下出现死锁

https://www.jianshu.com/p/44125bb12ebf  https://cloud.tencent.com/developer/article/1628870

**死锁的定义**

死锁是指两个或两个以上的进程在执行过程中，由于竞争资源或者由于彼此通信而造成的一种阻塞的现象，若无外力作用，它们都将无法推进下去。此时称系统处于死锁状态或系统产生了死锁，这些永远在互相等待的进程称为死锁进程。

**出现死锁的四个原因**(必要条件)

互斥条件：一个资源每次只能被一个线程使用。															 独占锁的特点之一

请求与保持条件：一个线程因请求资源而阻塞时，对已获得的资源保持不放。 		  独占锁的特点之一，尝试获取锁时并不会释放已经持有的锁

不剥夺条件：线程已获得的资源，在未使用完之前，不能强行剥夺。						   独占锁的特点之一

循环等待条件：若干线程之间形成一种头尾相接的循环等待资源关系。					   唯一需要记住的，这是解决死锁的关键

**解决死锁的方法**

在并发程序中，避免了出现复数个线程互相持有对方线程所需要的独占锁的的情况，就可以避免死锁。

**mysql的锁**

[【数据库】数据库的锁机制及原理_Zoom-CSDN博客_数据库的锁](https://blog.csdn.net/C_J33/article/details/79487941)



## 10.lambda表达式

形式:         ()->{}

可以看成一个匿名的方法,箭头左边圆括号内代表方法的输入参数,箭头右边代表该方法的代码块,代码块可以使用其他对象的方法

## 11.socket套接字  本地的进程间通信

https://blog.csdn.net/pashanhu6402/article/details/96428887

本地的进程间通信（IPC）有很多种方式，但可以总结为下面4类：

-   消息传递（管道、FIFO、消息队列）
-   同步（互斥量、条件变量、读写锁、文件和写记录锁、信号量）
-   共享内存（匿名的和具名的）
-   远程过程调用（Solaris门和Sun RPC）

管道:

## 12.类、类的引用、对象

(1)对象==实例

Person person = new Person("张三");

类名 引用名 = new 构造器名(即类名)(args);  //   new 类名(args);  是用带参数args的构造器创建对象,参数可以是0个和若干个,具体要看类提供了什么样的构造器，以及要构造什么样的对象

new是用来在堆上创建对象的

(2)父类引用指向子类对象 称为 向上转型(找到该对象的更高级别、更广泛的分类)

假设Animal是Cat的父类,则向上转型为

```java
Animal animal = new Cat();
```



继承实现了 **IS-A** 关系，例如 Cat 和 Animal 就是一种 IS-A 关系，因此 Cat 可以继承自 Animal，从而获得 Animal 非 private 的属性和方法。

继承应该遵循里氏替换原则，子类对象必须能够替换掉所有父类对象。



抽象类不可被实例化，使用抽象类需要继承，继承则会调用父类的构成器，所以抽象类是有构造器的。如果一个类中包含抽象方法，那么这个类必须声明为抽象类。抽象类和普通类最大的区别是，**抽象类不能被实例化，只能被继承**。

接口的方法是没有方法体的，但是JDK1.8以后支持了默认的方法实现，也就是有默认的方法具有方法体

接口中的声明为默认实现的方法例子如下:

```java
default void func2(){
    System.out.println("func2");
}
```
## 13.RPC(Remote Procedure Call) 远程程序调用

E:\001 java\JavaGuide\docs\system-design\distributed-system\rpc

**RMI(Remote Method Invocation,远程方法调用)（JDK自带）：** JDK自带的RPC，有很多局限性，不推荐使用。

**Dubbo:** Dubbo是 阿里巴巴公司开源的一个高性能优秀的服务框架，使得应用可通过高性能的 RPC 实现服务的输出和输入功能，可以和 Spring框架无缝集成。目前 Dubbo 已经成为 Spring Cloud Alibaba 中的官方组件。

### 何为 RPC?

**RPC（Remote Procedure Call）** 即远程过程调用，通过名字我们就能看出 RPC 关注的是远程调用而非本地调用。

**为什么要 RPC  ？** 因为，两个不同的服务器上的服务提供的方法不在一个内存空间，所以，需要通过网络编程才能传递方法调用所需要的参数。并且，方法调用的结果也需要通过网络编程来接收。但是，如果我们自己手动网络编程来实现这个调用过程的话工作量是非常大的，因为，我们需要考虑底层传输方式（TCP还是UDP）、序列化方式等等方面。

**RPC 能帮助我们做什么呢？ ** 简单来说，通过 RPC 可以帮助我们调用远程计算机上某个服务的方法，这个过程就像调用本地方法一样简单。并且！我们不需要了解底层网络编程的具体细节。


举个例子：两个不同的服务 A、B 部署在两台不同的机器上，服务 A 如果想要调用服务 B 中的某个方法的话就可以通过 RPC 来做。

一言蔽之：**RPC 的出现就是为了让你调用远程方法像调用本地方法一样简单。**

### RPC 的原理是什么?

为了能够帮助小伙伴们理解 RPC 原理，我们可以将整个 RPC的 核心功能看作是下面👇 6 个部分实现的：


1. **客户端（服务消费端）** ：调用远程方法的一端。
1. **客户端 Stub（桩）** ： 这其实就是一代理类。代理类主要做的事情很简单，就是把你调用方法、类、方法参数等信息传递到服务端。
1. **网络传输** ： 网络传输就是你要把你调用的方法的信息比如说参数啊这些东西传输到服务端，然后服务端执行完之后再把返回结果通过网络传输给你传输回来。网络传输的实现方式有很多种比如最近基本的 Socket或者性能以及封装更加优秀的 Netty（推荐）。
1. **服务端 Stub（桩）** ：这个桩就不是代理类了。我觉得理解为桩实际不太好，大家注意一下就好。这里的服务端 Stub 实际指的就是接收到客户端执行方法的请求后，去指定对应的方法然后返回结果给客户端的类。
1. **服务端（服务提供端）** ：提供远程方法的一端。

具体原理图如下，后面我会串起来将整个RPC的过程给大家说一下。


![RPC原理图](http://my-blog-to-use.oss-cn-beijing.aliyuncs.com/18-12-6/37345851.jpg)

1. 服务消费端（client）以本地调用的方式调用远程服务；
1. 客户端 Stub（client stub） 接收到调用后负责将方法、参数等组装成能够进行网络传输的消息体（序列化）：`RpcRequest`；
1. 客户端 Stub（client stub） 找到远程服务的地址，并将消息发送到服务提供端；
1. 服务端 Stub（桩）收到消息将消息反序列化为Java对象: `RpcRequest`；
1. 服务端 Stub（桩）根据`RpcRequest`中的类、方法、方法参数等信息调用本地的方法；
1. 服务端 Stub（桩）得到方法执行结果并将组装成能够进行网络传输的消息体：`RpcResponse`（序列化）发送至消费方；
1. 客户端 Stub（client stub）接收到消息并将消息反序列化为Java对象:`RpcResponse` ，这样也就得到了最终结果。over!



## 15.Top K问题 堆排序(完全二叉树)

维护大小为K的堆,堆是完全二叉树的结构，所以是用数组来进行存储的

堆:该堆中，所有父节点的值大于子节点的值(大顶堆);该堆中，所有父节点的值小于字节点的值(小顶堆)

插入元素: 在堆的末尾插入元素，并依据堆的规定进行比较交换，直到满足堆的定义

删除元素:将堆末尾的元素替换掉需要删除的元素的位置，并且把需要删除的元素丢弃，接着就按照堆的定义进行节点的交换，直到满足堆的定义为止

## 16.多态

多态分为编译时多态和运行时多态：

- 编译时多态主要指方法的重载
- 运行时多态指程序中定义的对象引用所指向的具体类型在运行期间才确定

运行时多态有三个条件：

- 继承
- 覆盖（重写）
- 向上转型

## 17.匿名内部类和lambda表达式

方法中使用匿名内部类和lambda表达式的输入变量必须在方法外,即输入变量是外部类的成员变量,

因为匿名内部类和lambda表达式本身就是一个类,实际声明时是外部类的成员,而非方法的成员

如下的 两个线程交替打印的例子,使用Runnable的匿名内部类,匿名内部类的变量count需要写成类AlternatePrintTest的成员变量,而不能在main方法里写,因为main方法写的值对于匿名内部类(一种成员类,是AlternatePrintTest类的成员,而非main方法的变量)是不可见的

```java
public class AlternatePrintTest {//两个线程交替打印

    private static int count=0;//匿名内部类需要使用的成员变量(即 不是 常量)
    private static int time=100;
    
    public static void main(String[] args) throws InterruptedException {//main方法

        Object lock=new Object();
 		//使用匿名内部类创建一个任务
        Runnable runnable=new Runnable() {
            @Override
            public void run() {

                    synchronized (lock){
                        while (count<time){

                            //获得锁就进行打印
                            System.out.println(Thread.currentThread().getName()+": "+count++);
                            lock.notifyAll();//通知其他任务
                            try {
                                if(count<time){//如果线程还没有完成,就让出当前锁,并休眠.防止死锁:也就是count>time时,不再释放锁,而是直接让销毁线程

                                    lock.wait();
                                }
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }
            }
        };
        int n=10;
        new Thread(runnable,"偶数").start();
        new Thread(runnable,"奇数").start();
    }

    


}
```

## 18.负载均衡

同一个服务部署在不同的机器时该调用那一台机器上的服务。





## 19.spring

Spring IoC控制反转:

[Spring IoC有什么好处呢？ - 知乎 (zhihu.com)

[**所谓依赖注入，就是把底层类作为参数传入上层类，实现上层类对下层类的“控制**”

依赖注入的方式:构造方法、**Setter传递**和**接口传递**。核心思路都是一样的，都是为了实现**控制反转**

Spring AOP 面向切面编程



synchronized修饰方法和静态方法

-   修饰一个方法：被修饰的方法称为同步方法，其作用的范围是整个方法， 作用的对象是调用这个方法的对象 ；
-   修饰一个静态的方法：其作用的范围是整个方法， 作用的对象是这个类的所有对象

类锁：所有对象共用一个锁

对象锁：一个对象一把锁，多个对象多把锁。

[synchronized 修饰静态方法、普通方法与代码块的区别 - 简书 (jianshu.com)](https://www.jianshu.com/p/3cfdf32bd37e)



线程池中 阻塞队列大小

## 20.redis

在redis sorted sets里面当items内容大于64的时候同时使用了hash和skiplist两种设计实现。这也会为了排序和查找性能做的优化。所以如上可知： 

添加和删除都需要修改skiplist，所以复杂度为O(log(n))。 

但是如果仅仅是查找元素的话可以直接使用hash，其复杂度为O(1) 

其他的range操作复杂度一般为O(log(n))

当然如果是小于64的时候，因为是采用了ziplist的设计，其时间复杂度为O(n)

**Redis持久化RDB和AOF**

[Redis 持久化](https://www.redis.com.cn/redis-persistence.html#:~:text=redis 提供了两种持久化的方式，分别是 RDB （Redis,DataBase）和 AOF （Append Only File）。)

## 21.JavaIO

BIO、NIO、AIO

Netty(基于NIO)

[Netty入门教程——认识Netty - 简书 (jianshu.com)](https://www.jianshu.com/p/b9f3f6a16911)

## 22.Stream流的几种方法

map  filter count sorted limit skip

[Java 8都出那么久了，Stream API了解下？ - Document (macrozheng.com)](http://www.macrozheng.com/#/technology/java_stream)

## 23.迭代接口Iterable和迭代器Iterator

实现了迭代接口Iterable才能使用迭代器Iterator.

迭代器相比for循环更适合对无序的集合(如Set)、链式结构进行遍历

[Java迭代器(iterator详解以及和for循环的区别)_Jae_Wang的博客-CSDN博客_java 迭代器](https://blog.csdn.net/Jae_Wang/article/details/80526216)

## 24.动态代理与静态代理的区别及优缺点

spring AOP用到了动态代理

[面试题-静态代理和动态代理的区别，什么场景使用？_赵大土的博客-CSDN博客_静态代理和动态代理的区别,什么场景使用?](https://blog.csdn.net/weixin_43647393/article/details/103281623)