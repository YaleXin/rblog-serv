FROM openjdk:8-jre-alpine
# 容器内创建项目目录
RUN mkdir -p /opt/blog
WORKDIR /opt/blog
# 将当前目录下文件(将后端 Java 项目打包的 jar 包放置于此目录下 ) 放入容器指定目录
ADD . /opt/blog
ENTRYPOINT ["java", "-jar", "rblog-0.0.1-SNAPSHOT.jar", ">>", "./log/nohup`date +%Y-%m-%d`.out", "2>&1"]