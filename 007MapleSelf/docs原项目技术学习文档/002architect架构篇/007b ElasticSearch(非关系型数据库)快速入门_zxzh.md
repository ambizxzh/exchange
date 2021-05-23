## Elasticsearch快速入门网址

1.[ElasticSearch原理 - 神一样的存在 - 博客园 (cnblogs.com)](https://www.cnblogs.com/dreamroute/p/8484457.html)

Elasticsearch是面向文档型数据库,是一种非关系型数据库，**一条数据在这里就是一个文档，用JSON作为文档序列化的格式**

用Mysql这样的数据库存储就会容易想到建立一张User表，有balabala的字段等，在Elasticsearch里这就是一个*文档*，当然这个文档会属于一个User的*类型*，各种各样的类型存在于一个*索引*当中。这里有一份简易的将Elasticsearch和关系型数据库术语对照表:

```
关系数据库(如MySQL)            ⇒ 数据库(database)  ⇒ 表(table)         ⇒ 行(row)              ⇒ 列(Columns)

非关系型数据库(Elasticsearch)	⇒ 索引(Index)   	 ⇒ 类型(type)         ⇒ 文档(Docments)       ⇒ 字段(Fields)  
```

## 

2.[Elasticsearch快速入门，掌握这些刚刚好！ (qq.com)](https://mp.weixin.qq.com/s/cohWZy_eUOUqbmUxhXzzNA)

3.[Elasticsearch【快速入门】 - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/54384152)

4.分布式消息队列kafka的结构图和Elasticsearch相似

E:\001 java\JavaGuide\docs\system-design\distributed-system\message-queue\Kafka常见面试题总结.md:什么是Producer、Consumer、Broker、Topic、Partition

![img](https://upload-images.jianshu.io/upload_images/6563032-cd20bbd6d4c5a400.png?imageMogr2/auto-orient/strip|imageView2/2/w/955/format/webp)

**相关概念**

-   Near Realtime（近实时）：Elasticsearch是一个近乎实时的搜索平台，这意味着从索引文档到可搜索文档之间只有一个轻微的延迟(通常是一秒钟)。

-   Cluster（集群）：集群是一个或多个节点的集合，它们一起保存整个数据，并提供跨所有节点的联合索引和搜索功能。每个集群都有自己的唯一集群名称，节点通过名称加入集群。

-   Node（节点）：**节点**是指属于集群的单个Elasticsearch实例，存储数据并**参与**集群的**索引**和搜索功能。可以将节点配置为按集群名称加入特定集群，默认情况下，每个节点都设置为加入一个名为`elasticsearch`的集群。

-   Index（索引）：索引是一些具有相似特征的文档集合，类似于MySql中数据库的概念。

-   Type（类型）：类型是索引的逻辑类别分区，通常，为具有一组公共字段的文档类型，类似MySql中表的概念。注意：在Elasticsearch 6.0.0及更高的版本中，**一个索引(数据库)只能包含一个类型(表格)**。

-   Document（文档）：**文档是可被索引的基本信息单位，以JSON形式表示**，类似于MySql中行记录的概念。

-   若把ElasticSearch看成面向文档型的数据库,以下就可以根据分库分表和主从复制的概念进行理解

    (1)Shards（分片）：当索引存储大量数据时，可能会超出单个节点的硬件限制，为了解决这个问题，Elasticsearch提供了将索引细分为分片的概念。分片机制赋予了索引水平扩容的能力、并允许跨分片分发和并行化操作，从而提高性能和吞吐量。

    (2)Replicas（副本）：在可能出现故障的网络环境中，需要有一个故障切换机制，Elasticsearch提供了将索引的分片复制为一个或多个副本的功能，副本在某些节点失效的情况下提供高可用性。

## 