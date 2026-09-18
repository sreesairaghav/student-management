package com.example.student;

import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private final List<Student> students = new ArrayList<>();

    // Operation 1: Add a student
    public void addStudent(Student student) {
        students.add(student);
    }

    // Operation 2: View all students
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }
}