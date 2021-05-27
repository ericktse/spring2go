
FROM openjdk:8-alpine
MAINTAINER xiaobin

# 设置为上海时区
RUN apk add --no-cache --update tzdata
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /app

WORKDIR /app

EXPOSE 7100

ADD ./target/spring2go-job-*.jar ./app.jar

CMD sleep 30;java -Djava.security.egd=file:/dev/./urandom -jar app.jar

