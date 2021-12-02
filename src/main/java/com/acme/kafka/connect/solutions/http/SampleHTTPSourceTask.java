package com.acme.kafka.connect.solutions.http;

import com.acme.kafka.connect.PropertiesUtil;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.acme.kafka.connect.solutions.http.SampleHTTPConnectorConfig.*;

public class SampleHTTPSourceTask extends SourceTask {

    private static Logger log = LoggerFactory.getLogger(SampleHTTPSourceTask.class);

    private SampleHTTPConnectorConfig config;
    private int monitorThreadTimeout;
    private CSVReader csvReader;

    public SampleHTTPSourceTask() {
    }

    @Override
    public String version() {
        return PropertiesUtil.getConnectorVersion();
    }

    @Override
    public void start(Map<String, String> properties) {
        config = new SampleHTTPConnectorConfig(properties);
        monitorThreadTimeout = config.getInt(MONITOR_THREAD_TIMEOUT_CONFIG);
        try {

            URL url = new URL(config.getString(SampleHTTPConnectorConfig.FILE_URL_PARAM_CONFIG));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream inputStream = urlConnection.getInputStream();
            csvReader = new CSVReader(new InputStreamReader(inputStream));
            csvReader.readNext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        Thread.sleep(monitorThreadTimeout);
        List<SourceRecord> records = new ArrayList<>();

        try {
            String[] value;
            //CHECKSTYLE:OFF
            if ((value = csvReader.readNext()) != null) {
                //CHECKSTYLE:ON
                records.add(new SourceRecord(
                        Collections.singletonMap("file", config.getString(SampleHTTPConnectorConfig.FILE_URL_PARAM_CONFIG)),
                        Collections.singletonMap("offset", 0),
                        config.getString(KAFKA_TOPIC_CONFIG), null, null, null, Schema.BYTES_SCHEMA,
                        String.join(",", value).getBytes()));
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return records;
    }

    @Override
    public void stop() {
        try {
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
