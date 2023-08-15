package org.example.container;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class SocketHandler extends Thread {

    private final Socket socket;
    private final Map<String, HttpServlet> urlToHandlers;

    public   SocketHandler(Socket socket, Map<String, HttpServlet> urlToHandlers) {
        this.socket = socket;
        this.urlToHandlers = urlToHandlers;
    }

    @Override
    public void run() {
        InputStream socketStream = null;
        try {
            socketStream = socket.getInputStream();
            var socketOutput = socket.getOutputStream();
            var input = new InputStreamReader(socketStream);
            var request = new HttpServletRequest(input);

            HttpServletResponse response = new HttpServletResponse(socketOutput);
            if(!request.populate()) {
                response.errorResponse();
            } else {
                HttpServlet servlet = urlToHandlers.get(request.getPath());
                if(servlet == null) {
                    response.missingHandlerResponse();
                } else {
                    PrintWriter writer = response.getWriter();
                    writer.println("HTTP/1.1 200 OK");
                    writer.println("Content-Type: text/html");
                    writer.println();

                    servlet.service(request, response);
                }
            }

            response.getWriter().flush();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
