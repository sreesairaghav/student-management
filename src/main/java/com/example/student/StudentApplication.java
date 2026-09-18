package com.example.student;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class StudentApplication {

    public static void main(String[] args) throws IOException {

        StudentService service = new StudentService();

        // Add sample students
        service.addStudent(
                new Student(101, "Rahul", "Computer Science")
        );

        service.addStudent(
                new Student(102, "Priya", "Information Technology")
        );

        // Start web server on port 8082
        HttpServer server = HttpServer.create(
                new InetSocketAddress(8082), 0
        );

        server.createContext("/", exchange -> {

            List<Student> students = service.getAllStudents();

            StringBuilder response = new StringBuilder();

            response.append("<html>");
            response.append("<head>");
            response.append("<title>Student Management System</title>");
            response.append("</head>");
            response.append("<body>");

            response.append("<h1>Student Management System</h1>");
            response.append("<h2>Students</h2>");

            response.append("<ul>");

            for (Student student : students) {
                response.append("<li>")
                        .append(student.getId())
                        .append(" - ")
                        .append(student.getName())
                        .append(" - ")
                        .append(student.getCourse())
                        .append("</li>");
            }

            response.append("</ul>");
            response.append("</body>");
            response.append("</html>");

            sendResponse(exchange, response.toString());
        });

        server.start();

        System.out.println(
                "Student Management System running at http://localhost:8082"
        );
    }

    private static void sendResponse(
            HttpExchange exchange,
            String response
    ) throws IOException {

        exchange.getResponseHeaders()
                .set("Content-Type", "text/html; charset=UTF-8");

        exchange.sendResponseHeaders(
                200,
                response.getBytes().length
        );

        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(response.getBytes());
        }
    }
}