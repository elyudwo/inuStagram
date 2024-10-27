FROM openjdk:17-jdk
#ENV TZ=Asia/Seoul
#RUN apt-get update && \
#    apt-get upgrade -y && \
#    apt-get install -yq tzdata && \
#    ln -fs /usr/share/zoneinfo/Asia/Seoul /etc/localtime && \
#    dpkg-reconfigure -f noninteractive tzdata

ARG JAR_FILE=build/libs/webclient-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} webclient-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/webclient-0.0.1-SNAPSHOT.jar"]