# poto-framework

- process-oriented to object 面向过程到面向对象编程，缩写poto

### 吐槽
- 目前微服务很火，而且现在说架构都是基于业务的宏观设计，而恰恰忽略了代码层面的架构，所有现在大部分的项目是微服务划分的很好，
在打开实际的各个微服务的代码，有的可以说几乎不可维护的状态，代码都是基于Restful mvc的代码结构层次，甚至划分module都是mvc（简单的项目还是可以用的），一旦项目复杂,日积月累的业务代码的堆积，
各种层次技术人员的随意编写代码，代码惨不忍睹！！！相信大家最头疼的就是看别人的代码。
- 拿着面向对象的语言写着面向过程的代码。

### DDD领域驱动
- DDD不是一套架构，而是一种架构思想，poto-framework只是框架的约束，降低DDD的实践门槛。业务层领域的划分才是重点。

### java的设计原则
- 单一职责原则
- 依赖倒置原则
- 开闭原则

### 什么是CQRS?
CQRS 架构全称是Command Query Responsibility Segregation，即命令查询职责分离，事件驱动。名词本身最早应该是Greg Young提出来的，但是概念却很早就有了。
本质上，CQRS也是一种读写分离的机制，架构图如下：

![](doc/cqrs.png)

### CQRS把整个系统划分成两块：

- Command Side 写的一边
接收外部所有的Insert、Update、Delete命令，转化为Command，每一个Command修改一个Aggregate的状态。Command Side的命令通常不需要返回数据。注意：这种“写”操作过程中，可能会涉及“读”，因为要做校验，这时可直接在这一边进行读操作，而不需要再到Query Side去。
- Query Side 读的一边
接受所有查询请求，直接返回数据。


