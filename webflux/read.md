webflux响应式编程框架 需要spring boot2.0以上才能支持

#基础概念
##响应式
####响应式编程的宣言
响应式编程框架，是基于响应式宣言的理念所产生的编程方式。响应式宣言分为4个部分  
- 灵敏的  
就是可以快速响应的，只要有任何可能，系统都应该尽可能的做出响应  
-可恢复的  
系统在运行中可能出现问题，但是能够有很强大的容错机制和修复机制保持响应性  
-可伸缩  
在任何 负载下，响应式编程都可以根据自身压力变化，请求少时，通过减少资源释放服务器的压力，负责大时能够通过扩展算法和软硬件的方式扩展服务器能力，以经济
实惠的方式实现可伸缩性。  
-消息驱动的  
响应式编程存在异步消息机制，事件之间的协作是通过消息进行连接的  

基于以上的理念，响应式编程提出了各种模型来满足响应式编程的理念，其中比较著名的有Reactor和RxJava，spring5基于它们构建WebFlux，默认使用Reactor。

####Reactor模型
为了解释这个模型存在的含义，先看传统编程中的模型  

![image传统线程模型](/photo/传统线程模型.jpg)
当请求上来之后，只能在请求队列中等待或者被系统抛弃，这样对于后续的请求而言，要么信赖的线程要等到旧线程运行完成后才能提供服务，要么就被系统所抛弃。为了克服这一问题，开发者提出了Reactor模式，其模型图如下：  
![image传统线程模型](/photo/传统线程模型.jpg)
当客户端触发服务端的一个响应时，服务器存在一个Selector线程，这个线程只是负责轮询发送过来的事件，并不处理请求，当它接收到有客户端事件时，就会找到对应的请求处理器
(RequestHandler),然后启用另外一条线程运行处理器。因为Selector线程只是负责轮询并不进行复杂的业务功能，所以它可以在轮询之后对请求做实时响应。
由于事件存在很多种，所以请求处理器也存在多个，因此还需要进行区分事件的类型，所以Selector存在一个路由的问题。当请求处理器处理业务时，结果最终会转换为数据流
(data stream)发送到客户端。对于数据流的处理还有一些细节，如背压(Back Pressure)等。  
从上述中可以看出，Reactor是一种基于事件的模型，对应服务器线程而言，它也是一种异步的，首先是Selector线程轮询到事件，然后通过路由找到处理器去运行对应的逻辑，
处理器最后所返回的结果会转换为数据流。

#####Reactor中的组件
- Reactor:Reactor是IO事件的派发者。
- Acceptor:Acceptor接受client连接，建立对应client的Handler，并向Reactor注册此Handler。
- Handler:和一个client通讯的实体，按这样的过程实现业务的处理。一般在基本的Handler基础上还会有更进一步的层次划分， 用来抽象诸如decode，process
和encoder这些过程。比如对Web Server而言，decode通常是HTTP请求的解析， process的过程会进一步涉及到Listener和Servlet的调用。业务逻辑的处
理在Reactor模式里被分散的IO事件所打破， 所以Handler需要有适当的机制在所需的信息还不全（读到一半）的时候保存上下文，并在下一次IO事件到来的时候
（另一半可读了）能继续中断的处理。为了简化设计，Handler通常被设计成状态机，按GoF的state pattern来实现。

    Reactor：相当于有分发功能的Selector  
    Acceptor：NIO中建立连接的那个判断分支  
    Handler：消息读写处理等操作类  
    
#####Reactor的几种模型

- Reactor单线程模型  
    ![image(单线程模型)](/photo/Reactor单线程模型)  
和NIO很相似，将消息相关处理独立到Handler中去了，但是如果在Handler中处理速度慢，那么后续的客户端请求都会被积压，导致响应变慢  

- Reactor多线程模型
    ![image(单线程模型)](/photo/Reactor多线程模型)
多线程模型就是将Handler中的IO操作和非IO操作分开。客户端的请求直接丢到线程池中，客户的请求就不会被阻塞。
当用户进一步增加的时候，Reactor会出现瓶颈，因为Reactor即处理IO操作请求，又要响应请求，所以引出了主从Reactor模型

- 主从Reactor模型
    ![image(主从Reactor模型)](/photo/主从Reactor模型)
主Reactor负责处理请求，从Reactor负责处理IO操作的请求



###Spring WebFlux概述
对于响应式编程而言氛围Router Functions、Spring WebFlux和HTTP/Reactive Streams共三层:

- Router Functions  
    是一个路由分发层，也就是它会根据请求的事件，决定采用什么类的什么方法处理客户端发送过来的事件请求。显然，在Reactor模式中，它就是Selector的作用。  
- Spring-webflux  
    是一种控制层，类似SpringMVC框架的层级，它主要处理业务逻辑前的封装和控制数据流返回格式等  
- HTTP/Reactive Streams  
    是将结果转换为数据流的过程。  

Spring WebFlux需要的是能够支持Servlet3.1+的容器，如Tomcat、Jetty和Undertow等，而在Java异步编程的领域，使用最多的是Netty，所以在Spring boot
对spring WebFlux的starter中默认是依赖于Netty库的。

在Spring WebFlux中，存在两种开发方式，一种是类似于Spring MVC的模式，另一种是函数功能性的编程。

数据流的封装
--
Reactor提供的Flux和Mono，它们都是封装数据流的类。其中Flux是存放0~N个数据流序列，响应式框架会一个接一个地(不是一次性的)将它们发送到客户端；
Mono则是存放0~1个数据流序列，两者之间可以相互转化。

背压(Backpressure)
--
这个概念只对Flux有意义。对于客户端，有时候响应能力距离服务端有很大的差距，如果在很短的时间内服务端将大量的数据流传输给客户端，
那么客户端就可能被压垮。为了解决这个问题，一般会考虑使用响应式拉取，也就是将服务端的数据划分多个序列，一次仅发送一个数据流序列给客户端，当客户端处理
完这个序列后，再给服务端发送消息，然后再拉取第二个序列进行处理，处理完后，再给服务端发送消息，依次类推，知道Flux中的0~N个数据流被完全处理，这样客户端
就可以根据自己的响应速度来获取数据流。