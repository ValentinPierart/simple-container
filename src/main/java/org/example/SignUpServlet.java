package org.example;

import org.example.container.HttpServlet;
import org.example.container.HttpServletRequest;
import org.example.container.HttpServletResponse;

import java.io.PrintWriter;

public class SignUpServlet extends HttpServlet {

    @Override
    protected void init() {
        System.out.println("HELLO SIGN UP");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = response.getWriter();

        writer.println("<html><body>");
        writer.println("<form method=\"post\">");
        writer.println("First Name: <input type=\"text\" id=\"fname\" name=\"fname\" value=\"Val\"><br>");
        writer.println("Last Name: <input type=\"text\" id=\"lname\" name=\"lname\" value=\"Windu\"><br><br>");
        writer.println("<input type=\"submit\" value=\"Submit\">");
        writer.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = response.getWriter();
        writer.println("<html><body>");
        writer.println("First Name: " + request.getRequestParameter("fname"));
        writer.println("<br>");
        writer.println("Last Name: " + request.getRequestParameter("lname"));
        writer.println("<br>");
        writer.println("doPost() in SignupServlet.........");
        writer.println("</body></html>");    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
