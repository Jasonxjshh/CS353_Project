# CS353_Project_Backend

## 0. Version

```txt
	The Client side:
	1.	Python 3.7.7 
	2.	adafruit-circuitpython-scd30	2.2.0
    3. 	pip		21.1.2
    4.	pika	1.2.0
```
```txt
	The server side:
	1.	Java JDK1.8 
	2.	SpringBoot 2.5.4 
	3.	MyBatis-Plus 3.4.3 
	4.	Swagger 3.0.0
	5.	FastJson 1.2.76 
	6.	org.springframework.boot:spring-boot-starter-amqp:2.6.1
```

## 1. Java Server 

#### 	Step1: 

​			Pull the code from GitHub or unzip the project files.

> ​		https://github.com/Jasonxjshh/CS353_back-end		

​			If you get the Project successfully, you can see the structure of the project

​			![image-20211226154910543](C:\Users\22762\AppData\Roaming\Typora\typora-user-images\image-20211226154910543.png)

#### 	Step2:  

​			Set up the MySQL database, RabbitMQ Server and do some configurations.

​			To set up the MySQL database, you can run the CS353sql.sql file. 

​			To set up the RabbitMQ, I suggest to use Docker. It is very convenient. 

​			If you setup those successfully, open the application.yml file to change some configurations.

```yaml
spring:
  datasource:
    username: {your MySQL database username}
    password: {your MySQL database password}
    url: {your MySQL database url}
    driver-class-name: com.mysql.cj.jdbc.Driver
  rabbitmq:
    host: {your RabbitMQ host}
    port: {your RabbitMQ port}
    username: {your RabbitMQ usernmae}
    password: {your RabbitMQ password}
    virtual-host: /Jason
    /* for the virtual-host, I suggest to create a host named Jason*/


mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  type-aliases-package: com.jason.pojo
```

#### 	Step3:

​			If every thing goes well, you can start the SpringBoot Application by running the Cs353ProjectApplication.java.

​			If you see 

![image-20211226160655678](C:\Users\22762\AppData\Roaming\Typora\typora-user-images\image-20211226160655678.png)

​			You Java server runs successfully.

​			If it is failed, check out the configurations and the version of the dependencies.

## 2. Python Client

#### 		Step1:

```python
credentials = pika.PlainCredentials("Jason", "123456")#change it with your RabbitMQ username and password
connection = pika.BlockingConnection(
    						pika.ConnectionParameters("121.199.73.186", 5672, '/Jason', credentials)
							)# change it with your RabbitMQ host, port and the virtual-host
channel = connection.channel()
channel.queue_declare("sensorData", durable=True)
```

#### 		Step2:

​				move the file in the Python_code into Raspberry Pi 4 and install the dependencies and tools in the Raspberry Pi 4.

#### 		Step3:

​				Connect to the sensor and run the code and you will see the data information print on the console.



