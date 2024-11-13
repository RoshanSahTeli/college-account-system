FROM  openjdk:latest

EXPOSE 8080

ADD target/account.jar account.jar

ENTRYPOINT [ "java","-jar","account.jar" ]