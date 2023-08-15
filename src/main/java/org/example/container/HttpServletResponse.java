package org.example.container;

import java.io.*;
import java.util.*;

public class HttpServletResponse {

    private final OutputStream out;
    private final PrintWriter writer;
    private final List<String> lines = new ArrayList<>();
    public HttpServletResponse(OutputStream out) {
        this.out = out;
        this.writer=  new PrintWriter(this.out);
    }


    public void addBody(String... body) {
        lines.addAll(List.of(body));
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public  void missingHandlerResponse()  {
        String http = writeHTTPResponse("404 NOT FOUND");
        String contentType = writeHTTPContentType("text/html");
        String body = writeHTTPBody("Cannot process handler.");

        writer.println(http);
        writer.println(contentType);
        writer.println();
        writer.println(body);
    }

    public void errorResponse()  {
        String http = writeHTTPResponse("500 INTERNAL SERVER ERROR");
        String contentType = writeHTTPContentType("text/plain");
        String body = writeHTTPBody("Cannot process request.");

        writer.println(http);
        writer.println(contentType);
        writer.println();
        writer.println(body);
    }

    private static String writeHTTPBody(String body) {
        return String.format("<html><body>%s</html></body>", body);
    }

    private static String writeHTTPContentType(String contentType) {
        return String.format("Content-Type: %s", contentType);
    }

    private static String writeHTTPResponse(String response) {
        return String.format("HTTP/1.1 %s", response);
    }
}
