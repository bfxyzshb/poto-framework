### 什么是CQRS?
CQRS 架构全称是Command Query Responsibility Segregation，即命令查询职责分离，名词本身最早应该是Greg Young提出来的，但是概念却很早就有了。
本质上，CQRS也是一种读写分离的机制，架构图如下：

![](doc/cqrs.png)

### CQRS把整个系统划分成两块：

- Command Side 写的一边
接收外部所有的Insert、Update、Delete命令，转化为Command，每一个Command修改一个Aggregate的状态。Command Side的命令通常不需要返回数据。注意：这种“写”操作过程中，可能会涉及“读”，因为要做校验，这时可直接在这一边进行读操作，而不需要再到Query Side去。
- Query Side 读的一边
接受所有查询请求，直接返回数据。

