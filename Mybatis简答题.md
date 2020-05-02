1、Mybatis动态sql是做什么的？都有哪些动态sql？简述一下动态sql的执行原理？

    Mybatis动态sql可以在Xml映射文件内，以标签的形式编写动态sql，使得sql可以根据参数的变化而相应的进行改变
    常用的动态sql标签，<where> <if> <foreach> <trim> <set> <choose> <when>
    执行原理是根据表达式的值 完成逻辑判断并动态拼接sql的功能

2、Mybatis是否支持延迟加载？如果支持，它的实现原理是什么？

    Mybatis仅支持association关联对象和collection关联集合对象的延迟加载，association指的是一对一，collection指的是一对多
    在Mybatis中，可以配置是否启用延迟加载lazyLoadingEnabled=true|false
    原理：使用CGLIB创建目标对象的代理对象，当跳用目标方法时，进入拦截器方法，比如调用 a.getB().getName()，
    拦截器 invoke()方法发现 a.getB()是null 值，那么就会单独发送事先保存好的查询关联 B 对象的 sql，把 B 查询上来，
    然后调用 a.setB(b)，于是 a 的对象 b 属性就有值了，接着完成 a.getB().getName()方法的调用。这就是延迟加载的基本原理
    
3、Mybatis都有哪些Executor执行器？它们之间的区别是什么？

    **SimpleExecutor、ReuseExecutor、BatchExecutor**
    **SimpleExecutor：** 每执行一次update或select，就开启一个Statement对象，用完立刻关闭Statement对象
    
    **ReuseExecutor：** 执行update或select，以sql作为key查找Statement对象，存在就使用，不存在就创建，用完后，
    不关闭Statement对象，而是放置于Map内，供下一次使用。简言之，就是重复使用Statement对象
    
    **BatchExecutor：** 执行update（没有select，JDBC批处理不支持select），将所有sql都添加到批处理中（addBatch()），
    等待统一执行（executeBatch()），它缓存了多个Statement对象，每个Statement对象都是addBatch()完毕后，等待逐一执行
    executeBatch()批处理。与JDBC批处理相同
  
4、简述下Mybatis的一级、二级缓存（分别从存储结构、范围、失效场景。三个方面来作答）？  


    **存储结构：**
    
        一级缓存使用的是基于PerpetualCache对象的HashMap存储
        
        二级缓存默认使用的也是PerpetualCache 存储，且可以自定义存储源，如redis、Ehcache
        
        
    **缓存范围：**
    
        一级缓存的作用域为session级别，即只缓存存在当前线程中
        
        二级缓存的作用域是mapper级别，可以在多线程之间共享
        
    **失效场景：**

        一级缓存：当 Session flush 或 close 之后，该Session中的所有 Cache 就将清空
        
        当某一个作用域(一级缓存Session/二级缓存Namespaces)的进行了 C/U/D 操作后，默认该作用域下所有 select 中的缓存将被clear
        
        增删改操作，无论是否进行提交commit()，均会清空一级、二级缓存
        
5、简述Mybatis的插件运行原理，以及如何编写一个插件？

    Mybatis采用责任链模式，通过动态代理组织多个插件（拦截器），通过这些插件可以改变Mybatis的默认行为（诸如SQL重写之类的）
    
    通过JDK的动态代理为需要拦截的接口生成代理对象，然后实现接口的拦截方法，所以当执行需要拦截的接口方法时，会进入拦截方法（AOP面向切面编程的思想）