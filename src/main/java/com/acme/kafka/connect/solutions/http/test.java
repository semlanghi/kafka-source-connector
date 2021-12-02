package com.acme.kafka.connect.solutions.http;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class test {

    public static void main(String[] args) throws IOException, CsvValidationException {
        URL url = new URL("https://raw.githubusercontent.com/semlanghi/kafka-source-connector/master/files/sub_file.csv");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        InputStream inputStream = urlConnection.getInputStream();
        CSVReader  csvReader = new CSVReader(new InputStreamReader(inputStream));
        System.out.println(String.join(",", csvReader.readNext()));
    }
}
