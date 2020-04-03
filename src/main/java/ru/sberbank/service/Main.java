package ru.sberbank.service;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            SBService service = SBServiceFactory.create(8080, new File("src/Entyties/Text"));
            service.start();
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
