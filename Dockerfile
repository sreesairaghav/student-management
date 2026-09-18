FROM eclipse-temurin:25-jre

WORKDIR /app

COPY target/student-management-1.0-SNAPSHOT.jar app.jar

EXPOSE 8082

CMD ["java", "-cp", "app.jar", "com.example.student.StudentApplication"]