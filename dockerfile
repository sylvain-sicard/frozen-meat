FROM arm32v7/openjdk
MAINTAINER Sylvain SICARD <sylvain@mssd.fr>

ENV DB_HOST=192.168.1.100
ENV DB_PORT=32768
ENV DB_NAME=frozenmeat
ENV DB_USER=frozenmeat
ENV DB_PASSWORD=frozenmeat

ADD target/lib /usr/share/lib

ARG JAR_FILE

ADD target/${JAR_FILE} /usr/share/service.jar
 
EXPOSE 8890
ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/service.jar"]