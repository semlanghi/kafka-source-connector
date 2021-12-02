package com.acme.kafka.connect.exercises.http;

import com.acme.kafka.connect.PropertiesUtil;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.acme.kafka.connect.solutions.file.SampleFileConnectorConfig.CONFIG_DEF;

public class SampleHTTPConnector extends SourceConnector {

    private final Logger log = LoggerFactory.getLogger(SampleHTTPConnector.class);

    private Map<String, String> originalProps;
    private SampleHTTPConnectorConfig config;


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
        return SampleHTTPSourceTask.class;
    }

    @Override
    public void start(Map<String, String> originalProps) {
        //TODO instantiate default connector config
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
