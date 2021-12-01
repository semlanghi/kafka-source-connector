package com.acme.kafka.connect.sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;

import static com.acme.kafka.connect.sample.SampleMySQLConnectorConfig.*;

public class SampleMySQLSourceTask extends SourceTask {

    private static Logger log = LoggerFactory.getLogger(SampleMySQLSourceTask.class);

    private SampleMySQLConnectorConfig config;
    private int monitorThreadTimeout;
    private List<String> sources;
    private Connection connection;
    private PreparedStatement preparedStatement;

    public SampleMySQLSourceTask() {
    }

    @Override
    public String version() {
        return PropertiesUtil.getConnectorVersion();
    }

    @Override
    public void start(Map<String, String> properties) {
        log.info("Starting a TASKKKKKKKKKKKKK.");
        config = new SampleMySQLConnectorConfig(properties);
        monitorThreadTimeout = config.getInt(MONITOR_THREAD_TIMEOUT_CONFIG);
        String sourcesStr = properties.get("sources");
        sources = Arrays.asList(sourcesStr.split(","));

        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + config.getString(HOST_PARAM_CONFIG) + ":"
                            + config.getInt(PORT_PARAM_CONFIG) + "/kafka_test",
                    config.getString(USER_PARAM_CONFIG), config.getString(PASSWORD_PARAM_CONFIG));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        Thread.sleep(monitorThreadTimeout);
        List<SourceRecord> records = new ArrayList<>();
        for (String source : sources) {
            log.info("Polling data from the source '" + source + "'");
            try {

                ResultSet set = connection.prepareStatement("SELECT * FROM " + source + ";").executeQuery();

                while (set.next()) {
                    String value = "Data from " + source + ": id " + set.getLong(1);
                    records.add(new SourceRecord(
                            Collections.singletonMap("source", source),
                            Collections.singletonMap("offset", 0),
                            source, null, null, null, Schema.BYTES_SCHEMA,
                            value.getBytes()));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return records;
    }

    @Override
    public void stop() {
    }

}