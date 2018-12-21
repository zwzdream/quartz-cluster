

## 该文件主要说明model1和model2之间的区别

## springboot2.0 Quartz自动化配置集成

model1和model2都是使用SpringBoot2.0特性，在pom引入SpringBoot提供的quartz依赖包

```java
 <!--quartz相关依赖-->
<dependency>
	<groupId>org.quartz-scheduler</groupId>
	<artifactId>quartz</artifactId>
	<version>${quartz.version}</version>
</dependency>
<dependency>
	<groupId>org.quartz-scheduler</groupId>
	<artifactId>quartz-jobs</artifactId>
	<version>${quartz.version}</version>
</dependency>
>>>>替换为：>>>>
<!--quartz依赖-->
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-quartz</artifactId>
     </dependency>
```

在之前章节我们使用`QuartzConfiguration`配置类来完成了`Quartz`需要的一系列配置，如：`JobFactory`、`SchedulerFactoryBean`等，在我们添加`spring-boot-starter-quartz`依赖后就不需要主动声明工厂类，因为`spring-boot-starter-quartz`已经为我们自动化配置好了。 

更多请参看文件:[SpringBoot2.0新特性 - Quartz自动化配置集成](http://blog.yuqiyu.com/spring-boot-chapter47.html)

-----



### model1

在application.yml配置`spring.quartz.properties` 属性，这样可以省略`quartz.properties`配置文件的定义；

在service层(业务逻辑层 )注入Scheduler Bean,是可以的，得到使用`spring.quartz.properties`属性配置的Scheduler

````java
 @Autowired
 private Scheduler scheduler;
````

不用在pom.xml引入c3p0依赖包.

数据库存的集群名称或者说是显示名（SCHED_NAME）为quartzScheduler，而不是application.yml配置文件里面配置的clusteredScheduler;

在application.yml文件可以不配置quartz的数据库（DataSource），这样默认quartz集群使用的DataSource跟项目的DataSource是同一个

重启后，会自动运行任务调度器（即执行定义的任务）。

注入的调度器（Scheduler)

### model2

使用`quartz.properties`配置文件配置quartz的属性

把quartz相关操作封装到QuartzManager工具类中，在该类中，无法注入Scheduler Bean（会报Null异常）

````java
@Component
public class QuartzManager {
     @Autowired
 private Scheduler scheduler;
    ......
}
````

在addJob方法中,`scheduler.scheduleJob(jobDetail, trigger);`任务调度群绑定好任务和触发器后，需要使用代码启动`if (!scheduler.isShutdown()) {   scheduler.start();}`，不然任务不会马上启动；

需要在pom.xml引入c3p0依赖包，quartz的默认数据库连接池，在创建任务（调用`QuartzManager.addJob()`需要）用到；

数据库存的集群名称或者说是显示名（SCHED_NAME）为MyClusteredScheduler,即quartz.properties里面配置的名字；

必须在quartz.properties文件里面配置集群使用的数据库（哪怕与项目共用一个数据库）

重启后，不会自动运行任务调度器，需要调用`QuartzManager.startJobs()`方法（该方法已定义好）启动所有定时任务，而且，每一个运行实例都需要这样，所以需要实现ApplicationRunner，在项目启动后，执行QuartzManager.startJobs()，马上开启所有定时任务

通过新建未初始化工厂拿到的，`new StdSchedulerFactory().getScheduler()`;

```java
@Component
public class AppTest implements ApplicationRunner {
      @Override
    public void run(ApplicationArguments args) throws Exception {
        QuartzManager.startJobs();
    }
}
```



-----

为了测试集群需要运行多个实例，在Program arguments配置--server.port即可。

model1的方式依赖于service层，需要写成一个业务类，但是编码少，model2的方式需要手动编码的比较多，但是可以当成工具类来用

---------

关于quartz集群，在数据库存的SCHED_NAME

model1，使用注入Scheduler Bean，存的是springboot2.0自动配置文件`QuartzAutoConfiguration`里面定义的` SchedulerFactoryBean`的实例名 `quartzScheduler`.

```java
@Bean
@ConditionalOnMissingBean
public SchedulerFactoryBean quartzScheduler() {
    .......
}
```

model2，使用quartz.propetties，存的是配置文件里面自定义的名字

![两种显示名对比](.\sched_name.jpg)

-----

官方文档：

In particular, an Executor bean is not associated with the scheduler as Quartz offers a way to configure the scheduler via spring.quartz.properties. If you need to customize the task executor, consider implementing SchedulerFactoryBeanCustomizer. 



--------



至于其他的，网上查

----

### model3

至于model3，是springboot2.0以前使用quartz 集权的写法，也就是springboot2.0提供quartz集群自动化配置的源码。（功能一样，写法可能略有出入）

详细参见，文章开头 ：springboot2.0 Quartz自动化配置集成

另外：将QuartzManagercopy一份QuartzManager2，

在addJob传入service层注入的Scheduler Bean

```

public static void addJob(Scheduler scheduler,String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass, String cron,Object...objects) {
   ...
   }
```

或者在里面定义withSchedule绑定schedule的方法，在`ApplicationRunner`实现类中进行绑定

```
public class QuartzManager2 {
    private static Scheduler scheduler ;

    public static void withSchedule(Scheduler scheduler){
        QuartzManager2.scheduler=scheduler;
    }
```

```
@Component
public class AppTest implements ApplicationRunner {
    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
                System.out.println("项目启动后立即执行这个方法");
                QuartzManager2.withSchedule(scheduler);
                ......
   }
```

------

mock测试时，必须给全URI，例如`localhost:8020/user/hi`，会出错，必须为`http://localhost:8020/user/hi`.