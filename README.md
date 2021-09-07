# poto-framework

- process-oriented to object 面向过程到面向对象编程，缩写poto

### 吐槽
- 目前微服务很火，而且现在说架构都是基于业务的宏观设计，而恰恰忽略了代码层面的架构，所有现在大部分的项目是微服务划分的很好，
在打开实际的各个微服务的代码，有的可以说几乎不可维护的状态，代码都是基于Restful mvc的代码结构层次，甚至划分module都是mvc（简单的项目还是可以用的），一旦项目复杂,日积月累的业务代码的堆积，
各种层次技术人员的随意编写代码，代码惨不忍睹！！！相信大家最头疼的就是看别人的代码。
- 拿着面向对象的语言写着面向过程的代码。

### DDD领域驱动
- DDD不是一套架构，而是一种架构思想，poto-framework只是框架的约束，降低DDD的实践门槛。业务层领域的划分才是重点。

### DDD分层架构
- Evans在它的《领域驱动设计：软件核心复杂性应对之道》书中推荐采用分层架构去实现领域驱动设计：

  其实这种分层架构我们早已驾轻就熟，MVC模式就是我们所熟知的一种分层架构，我们尽可能去设计每一层，使其保持高度内聚性，让它们只对下层进行依赖，体现了高内聚低耦合的思想。
分层架构的落地就简单明了了，用户界面层我们可以理解成web层的Controller，应用层和业务无关，它负责协调领域层进行工作，领域层是领域驱动设计的业务核心，包含领域模型和领域服务，领域层的重点放 在如何表达领域模型上，无需考虑显示和存储问题，基础实施层是最底层，提供基础的接口和实现，领域层和应用服务层通过基础实施层提供的接口实现类如持久化、发送消息等功能。阿里巴巴开源的整洁面向对向分层架构COLA就采取了这样的分层架构来实现领域驱动。

- 改进DDD分层架构和DIP依赖倒置原则
  DDD分层架构是一种可落地的架构，但是我们依然可以进行改进，Vernon在它的《实现领域驱动设计》一书中提到了采用依赖倒置原则改进的方案。
所谓的依赖倒置原则指的是：高层模块不应该依赖于低层模块，两者都应该依赖于抽象，抽象不应该依赖于细节，细节应该依赖于抽象。

从图中可以看到，基础实施层位于其他所有层的上方，接口定义在其它层，基础实施实现这些接口。依赖原则的定义在DDD设计中可以改述为：领域层等其他层不应该依赖于基础实施层，两者都应该依赖于抽象，具体落地的时候，这些抽象的接口定义放在了领域层等下方层中。这也就是意味着一个重要的落地指导原则： 所有依赖基础实施实现的抽象接口，都应该定义在领域层或应用层中。

采用依赖倒置原则改进DDD分层架构除了上面说的DIP的好处外，还有什么好处吗？其实这种分层结构更加地高内聚低耦合。每一层只依赖于抽象，因为具体的实现在基础实施层，无需关心。只要抽象不变，就无需改动那一层，实现如果需要改变，只需要修改基础实施层就可以了。

采用依赖倒置原则的代码落地中，资源库Repository的抽象接口定义就会放在领域层了，下文会再阐述如何落地Repository。

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


