package ru.sberbank.service;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.jetbrains.annotations.NotNull;
import ru.sberbank.dao.DaoServer;

import java.io.*;
import java.net.InetSocketAddress;


public class SBServer implements SBService {

    private int port;
    private @NotNull
    final File data;
    private HttpServer httpServer;
    private DaoServer daoServer;

    public SBServer(int port, @NotNull final File data) {
        this.port = port;
        this.data = data;
    }

    @Override
    public void start() {
        try {
            daoServer = new DaoServer(data);
            httpServer = HttpServer.create(new InetSocketAddress(port), 0);
            httpServer.createContext("/v1/entity", new MyHandler());
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        httpServer.stop(1000);
    }

    private class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String requestParamValue = null;
            requestParamValue = handleGetRequest(httpExchange, 0);
            handleResponse(httpExchange,requestParamValue);
        }

        //Get request id=<ID>
        private String handleGetRequest(HttpExchange httpExchange, int numberParam) {
            return httpExchange
                    .getRequestURI()
                    .toString()
                    .split("\\?")[1]
                    .split("&")[numberParam]
                    .split("=")[1];
        }

        private void handleResponse(HttpExchange httpExchange, String requestParamValue) throws IOException {

            OutputStream outputStream = httpExchange.getResponseBody();
            String path = httpExchange.getRequestURI().getPath().substring(4);

            if ("GET".equals(httpExchange.getRequestMethod())) {
                byte[] resp = daoServer.get(requestParamValue);

                if (resp.length > 0) {
                    httpExchange.sendResponseHeaders(200, resp.length);
                    outputStream.write(resp);
                }
                else {
                    httpExchange.sendResponseHeaders(404, 0);
                    outputStream.write("Not Found".getBytes());
                }
                outputStream.flush();
                outputStream.close();
            }
            else if ("PUT".equals(httpExchange.getRequestMethod())) {
                daoServer.insert(requestParamValue, handleGetRequest(httpExchange, 1).getBytes());
                byte[] resp = "Created".getBytes();
                httpExchange.sendResponseHeaders(201, resp.length);
                outputStream.write(resp);
            }
            else if ("DELETE".equals(httpExchange.getRequestMethod())) {
                daoServer.delete(requestParamValue);
                byte[] resp = "Accepted".getBytes();
                httpExchange.sendResponseHeaders(202, resp.length);
                outputStream.write(resp);
            }
        }
    }
}
