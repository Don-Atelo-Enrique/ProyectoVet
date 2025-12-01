FROM amazoncorretto:17 AS build

COPY ./ /app
WORKDIR /app

RUN chmod u+x ./mvnw

RUN ./mvnw dependency:go-offline

RUN ./mvnw package -DskipTests

### TODO: Solution to my problem, is to add the a build stage here
### and in it, first, modify the application.properties file so that it
### points to the db (docker compose) database server.
FROM amazoncorretto:17 AS deploy

COPY --from=build /app/target/proyecto-0.0.1-SNAPSHOT.jar /app.jar
## COPY ./compilet/gestiontareas-0.0.1-SNAPSHOT.jar /app.jar

CMD ["java","-jar","/app.jar"]
# CMD ["/mvnw","spring-boot:run"]
