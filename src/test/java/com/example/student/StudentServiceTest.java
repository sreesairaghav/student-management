package com.example.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class StudentServiceTest {

    @Test
    void testAddStudent() {

        StudentService service = new StudentService();

        Student student =
                new Student(103, "Arun", "Mechanical Engineering");

        service.addStudent(student);

        assertEquals(1, service.getAllStudents().size());
        assertEquals("Arun", service.getAllStudents().get(0).getName());
        assertEquals("Mechanical Engineering",
                service.getAllStudents().get(0).getCourse());
    }
}