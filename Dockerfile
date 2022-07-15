#set up file to build my docker img (parent img)
#defining base docker image
FROM openjdk:18
LABEL maintainer ="Omar's"
ADD target/cinema-0.0.1-SNAPSHOT.jar springboot-dockerimg-demo.jar
ENTRYPOINT ["java","-jar","springboot-dockerimg-demo.jar"]

