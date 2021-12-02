package com.acme.kafka.connect.solutions.mysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.acme.kafka.connect.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;

import static com.acme.kafka.connect.solutions.mysql.SampleMySQLConnectorConfig.*;

public class SampleMySQLSourceTask extends SourceTask {

    private static Logger log = LoggerFactory.getLogger(SampleMySQLSourceTask.class);

    private SampleMySQLConnectorConfig config;
    private int monitorThreadTimeout;
    private Connection connection;
    private int offset = 0;

    public SampleMySQLSourceTask() {
    }

    @Override
    public String version() {
        return PropertiesUtil.getConnectorVersion();
    }

    @Override
    public void start(Map<String, String> properties) {
        config = new SampleMySQLConnectorConfig(properties);
        monitorThreadTimeout = config.getInt(MONITOR_THREAD_TIMEOUT_CONFIG);
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
        String sql = config.getString(SQL_QUERY_CONFIG);
        try {

            if (offset > 0) {
                sql = sql + " " + "OFFSET " + offset + ";";
            }

            ResultSet set = connection.prepareStatement(sql).executeQuery();

            while (set.next()) {
                String value = "Data from " + sql + ": id " + set.getLong(1);
                records.add(new SourceRecord(
                        Collections.singletonMap("sql", sql),
                        Collections.singletonMap("offset", 0),
                        config.getString(KAFKA_TOPIC_CONFIG), null, null, null, Schema.BYTES_SCHEMA,
                        value.getBytes()));
                offset++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    @Override
    public void stop() {
    }

}
