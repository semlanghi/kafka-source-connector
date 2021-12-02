package com.acme.kafka.connect.sample.mysql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acme.kafka.connect.sample.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

import static com.acme.kafka.connect.sample.mysql.SampleMySQLConnectorConfig.*;

public class SampleMySQLConnector extends SourceConnector {

    private final Logger log = LoggerFactory.getLogger(SampleMySQLConnector.class);

    private Map<String, String> originalProps;
    private SampleMySQLConnectorConfig config;


    @Override
    public String version() {
        return PropertiesUtil.getConnectorVersion();
    }

    @Override
    public ConfigDef config() {
        return CONFIG_DEF;
    }

    @Override
    public Class<? extends Task> taskClass() {
        return SampleMySQLSourceTask.class;
    }

    @Override
    public void start(Map<String, String> originalProps) {
        this.config = new SampleMySQLConnectorConfig(originalProps);
        this.originalProps = originalProps;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        List<Map<String, String>> taskConfigs = new ArrayList<>();
        taskConfigs.add(new HashMap<>(originalProps));
        return taskConfigs;
    }

    @Override
    public void stop() {
    }

}
