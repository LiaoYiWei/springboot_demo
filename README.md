# spring boot demo
## 启动程序
**安装docker**  
https://www.docker.com/get-docker

**启动mysql容器**
```  
docker run -v ~/data/mysql:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root  -p 3306:3306 -e "TZ=Asia/Shanghai" -d --name=mysql mysql:latest --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci  --skip-grant-tables
```
**用户名密码**  
root/root  
**创建Database** 
```CREATE SCHEMA springboot;```
```
create table `Order`
(
	id bigint not null
		primary key,
	status varchar(50) null,
	price varchar(50) null
);
```
**启动zookeeper**
```
docker run --name=zookeeper -p 2818:2181 -p 2888:2888 -p 3888:3888 -d zookeeper:latest
```

**启动程序**  
`运行com.lyw.springboot_demo.SpringbootDemoApplication.main方法`    

## 相关链接
**功能api doc**  
<http://localhost:8080/swagger-ui.html>  
**监控doc**  
<http://localhost:8080/docs/>  
**监控地址HAL browser**  
<http://localhost:8080/actuator/browser.html#/actuator>

## spring boot介绍
* Spring Boot简化了基于Spring的应用开发，你只需要"run"就能创建一个独立的，产品级别的Spring应用。 Spring Boo为Spring平台及第三方库提供开箱即用的设置，这样你就可以有条不紊地开始。多数Spring Boot应用只需要很少的Spring配置。
* 你可以使用Spring Boot创建Java应用，并使用java -jar启动它或采用传统的war部署方式。Spring Boot也提供了一个运行"spring脚本"的命令行工具。
* Spring Boot主要的目标是：
   * 为所有Spring开发提供一个从根本上更快，且随处可得的入门体验。
   * 开箱即用。
   * 提供一系列大型项目常用的非功能性特征，比如：内嵌服务器，安全，指标，健康检测，外部化配置。
   * 没有代码生成，也不需要XML配置。
* Spring Boot将很多魔法带入了Spring应用程序的开发之中，其中最重要的是以下四个核心
   * 自动配置:针对很多Spring应用程序常见的应用功能，Spring Boot能自动提供相关配置。
   * 起步依赖:告诉Spring Boot需要什么功能，它就能引入需要的库。
   * 命令行界面:这是Spring Boot的可选特性，借此你只需写代码就能完成完整的应用程序，
     无需传统项目构建。
   * Actuator:让你能够深入运行中的Spring Boot应用程序，一探究竟。
## spring boot官方网站
<https://projects.spring.io/spring-boot/>
## spring boot原理
### 任何一个Spring Boot项目，都会用到如下的启动类
```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
### SpringBootApplication背后的秘密
```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
        @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
        @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
    ...
}
```
虽然定义使用了多个Annotation进行了原信息标注，但实际上重要的只有三个Annotation：
* @Configuration（@SpringBootConfiguration点开查看发现里面还是应用了@Configuration）
* @EnableAutoConfiguration
* @ComponentScan
所以，如果我们使用如下的SpringBoot启动类，整个SpringBoot应用依然可以与之前的启动类功能对等：
```java
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
每次写这3个比较累，所以写一个@SpringBootApplication方便点。接下来分别介绍这3个Annotation。
### @Configuration
这里的@Configuration对我们来说不陌生，它就是JavaConfig形式的Spring Ioc容器的配置类使用的那个@Configuration，SpringBoot社区推荐使用基于JavaConfig的配置形式，所以，这里的启动类标注了@Configuration之后，本身其实也是一个IoC容器的配置类。
举几个例子回顾下，XML跟config配置方式的区别：
基于XML的配置形式是这样:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd"
       default-lazy-init="true">
    <!--bean定义-->
    <bean id="mockService" class="..MockServiceImpl">
        <propery name ="dependencyService" ref="dependencyService" />
    </bean>
    <bean id="dependencyService" class="DependencyServiceImpl"></bean>
</beans>
```
而基于JavaConfig的配置形式是这样的:  
```java
@Configuration
public class MockConfiguration{
    @Bean
    public MockService mockService(){
        return new MockServiceImpl(dependencyService());
    }
    
    @Bean
    public DependencyService dependencyService(){
        return new DependencyServiceImpl();
    }
}
```
### @ComponentScan 
@ComponentScan这个注解在Spring中很重要，它对应XML配置中的元素，@ComponentScan的功能其实就是自动扫描并加载符合条件的组件（比如@Component和@Repository等）或者bean定义，最终将这些bean定义加载到IoC容器中。  
我们可以通过basePackages等属性来细粒度的定制@ComponentScan自动扫描的范围，如果不指定，则默认Spring框架实现会从声明@ComponentScan所在类的package进行扫描。

```
注：所以SpringBoot的启动类最好是放在root package下，因为默认不指定basePackages。
```
### @EnableAutoConfiguration
@EnableAutoConfiguration借助@Import的帮助，将所有符合自动配置条件的bean定义加载到IoC容器  
@EnableAutoConfiguration作为一个复合Annotation,其自身定义关键信息如下：
```java
@SuppressWarnings("deprecation")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import(EnableAutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {
    ...
}
```
其中，最关键的要属@Import(EnableAutoConfigurationImportSelector.class)，借助EnableAutoConfigurationImportSelector，@EnableAutoConfiguration可以帮助SpringBoot应用将所有符合条件的@Configuration配置都加载到当前SpringBoot创建并使用的IoC容器。就像一只“八爪鱼”一样    
借助于Spring框架原有的一个工具类：SpringFactoriesLoader的支持，@EnableAutoConfiguration可以智能的自动配置功效才得以大功告成！  
![Springboot-1](https://raw.githubusercontent.com/LiaoYiWei/springboot_demo/master/doc/springboot3-1.png)

### SpringFactoriesLoader详解
SpringFactoriesLoader属于Spring框架私有的一种扩展方案，其主要功能就是从指定的配置文件META-INF/spring.factories加载配置。
```java
public abstract class SpringFactoriesLoader {
    //...
    public static <T> List<T> loadFactories(Class<T> factoryClass, ClassLoader classLoader) {
        ...
    }


    public static List<String> loadFactoryNames(Class<?> factoryClass, ClassLoader classLoader) {
        ....
    }
}
```
配合@EnableAutoConfiguration使用的话，它更多是提供一种配置查找的功能支持，即根据@EnableAutoConfiguration的完整类名org.springframework.boot.autoconfigure.EnableAutoConfiguration作为查找的Key,获取对应的一组@Configuration类
![springboot-2](https://raw.githubusercontent.com/LiaoYiWei/springboot_demo/master/doc/springboot3-2.jpg)
上图就是从SpringBoot的autoconfigure依赖包中的META-INF/spring.factories配置文件中摘录的一段内容，可以很好地说明问题。  
所以，@EnableAutoConfiguration自动配置的魔法骑士就变成了：从classpath中搜寻所有的META-INF/spring.factories配置文件，并将其中org.springframework.boot.autoconfigure.EnableutoConfiguration对应的配置项通过反射（Java Refletion）实例化为对应的标注了@Configuration的JavaConfig形式的IoC容器配置类，然后汇总为一个并加载到IoC容器。

### 深入探索SpringApplication执行流程
SpringApplication的run方法的主要流程大体可以归纳如下：
1. 如果我们使用的是SpringApplication的静态run方法，那么，这个方法里面首先要创建一个SpringApplication对象实例，然后调用这个创建好的SpringApplication的实例方法。在SpringApplication实例初始化的时候，它会提前做几件事情：
    * 根据classpath里面是否存在某个特征类（org.springframework.web.context.ConfigurableWebApplicationContext）来决定是否应该创建一个为Web应用使用的ApplicationContext类型。
    * 使用SpringFactoriesLoader在应用的classpath中查找并加载所有可用的ApplicationContextInitializer
    * 使用SpringFactoriesLoader在应用的classpath中查找并加载所有可用的ApplicationListener。
    * 推断并设置main方法的定义类。
2. SpringApplication实例初始化完成并且完成设置后，就开始执行run方法的逻辑了，方法执行伊始，首先遍历执行所有通过SpringFactoriesLoader可以查找到并加载的SpringApplicationRunListener。调用它们的started()方法。
3. 创建并配置当前Spring Boot应用将要使用的Environment（包括配置要使用的PropertySource以及Profile）。
4. 遍历调用所有SpringApplicationRunListener的environmentPrepared()的方法。
5. 如果SpringApplication的showBanner属性被设置为true，则打印banner。
6. 根据用户是否明确设置了applicationContextClass类型以及初始化阶段的推断结果，决定该为当前SpringBoot应用创建什么类型的ApplicationContext并创建完成，然后根据条件决定是否添加ShutdownHook，决定是否使用自定义的BeanNameGenerator，决定是否使用自定义的ResourceLoader，当然，最重要的，将之前准备好的Environment设置给创建好的ApplicationContext使用。
7. ApplicationContext创建好之后，SpringApplication会再次借助Spring-FactoriesLoader，查找并加载classpath中所有可用的ApplicationContext-Initializer，然后遍历调用这些ApplicationContextInitializer的initialize（applicationContext）方法来对已经创建好的ApplicationContext进行进一步的处理。
8. 遍历调用所有SpringApplicationRunListener的contextPrepared()方法。
9. 最核心的一步，将之前通过@EnableAutoConfiguration获取的所有配置以及其他形式的IoC容器配置加载到已经准备完毕的ApplicationContext。
10. 遍历调用所有SpringApplicationRunListener的contextLoaded()方法。
11. 调用ApplicationContext的refresh()方法，完成IoC容器可用的最后一道工序。
12. 查找当前ApplicationContext中是否注册有CommandLineRunner，如果有，则遍历执行它们。
13. 正常情况下，遍历执行SpringApplicationRunListener的finished()方法、（如果整个过程出现异常，则依然调用所有SpringApplicationRunListener的finished()方法，只不过这种情况下会将异常信息一并传入处理）

去除事件通知点后，整个流程如下：
![Springboot-3](https://raw.githubusercontent.com/LiaoYiWei/springboot_demo/master/doc/springboot3-3.jpg)




 
 
  




