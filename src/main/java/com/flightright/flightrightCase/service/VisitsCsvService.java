package com.flightright.flightrightCase.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class VisitsCsvService {

    public void csvReadder(String filePath){

        File csvFile = new File(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String email = fields[0];
                String phone = fields[1];
                String source = fields[2];

                System.out.println(email+phone+source);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
