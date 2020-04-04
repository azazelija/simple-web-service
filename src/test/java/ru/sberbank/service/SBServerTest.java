package ru.sberbank.service;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class SBServerTest {

    @Test
    void TestService() throws IOException {
        SBService service = SBServiceFactory.create(8080, new File("src/Entyties/Text"));
        service.start();
        TestServiceGET();
        TestServicePUT();
        TestServiceDELETE();

    }

    void TestServiceGET() throws IOException {
        String url = "http://localhost:8080/v1/entity?id=1";
        HttpURLConnection connection = (HttpURLConnection) (new URL(url).openConnection());
        connection.setRequestMethod("GET");

        StringBuilder respBody = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        reader.lines().forEach(respBody::append);
        reader.close();

        assertEquals("OK", connection.getResponseMessage());
        assertEquals(200, connection.getResponseCode());
        assertEquals("id = 1, data = \"Kate\";", respBody.toString());

        String url2 = "http://localhost:8080/v1/entity?id=100";
        HttpURLConnection connection2 = (HttpURLConnection) (new URL(url2).openConnection());
        connection2.setRequestMethod("GET");

        assertEquals("Not Found", connection2.getResponseMessage());
        assertEquals(404, connection2.getResponseCode());
    }

    void TestServicePUT() throws IOException {
        String url = "http://localhost:8080/v1/entity?id=6&name=Kate";
        HttpURLConnection connection = (HttpURLConnection) (new URL(url).openConnection());
        connection.setRequestMethod("PUT");

        assertEquals("Created", connection.getResponseMessage());
        assertEquals(201, connection.getResponseCode());
    }

    void TestServiceDELETE() throws IOException {
        String url = "http://localhost:8080/v1/entity?id=6";
        HttpURLConnection connection = (HttpURLConnection) (new URL(url).openConnection());
        connection.setRequestMethod("DELETE");

        assertEquals("Accepted", connection.getResponseMessage());
        assertEquals(202, connection.getResponseCode());
    }

}