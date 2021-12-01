package com.acme.kafka.connect.sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.apache.kafka.connect.util.ConnectorUtils;

import static com.acme.kafka.connect.sample.SampleMySQLConnectorConfig.*;

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
        log.info("Starting a connector TASKKKKKKKKKKKKK.");
        this.config = new SampleMySQLConnectorConfig(originalProps);
        this.originalProps = originalProps;
        String hostName = config.getString(HOST_PARAM_CONFIG);
        String userName = config.getString(USER_PARAM_CONFIG);
        String password = config.getString(PASSWORD_PARAM_CONFIG);
        int monitorThreadTimeout = config.getInt(MONITOR_THREAD_TIMEOUT_CONFIG);
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        List<Map<String, String>> taskConfigs = new ArrayList<>();


        List<String> partitions = getPartitions();
        if (partitions.isEmpty()) {
            taskConfigs = Collections.emptyList();
            log.warn("No tasks created because there is zero to work on");
        } else {
            int numTasks = Math.min(partitions.size(), maxTasks);
            List<List<String>> partitionSources = ConnectorUtils.groupPartitions(partitions, numTasks);
            for (List<String> source : partitionSources) {
                Map<String, String> taskConfig = new HashMap<>(originalProps);
                taskConfig.put("sources", String.join(",", source));
                taskConfigs.add(taskConfig);
            }
        }
        return taskConfigs;
    }

    private List<String> getPartitions() {
        List<String> temp = new ArrayList<>();
        temp.add("subscription");
        return temp;
    }

    @Override
    public void stop() {
    }

}
