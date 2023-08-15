package org.example.container;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpServletRequest {

    private RequestMethod method;
    private String path;
    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, String> requestParams = new HashMap<>();
    private final BufferedReader bufferReader;

    public HttpServletRequest(InputStreamReader input) {
        this.bufferReader = new BufferedReader(input);
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getRequestParameter(String key) {
        return requestParams.get(key);
    }

    public boolean populate() throws IOException {
        String line = bufferReader.readLine();
        if(line == null || line.isEmpty()) {
            return false;
        }

        String[] lines = line.split(" ");

        if (lines.length != 3) {
            return false;
        }

        this.method = RequestMethod.valueOf(lines[0]);
        String[] params = lines[1].split("\\?");
        this.path = params[0];
        if(params.length > 1) {
            this.populateRequestParam(params[1]);
        }

        line = bufferReader.readLine();
        while (!line.isEmpty()) {
            String[] header = line.split(":", 2);
            this.headers.put(header[0], header[1]);
            line = bufferReader.readLine();
        }

        if(RequestMethod.POST.equals(method)) {
            StringBuilder body = new StringBuilder();
            while(bufferReader.ready()) {
                body.append((char) bufferReader.read());
            }
            this.populateRequestParam(body.toString());
        }

        return true;
    }

    private void populateRequestParam(String parameters) {
        String[] allParams = parameters.split("&");
        Arrays.stream(allParams)
                .map(param -> param.split("="))
                .forEach(keyValue -> requestParams.put(keyValue[0], keyValue[1]));
    }
}
