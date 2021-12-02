package com.acme.kafka.connect.sample.file;

import com.acme.kafka.connect.sample.PropertiesUtil;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.acme.kafka.connect.sample.mysql.SampleMySQLConnectorConfig.*;

public class SampleFileSourceTask extends SourceTask {

    private static Logger log = LoggerFactory.getLogger(SampleFileSourceTask.class);

    private SampleFileConnectorConfig config;
    private int monitorThreadTimeout;
    private CSVReader csvReader;

    public SampleFileSourceTask() {
    }

    @Override
    public String version() {
        return PropertiesUtil.getConnectorVersion();
    }

    @Override
    public void start(Map<String, String> properties) {
        config = new SampleFileConnectorConfig(properties);
        monitorThreadTimeout = config.getInt(MONITOR_THREAD_TIMEOUT_CONFIG);
        try {
            csvReader = new CSVReader(new FileReader("/etc/examplefiles/"
                    + config.getString(SampleFileConnectorConfig.FILE_NAME_PARAM_CONFIG)));
            csvReader.readNext();
        } catch (CsvValidationException | IOException e) {
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
                        Collections.singletonMap("file", config.getString(SampleFileConnectorConfig.FILE_NAME_PARAM_CONFIG)),
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
