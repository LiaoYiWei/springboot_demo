# spring boot demo
## spring boot介绍
* Spring Boot简化了基于Spring的应用开发，你只需要"run"就能创建一个独立的，产品级别的Spring应用。 Spring Boo为Spring平台及第三方库提供开箱即用的设置，这样你就可以有条不紊地开始。多数Spring Boot应用只需要很少的Spring配置。
* 你可以使用Spring Boot创建Java应用，并使用java -jar启动它或采用传统的war部署方式。Spring Boot也提供了一个运行"spring脚本"的命令行工具。
* Spring Boot主要的目标是：
   * 为所有Spring开发提供一个从根本上更快，且随处可得的入门体验。
   * 开箱即用，但通过不采用默认设置可以快速摆脱这种方式。
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
举几个简单例子回顾下，XML跟config配置方式的区别：
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
    
}
```
其中，最关键的要属@Import(EnableAutoConfigurationImportSelector.class)，借助EnableAutoConfigurationImportSelector，@EnableAutoConfiguration可以帮助SpringBoot应用将所有符合条件的@Configuration配置都加载到当前SpringBoot创建并使用的IoC容器。就像一只“八爪鱼”一样    
借助于Spring框架原有的一个工具类：SpringFactoriesLoader的支持，@EnableAutoConfiguration可以智能的自动配置功效才得以大功告成！






 

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



 
 
  




