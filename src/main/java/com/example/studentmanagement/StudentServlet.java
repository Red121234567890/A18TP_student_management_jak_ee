package com.example.studentmanagement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/students/*")  // Mappage mis à jour pour le servlet
public class StudentServlet extends HttpServlet {
    private StudentService studentService = new StudentService();

    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("StudentServlet initialisé");  // Log indiquant que le servlet a été initialisé
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet appelé. RequestURI: " + req.getRequestURI());  // Log avec des informations sur la requête

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>")
                .append("<html><head>")
                .append("<meta charset='UTF-8'>")
                .append("<title>Gestion des étudiants</title>")
                .append("<style>")
                .append("body { font-family: Arial, sans-serif; margin: 20px; }")
                .append("form { margin-top: 20px; padding: 20px; border: 1px solid #ccc; }")
                .append("input { margin: 5px 0; padding: 5px; }")
                .append("</style>")
                .append("</head><body>");

        // Affichage d'informations sur l'URL actuel pour le débogage
        html.append("<div style='background-color: #eee; padding: 10px; margin-bottom: 20px;'>")
                .append("URL actuel : ").append(req.getRequestURI())
                .append("</div>");

        // Affichage de la liste des étudiants
        html.append("<h1>Liste des étudiants</h1>")
                .append("<ul>");
        for (Student student : studentService.getStudents()) {
            html.append("<li>")
                    .append(student.getFirstName())
                    .append(" ")
                    .append(student.getLastName())
                    .append(" - ")
                    .append(student.getSpecialty())
                    .append("</li>");
        }
        html.append("</ul>");

        // Formulaire pour ajouter un étudiant
        html.append("<h2>Ajouter un étudiant</h2>")
                .append("<form method='POST' action='" + req.getContextPath() + "/students'>")
                .append("<div><label for='firstName'>Prénom:</label>")
                .append("<input type='text' id='firstName' name='firstName' required></div>")
                .append("<div><label for='lastName'>Nom:</label>")
                .append("<input type='text' id='lastName' name='lastName' required></div>")
                .append("<div><label for='specialty'>Spécialité:</label>")
                .append("<input type='text' id='specialty' name='specialty' required></div>")
                .append("<div><input type='submit' value='Ajouter'></div>")
                .append("</form>");

        html.append("</body></html>");

        out.println(html.toString());
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost appelé. RequestURI: " + req.getRequestURI());  // Log indiquant qu'une requête POST a été reçue

        req.setCharacterEncoding("UTF-8");

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String specialty = req.getParameter("specialty");

        // Vérification des champs obligatoires
        if (firstName == null || firstName.isEmpty() ||
                lastName == null || lastName.isEmpty() ||
                specialty == null || specialty.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tous les champs sont obligatoires.");
            return;
        }

        // Création et ajout d'un nouvel étudiant
        Student newStudent = new Student(studentService.getStudents().size() + 1, firstName, lastName, specialty);
        studentService.addStudent(newStudent);

        System.out.println("Nouvel étudiant ajouté : " + firstName + " " + lastName + " - " + specialty);

        // Redirection vers la liste des étudiants après l'ajout
        resp.sendRedirect(req.getContextPath() + "/students");
    }
}
