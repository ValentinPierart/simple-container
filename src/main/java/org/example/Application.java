package org.example;

import org.example.container.HttpServlet;
import org.example.container.SimpleWebContainer;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        var webContainer = new SimpleWebContainer(8888, "config.properties");
        webContainer.loadProperties();

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                webContainer.getHandlers().forEach(HttpServlet::destroy);
            }
        });
        webContainer.start();
    }
}
