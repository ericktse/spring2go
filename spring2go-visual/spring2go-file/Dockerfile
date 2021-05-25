FROM openjdk:8-alpine

# 设置为上海时区
RUN apk add --no-cache --update tzdata
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /app

WORKDIR /app

EXPOSE 6500

ADD ./target/spring2go-file-0.0.1.jar ./

#-Djava.security.egd=file:/dev/./urandom
#将$JAVA_HOME/jre/lib/security/Java.security内,将securerandom.source的内容改为file:/dev/./urandom即可
#Linux或者部分unix系统提供随机数设备是/dev/random 和/dev/urandom ，两个有区别，urandom安全性没有random高，但random需要时间间隔生成随机数。
#可能在生成随机数的时候卡住了,导致tomcat启动不了
#在服务器启动时也可以加上参数 -Djava.security.egd=file:/dev/./urandom

#sleep 60;
#通过sleep实现docker容器启动顺序的控制，
#通常DB直接启动，中间件（注册中心等）等待30秒启动，业务模块等待60秒启动
CMD sleep 60;java -Djava.security.egd=file:/dev/./urandom -jar spring2go-file-0.0.1.jar