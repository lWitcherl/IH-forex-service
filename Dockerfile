FROM openjdk:17
ADD /target/forex-service.jar forexservice.jar
ENTRYPOINT ["java", "-jar", "forexservice.jar"]