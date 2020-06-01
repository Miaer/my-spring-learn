名称解释
--
REST不是一种标准，而是一种风格，一种架构风格，符合了REST原则，就称它为REST风格架构      
REST(Representational State Transfer) 表现层状态转换：  
    首先要有资源才能表现，所以第一个名称是"资源"；  
    有了资源也要根据合适的形式表现资源，这也就是第二个名称——"表现层"；  
    最后资源可以被增、删、改等，也就是最后一个名词——"状态转换"；  
    
    资源：它可以是系统权限用户、角色和菜单等，也可以是一些媒体类型，如文本、图片、歌曲等，
    总之它就是一个具体存在的对象。可以用一个URI指向它。要获取这个资源，访问它的URI即可。
    在REST中每个资源都会有独一无二的URI。在REST中，URI可以称为端点(End Point)  
    
    表现层：有了资源还需要确定如何表现这个资源。例如，一个用户可以使用JSON、XML或者其他的形式表现出来，又如可能返回的是
    一幅图片。在互联网开发中，JSON数据是一种最常用的表现形式，本次也是以JSON为中心。
    
    状态转换：现实中资源并不是一成不变的，它是一个变化的过程，一个资源可以经历创建(create)、访问(visit)、
    修改(updata)和删除(delete)的过程。对于HTTP协议，是一个没有状态的协议，资源状态的改变只能在服务端
    保存和变化，不过好在HTTP中存在多种动作来应对这些变化。
    
REST风格架构的特点
--

    服务器存在一系列的资源，每个资源通过单独唯一的URI进行标识  
    客户端和服务器之前可以相互传递资源，而资源会以某种表现层得以展示  
    客户端通过HTTP协议所定义的动作对资源进行操作，以实现资源的状态转换
    
HTTP的动作
--

GET(VISIT):访问服务器资源(一个或多个)　　
POST(CREATE):提交服务资源信息，用来创建新的资源　　
PUT(UPDATE):修改服务器已经存在的资源　　
PATCH(UPDATE):修改服务器已经存在的资源，使用PATCH时只需要将部分资源属性提交，不常用，有些Java类不支持，慎用　　
DELETE(DELETE):从服务器将资源删除　　     
        
还有两个不常用的动作行为
--
HEAD：获取资源的元数据(Content-type)  
OPTIONS：提供资源可供客户端修改的属性信息  

REST风格的URI设计
--

    # 获取用户信息，1是用户编号  
    GET /user/1
    # 查询多个用户信息
    GET /users/{userName}/{note}
    # 创建用户
    POST /user/{userName}/{sex}/{note}
    # 修改用户全部属性
    PUT /user/{id}/{userName}/{sex}/{note}
    # 修改用户名称(部分属性)
    PATCH /user/{id}/{userName}

REST风格的一些误区
--
    1、不应该在URI中存在动词
        GET /user/get/1 修改为 GET /user/1
    2、不应该加入版本号
        GET /v1/user/1
    3、不应该使用URL传递参数
        PUT users?userName=user_name&note=note
       REST风格的建议是采用
        PUT users/{userName}/note

使用Spring MVC开发REST风格端点
--

注解
--

    @GetMapping
    @PostMapping
    @PutMapping
    @PatchMapping
    @DeleteMapping
