# a spring boot demo

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





 
 
  




