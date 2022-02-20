# 聊天室 Demo
![img1.png](images/img1.png)

# 项目依赖
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <version>2.6.2</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-websocket</artifactId>
        <version>2.6.2</version>
    </dependency>
    <dependency>
        <groupId>org.webjars</groupId>
        <artifactId>stomp-websocket</artifactId>
        <version>2.3.4</version>
    </dependency>
    <dependency>
        <groupId>org.webjars</groupId>
        <artifactId>sockjs-client</artifactId>
        <version>1.5.1</version>
    </dependency>
</dependencies>
```

# 项目介绍
1. 只是一个示例项目，并没有连接数据库，也没有使用配合的mq。
项目启动后，打开浏览器输入http://localhost:1001/index.html，该Demo项目只有这一个页面。
用户需要先登录，才能在聊天室聊天，账户/密码页面上有，如果需要额外添加，可以去AllUserList类中添加。
![img2.png](images/img2.png)
2. 然后用户就可以发送信息了
![img3.png](images/img3.png)

3. 浏览器打开新的标签，打开页面http://localhost:1001/index.html，再登录一个账号。
李三这边就会显示在线用户，并且聊天框中提示用户上线
![img.png](images/img4.png)

4. 李四这边也会显示在线用户张三
![img.png](images/img5.png)

5. 再登录一个账号，张三这边
![img.png](images/img6.png)

6. 李四这边
![img.png](images/img7.png)


7. 开始聊天
![img.png](images/img8.png)
![img.png](images/img9.png)
![img.png](images/img10.png)













