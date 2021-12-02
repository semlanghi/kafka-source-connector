package com.acme.kafka.connect.exercises.mysql;

import com.acme.kafka.connect.PropertiesUtil;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.acme.kafka.connect.exercises.mysql.SampleMySQLConnectorConfig.MONITOR_THREAD_TIMEOUT_CONFIG;

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
        //TODO instantiate  connector config
        monitorThreadTimeout = config.getInt(MONITOR_THREAD_TIMEOUT_CONFIG);
        try {
            //TODO create a connection (hint: JDBC protocol pattern is "jdbc:mysql://{host}:{port}/db")
            connection = null;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        Thread.sleep(monitorThreadTimeout);
        List<SourceRecord> records = new ArrayList<>();
        String sql = null; //TODO get the query
        try {

            if (offset > 0) {
                //TODO add offset clause to avoid duplicates
            }

            ResultSet set = null; //TODO execute the query.

            while (set.next()) {
                String value = "Data from " + sql + ": id " + set.getLong(1);
                records.add(new SourceRecord(
                        Collections.singletonMap(null, null),
                        Collections.singletonMap("offset", 0),
                        null, null, null, null, Schema.BYTES_SCHEMA,
                        null));
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
