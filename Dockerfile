# FROM 10.241.60.7:8004/base/jdk:8u171
#
# USER root
#
# COPY --chown=bonree:bonree OpenRUM-collector.jar /data/br/jar/
# COPY --chown=bonree:root entrypoint.sh /data/br/jar/
# COPY --chown=bonree:root collector.env /data/br/conf/
#
# WORKDIR /data/br/jar
# ENTRYPOINT ["bash", "/data/br/jar/entrypoint.sh"]

FROM  openjdk:8

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} collector.jar
ENTRYPOINT ["java","-jar","collector.jar"]



# 添加 Java 8 镜像来源
#FROM  openjdk:8
## 添加参数
#ARG JAR_FILE
## 添加 Spring Boot 包
#ADD target/${JAR_FILE} app.jar
## 执行启动命令
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]