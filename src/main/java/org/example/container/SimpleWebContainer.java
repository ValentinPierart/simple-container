package org.example.container;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class SimpleWebContainer {

    private final int port;
    private final String configPath;
    private final Map<String, HttpServlet> urlToHandlers = new HashMap<>();

    public Collection<HttpServlet> getHandlers() {
        return urlToHandlers.values();
    }

    public SimpleWebContainer(int port, String configPath) {
        this.port = port;
        this.configPath = configPath;
    }

    public void start() throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(this.port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                var socketHandler =  new SocketHandler(socket, urlToHandlers);
                socketHandler.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadProperties() throws IOException {
        try(var inputStream = getClass().getClassLoader().getResourceAsStream(configPath)) {
            var properties = new Properties();
            properties.load(inputStream);
            properties.forEach((key, value) -> urlToHandlers.put((String) key, this.loadServlet((String) value)));
        } catch (IOException e) {
            throw new IOException(String.format("Unable to find config file at %s", configPath));
        }
    }

    private HttpServlet loadServlet(String servletClass) {
        try {
            HttpServlet servlet = (HttpServlet) Class.forName(servletClass).getDeclaredConstructor().newInstance();
            servlet.init();
            return servlet;
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}