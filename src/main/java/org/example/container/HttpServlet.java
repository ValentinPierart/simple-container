package org.example.container;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class HttpServlet {

    protected abstract void init();

    protected void service(HttpServletRequest request, HttpServletResponse response) {
        RequestMethod method = request.getMethod();
        switch (method) {
            case GET -> doGet(request, response);
            case POST -> doPost(request, response);
            default -> throw new RuntimeException("Method not supported");
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {}

    public void destroy() {

    }
}
