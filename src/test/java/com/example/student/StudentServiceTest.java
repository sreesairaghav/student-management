package com.example.student;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.EmptySource;

import java.util.List;

public class StudentServiceTest {

    private StudentService service;

    @BeforeEach
    void setUp() {
        service = new StudentService();
    }

    @Test
    void testAddStudent() {
        Student student = new Student(103, "Arun", "Mechanical Engineering");
        service.addStudent(student);

        assertEquals(1, service.getAllStudents().size());
        assertEquals("Arun", service.getAllStudents().get(0).getName());
        assertEquals("Mechanical Engineering", service.getAllStudents().get(0).getCourse());
    }

    @Test
    void testAddMultipleStudents() {
        service.addStudent(new Student(1, "Alice", "CS"));
        service.addStudent(new Student(2, "Bob", "EE"));
        service.addStudent(new Student(3, "Charlie", "ME"));

        List<Student> students = service.getAllStudents();
        assertEquals(3, students.size());
        assertEquals("Alice", students.get(0).getName());
        assertEquals("Bob", students.get(1).getName());
        assertEquals("Charlie", students.get(2).getName());
    }

    @Test
    void testGetAllStudentsReturnsEmptyListInitially() {
        assertTrue(service.getAllStudents().isEmpty());
    }

    @Test
    void testGetAllStudentsReturnsCopyNotReference() {
        service.addStudent(new Student(1, "Test", "Course"));
        List<Student> students = service.getAllStudents();
        students.clear();

        assertEquals(1, service.getAllStudents().size());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, -1, Integer.MAX_VALUE, Integer.MIN_VALUE})
    void testAddStudentWithVariousIds(int id) {
        Student student = new Student(id, "Test", "Course");
        service.addStudent(student);

        assertEquals(1, service.getAllStudents().size());
        assertEquals(id, service.getAllStudents().get(0).getId());
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", "Very Long Name That Exceeds Typical Length Limits For Testing Purposes", "Name With Spaces", "Special!@#$%^&*()Chars"})
    void testAddStudentWithVariousNames(String name) {
        Student student = new Student(1, name, "Course");
        service.addStudent(student);

        assertEquals(name, service.getAllStudents().get(0).getName());
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void testAddStudentWithBlankOrNullName(String name) {
        Student student = new Student(1, name, "Course");
        service.addStudent(student);

        assertEquals(1, service.getAllStudents().size());
        assertEquals(name, service.getAllStudents().get(0).getName());
    }

    @Test
    void testAddNullStudent() {
        service.addStudent(null);
        assertEquals(1, service.getAllStudents().size());
        assertNull(service.getAllStudents().get(0));
    }

    @Test
    void testStudentIdBoundaryValues() {
        service.addStudent(new Student(0, "Zero", "Course"));
        service.addStudent(new Student(Integer.MAX_VALUE, "Max", "Course"));
        service.addStudent(new Student(Integer.MIN_VALUE, "Min", "Course"));

        assertEquals(3, service.getAllStudents().size());
    }

    @Test
    void testAddDuplicateStudents() {
        Student student1 = new Student(1, "Alice", "CS");
        Student student2 = new Student(1, "Alice", "CS");

        service.addStudent(student1);
        service.addStudent(student2);

        assertEquals(2, service.getAllStudents().size());
    }
}