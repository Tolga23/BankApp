FROM postgres
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD xxx
ENV POSTGRES_DB bank

FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","/application.jar"]


## Docker Compose Code
## docker build . -t bank-final:1.0
## docker run -p 8080:8080 bank-final:1.0  &&  docker run --name bank-final -d -p 8080:8080 bank-final:1.0
## docker ps -a
