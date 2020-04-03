package ru.sberbank.service;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.InetSocketAddress;


public class SBServer implements SBService {

    private int port;
    private @NotNull
    final File data;
    private HttpServer server;

    public SBServer(int port, @NotNull final File data) {
        this.port = port;
        this.data = data;
    }

    @Override
    public void start() {
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/v1", new MyHandler());
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        server.stop(1000);
    }

    private class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String requestParamValue = null;
            requestParamValue = handleGetRequest(httpExchange);
            handleResponse(httpExchange,requestParamValue);
        }

        private String handleGetRequest(HttpExchange httpExchange) {
            return httpExchange
                    .getRequestURI()
                    .toString()
                    .split("\\?")[1]
                    .split("=")[1];
        }

        private void handleResponse(HttpExchange httpExchange, String requestParamValue) throws IOException {

            OutputStream outputStream = httpExchange.getResponseBody();
            String path = httpExchange.getRequestURI().getPath().substring(4);

            if ("GET".equals(httpExchange.getRequestMethod())) {
                String resp = "Hello" + requestParamValue;

                httpExchange.sendResponseHeaders(200, resp.length());
                outputStream.write(resp.getBytes());

                outputStream.flush();
                outputStream.close();
            }
            else if ("PUT".equals(httpExchange.getRequestMethod())) {

            }
            else if ("DELETE".equals(httpExchange.getRequestMethod())) {

            }
        }
    }
}
