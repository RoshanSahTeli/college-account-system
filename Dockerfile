FROM  openjdk:latest

EXPOSE 8080

ADD target/account-0.0.1-SNAPSHOT.jar account-0.0.1-SNAPSHOT.jar

ENTRYPOINT [ "java","-jar","account-0.0.1-SNAPSHOT.jar" ]