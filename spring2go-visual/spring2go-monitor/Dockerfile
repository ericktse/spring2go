FROM openjdk:8-alpine

# 设置为上海时区
RUN apk add --no-cache --update tzdata
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /app

WORKDIR /app

EXPOSE 9100

ADD ./target/spring2go-monitor-0.0.1.jar ./

#将$JAVA_HOME/jre/lib/security/Java.security内,将securerandom.source的内容改为file:/dev/./urandom即可
#Linux或者部分unix系统提供随机数设备是/dev/random 和/dev/urandom ，两个有区别，urandom安全性没有random高，但random需要时间间隔生成随机数。
#可能在生成随机数的时候卡住了,导致tomcat启动不了
#在服务器启动时也可以加上参数 -Djava.security.egd=file:/dev/./urandom
CMD sleep 30;java -Djava.security.egd=file:/dev/./urandom -jar spring2go-monitor-0.0.1.jar