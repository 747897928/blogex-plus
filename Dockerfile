FROM registry.access.redhat.com/ubi8-minimal:8.4
MAINTAINER zhaoyijie<747897928@qq.com>

ENV JAVA_HOME=/opt/java/openjdk
# 引用java基础镜像
COPY --from=eclipse-temurin:17 $JAVA_HOME $JAVA_HOME
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# 复制jar包到容器内
COPY ./target/blogex-api  /usr/local/blogex-api

EXPOSE 20010

WORKDIR /usr/local/blogex-api
ENTRYPOINT ["sh","bin/entrypoint.sh"]