package com.example.student;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class StudentApplication {

    public static void main(String[] args) throws IOException {

        StudentService service = new StudentService();

        service.addStudent(
                new Student(101, "Rahul", "Computer Science")
        );

        service.addStudent(
                new Student(102, "Priya", "Information Technology")
        );

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8082), 0
        );

        server.createContext("/", exchange -> {

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {

                String body = new String(
                        exchange.getRequestBody().readAllBytes(),
                        StandardCharsets.UTF_8
                );

                String[] values = body.split("&");

                int id = 0;
                String name = "";
                String course = "";

                for (String value : values) {
                    String[] pair = value.split("=", 2);

                    if (pair.length < 2) {
                        continue;
                    }

                    String key = URLDecoder.decode(
                            pair[0],
                            StandardCharsets.UTF_8
                    );

                    String data = URLDecoder.decode(
                            pair[1],
                            StandardCharsets.UTF_8
                    );

                    if ("id".equals(key)) {
                        id = Integer.parseInt(data);
                    } else if ("name".equals(key)) {
                        name = data;
                    } else if ("course".equals(key)) {
                        course = data;
                    }
                }

                service.addStudent(
                        new Student(id, name, course)
                );
            }

            List<Student> students = service.getAllStudents();

            StringBuilder response = new StringBuilder();

            response.append("<html>");
            response.append("<head>");
            response.append("<title>Student Management System</title>");
            response.append("</head>");
            response.append("<body>");

            response.append("<h1>Student Management System</h1>");

            response.append("<h2>Add Student</h2>");

            response.append("<form method='POST' action='/'>");

            response.append("ID: ");
            response.append("<input type='number' name='id' required><br><br>");

            response.append("Name: ");
            response.append("<input type='text' name='name' required><br><br>");

            response.append("Course: ");
            response.append("<input type='text' name='course' required><br><br>");

            response.append("<button type='submit'>Add Student</button>");

            response.append("</form>");

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

        byte[] responseBytes =
                response.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(
                200,
                responseBytes.length
        );

        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(responseBytes);
        }
    }
}