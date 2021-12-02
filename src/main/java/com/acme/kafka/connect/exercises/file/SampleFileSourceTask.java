package com.acme.kafka.connect.exercises.file;

import com.acme.kafka.connect.PropertiesUtil;
import com.opencsv.CSVReader;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.acme.kafka.connect.solutions.mysql.SampleMySQLConnectorConfig.MONITOR_THREAD_TIMEOUT_CONFIG;

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
        //TODO instantiate connector config
        monitorThreadTimeout = config.getInt(MONITOR_THREAD_TIMEOUT_CONFIG);
        try {
            //TODO read data from disk using CSV Reader (hint: files are in "/etc/examplefiles/")
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
                        //TODO complete the source record (Hint: file, topic name, and value)
                        Collections.singletonMap(null, null),
                        Collections.singletonMap("offset", 0),
                        null, null, null, null, Schema.BYTES_SCHEMA,
                        null));
            }
        } catch (Exception e) {
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
