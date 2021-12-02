package com.acme.kafka.connect.sample.file;

import com.acme.kafka.connect.sample.PropertiesUtil;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.acme.kafka.connect.sample.file.SampleFileConnectorConfig.CONFIG_DEF;

public class SampleFileConnector extends SourceConnector {

    private final Logger log = LoggerFactory.getLogger(SampleFileConnector.class);

    private Map<String, String> originalProps;
    private SampleFileConnectorConfig config;


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
        return SampleFileSourceTask.class;
    }

    @Override
    public void start(Map<String, String> originalProps) {
        this.config = new SampleFileConnectorConfig(originalProps);
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
