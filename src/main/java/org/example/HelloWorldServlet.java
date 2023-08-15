package org.example;

import org.example.container.HttpServlet;
import org.example.container.HttpServletRequest;
import org.example.container.HttpServletResponse;

import java.io.PrintWriter;

public class HelloWorldServlet extends HttpServlet {

    @Override
    protected void init() {
        System.out.println("HELLO WORLD INIT");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = response.getWriter();
        writer.println("<html><body>");
        writer.println("HELLO WORLD");
        writer.println("</body></html>");
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
