FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

EXPOSE 80

ENTRYPOINT ["java","-jar","target/course_ventures-0.0.1-SNAPSHOT.jar"]