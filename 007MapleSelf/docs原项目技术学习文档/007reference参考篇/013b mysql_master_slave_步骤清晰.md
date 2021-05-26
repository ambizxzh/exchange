# MySQL 的主从复制实践([MySQL 的主从复制实践-阿里云开发者社区 (aliyun.com)](https://developer.aliyun.com/article/744929))



**简介：** MySQL 的主从复制又叫 Replication、AB 复制。至少需要两个 MySQL 服务（可以是同一台机器，也可以是不同机器之间进行）。比如A服务器做主服务器，B服务器做从服务器，在A服务器上进行数据的更新，通过 binlog 日志记录同步到B服务器上，并重新执行同步过来的 binlog 数据，从而达到两台服务器数据一致。

## 简介

>   MySQL 的主从复制又叫 Replication、AB 复制。至少需要两个 MySQL 服务（可以是同一台机器，也可以是不同机器之间进行）。
>
>   比如A服务器做主服务器，B服务器做从服务器，在A服务器上进行数据的更新，通过 binlog 日志记录同步到B服务器上，并重新执行同步过来的 binlog 数据，从而达到两台服务器数据一致。
>
>   MySQL 数据库的主从复制方案，与使用 scp/rsync 等命令进行的文件级别复制类似，都是数据的远程传输。
>
>   只不过 MySQL 的主从复制是其自带的功能，无需借助第三方工具，而且MySQL的主从复制并不是数据库磁盘上的文件直接拷贝，而是通过逻辑的 binlog 日志复制到要同步的服务器本地，然后由本地的线程读取日志里面的 SQL 语句，重新应用到 MySQL 数据库中。

## 作用

```
1. 可以实时灾备，用于故障切换；
2. 读写分离，提供查询服务，实现负载均衡；
3. 数据热备，避免影响业务。
```

## 原理

![img](..\images\nz7ANMXkzc.jpg!large)

>   1、主服务器MySQL服务将所有的写操作记录在 binlog 日志中，并生成 log dump 线程，将 binlog 日志传给从服务器MySQL服务的 I/O 线程。
>
>   2、从服务器MySQL服务生成两个线程，一个是 I/O 线程，另一个是 SQL 线程。
>
>   3、从库 I/O 线程去请求主库的 binlog 日志，并将 binlog 日志中的文件写入 relaylog（中继日志）中。
>
>   4、从库的 SQL 线程会读取 relaylog 中的内容，并解析成具体的操作，来实现主从的操作一致，达到最终两个数据库数据一致的目的。

```
注意点：
- 主从复制是异步的逻辑的 SQL 语句级的复制；
- 复制时，主库有一个 I/O 线程，从库有两个线程，及 I/O 和 SQL 线程；
- 实现主从复制的必要条件是主库要开启记录 binlog 的功能；
- 作为复制的所有 MySQL 节点的 server-id 都不能相同；
- binlog 文件只记录对数据内容有更改的 SQL 语句，不记录任何查询语句。
```

## 形式

#### 一主一从

![img](..\images\FscXWNevul.png!large)

#### 主主复制

![img](..\images\PobqrmQEHF.png!large)

#### 一主多从

![img](..\images\7fkngwkxOl.png!large)

#### 多主一从（5.7后开始支持）

![img](..\images\4NhuLNVUzT.png!large)

#### 联级复制

![img](..\images\hytnYircDS.png!large)

## 实践

#### 需求

>   实现一主一从复制模式，同一台主机两个MySQL实例

#### 环境

```bash
Mac：10.15.1
Docker：2.0.0.3//采取docker安装MySQL主要是管理维护方便、独立IP、启动秒级
MySQL-master：5.7.29//主服务器
MySQL-master IP：172.17.0.3
MySQL-slave：5.7.29//从服务器
MySQL-slave IP：172.17.0.4
```

#### 步骤

##### 第一步：准备好两台MySQL服务器

>   mysql-master（主服务器）：

```bash
1. 创建目录结构：
master/conf、master/data、master/logs
2. 启动运行实例容器：
docker run --name mysql-master
> -p 3310:3306
> -v ~/docker/master/conf:/etc/mysql/conf.d
> -v ~/docker/master/data:/var/lib/mysql
> -v ~/docker/master/logs:/var/log/mysql
> -e MYSQL_ROOT_PASSWORD=123456
> -d mysql:5.7
3. 进入容器
docker exec -it mysql-master bash
4. 登录MySQL
mysql -uroot -p
```

>   mysql-slave（从服务器）：

```bash
1. 创建目录结构：
slave/conf、slave/data、slave/logs
2. 启动运行实例容器：
docker run --name mysql-slave
> -p 3310:3306
> -v ~/docker/slave/conf:/etc/mysql/conf.d
> -v ~/docker/slave/data:/var/lib/mysql
> -v ~/docker/slave/logs:/var/log/mysql
> -e MYSQL_ROOT_PASSWORD=123456
> -d mysql:5.7
3. 进入容器
docker exec -it mysql-slave bash
4. 登录MySQL
mysql -uroot -p
```

##### 第二步：配置文件（my.cnf）修改

```bash
主服务器：

[mysqld]
port = 3306
server-id = 1
#要同步的数据库
binlog-do-db = school
#要生成二进制日志文件 主服务器一定要开启
log-bin = mysql-bin

重启MySQL: docker restart mysql-master
从服务器：

[mysqld]
port = 3306
server-id = 2
#要同步的数据库
binlog-do-db = school
#要生成二进制日志文件（从服务器可选）
log-bin = mysql-bin

重启MySQL: docker restart mysql-slave
```

##### 第三步：创建主服务器复制用户及相关权限

```bash
create user 'slave'@'%' identified by '123456';//创建用户
grant replication slave,replication client on *.* to 'slave'@'%';//设置用户权限
flush privileges;//刷新权限
show grants for 'slave'@'%';//查看用户权限
```

##### 第四步：数据备份同步

```bash
1. 登录master，执行锁表操作
mysql -uroot -p
FLUSH TABLES WITH READ LOCK;
2. 将master中需要同步的db的数据dump出来
mysqldump -uroot -p school > school.dump
3. 将数据导入slave
mysql -uroot -h172.17.0.4 -p school < school.dump
4. 解锁master
UNLOCK TABLES;
```

##### 第五步：主服务器复制状态

```bash
1. 创建新数据表及增加数据
create table user( id int(10) auto_increment, name varchar(30), primary key (id) )charset=utf8mb4;
insert into user(name) values(222);
2. 主服务器 binlog 记录状态
mysql> show master status;
+------------------+----------+--------------+------------------+-------------------+
| File | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
+------------------+----------+--------------+------------------+-------------------+
| mysql-bin.000001 | 769 | school | | |
+------------------+----------+--------------+------------------+-------------------+
```

![img](..\images\L8aJRlzFJu.png!large)

##### 第六步：从服务器开始复制

```bash
1. 连接主服务器及设置复制的起始节点
mysql> change master to master_host='172.17.0.3',
-> master_port=3306,
-> master_user='slave',
-> master_password='123456',
-> master_log_file='mysql-bin.000001',
-> master_log_pos=769;
2. 开始复制
start slave;
3. 查看复制状态
mysql> show slave status \G
*************************** 1. row ***************************
Slave_IO_State: Waiting for master to send event
Master_Host: 172.17.0.3
Master_User: slave
Master_Port: 3306
Connect_Retry: 60
Master_Log_File: mysql-bin.000001
Read_Master_Log_Pos: 961
Relay_Log_File: 87dc5224655d-relay-bin.000003
Relay_Log_Pos: 320
Relay_Master_Log_File: mysql-bin.000001
Slave_IO_Running: Yes //表示I/O线程读取成功
Slave_SQL_Running: Yes //表示SQL线程执行成功
Replicate_Do_DB:
Replicate_Ignore_DB:
Replicate_Do_Table:
Replicate_Ignore_Table:
Replicate_Wild_Do_Table:
Replicate_Wild_Ignore_Table:
Last_Errno: 0
Last_Error:
Skip_Counter: 0
Exec_Master_Log_Pos: 961
Relay_Log_Space: 892
Until_Condition: None
Until_Log_File:
Until_Log_Pos: 0
Master_SSL_Allowed: No
Master_SSL_CA_File:
Master_SSL_CA_Path:
Master_SSL_Cert:
Master_SSL_Cipher:
Master_SSL_Key:
Seconds_Behind_Master: 0
Master_SSL_Verify_Server_Cert: No
Last_IO_Errno: 0
Last_IO_Error:
Last_SQL_Errno: 0
Last_SQL_Error:
Replicate_Ignore_Server_Ids:
Master_Server_Id: 1
Master_UUID: 45540733-4e0c-11ea-b0ac-0242ac110003
Master_Info_File: /var/lib/mysql/master.info
SQL_Delay: 0
SQL_Remaining_Delay: NULL
Slave_SQL_Running_State: Slave has read all relay log; waiting for more updates
Master_Retry_Count: 86400
Master_Bind:
Last_IO_Error_Timestamp:
Last_SQL_Error_Timestamp:
Master_SSL_Crl:
Master_SSL_Crlpath:
Retrieved_Gtid_Set:
Executed_Gtid_Set:
Auto_Position: 0
Replicate_Rewrite_DB:
Channel_Name:
Master_TLS_Version:
4. 查看数据表数据
mysql> show create table user\G
*************************** 1. row ***************************
Table: user
Create Table: CREATE TABLE `user` (
`id` int(10) NOT NULL AUTO_INCREMENT,
`name` varchar(30) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4

mysql> select * from user;
+----+------+
| id | name |
+----+------+
| 1 | 222 |
+----+------+
复制的控制相关命令：
stop salve //停止slave连路  
reset slave //重置slave连路  
start slave //开启slave连路  
stop master //停止master连路  
reset master //重置master连路  
start master //开启master连路
```

##### 第七步：主从服务器的进程查看

>   mysql-master：

![img](..\images\ycRaIIwO2j.png!large)

>   mysql-slave：

![img](..\images\hKvq1ijADN.png!large)