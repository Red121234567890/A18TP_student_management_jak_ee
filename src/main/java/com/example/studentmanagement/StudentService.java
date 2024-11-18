package com.example.studentmanagement;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    // Liste des étudiants
    private List<Student> students = new ArrayList<>();

    // Constructeur pour initialiser quelques étudiants par défaut
    public StudentService() {
        students.add(new Student(1, "Alice", "Smith", "Computer Science"));
        students.add(new Student(2, "Bob", "Johnson", "Mathematics"));
        students.add(new Student(3, "Charlie", "Brown", "Physics"));
        students.add(new Student(4, "Diana", "Evans", "Chemistry"));
        students.add(new Student(5, "Eve", "Davis", "Biology"));
    }

    // Méthode pour obtenir la liste des étudiants
    public List<Student> getStudents() {
        return students;
    }

    // Méthode pour ajouter un nouvel étudiant
    public void addStudent(Student student) {
        students.add(student);
    }
}
