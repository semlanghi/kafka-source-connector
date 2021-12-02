package com.acme.kafka.connect.solutions.file;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;

import java.util.Map;

public class SampleFileConnectorConfig extends AbstractConfig {

    public SampleFileConnectorConfig(final Map<?, ?> originalProps) {
        super(CONFIG_DEF, originalProps);
    }

    public static final String KAFKA_TOPIC_CONFIG = "example.kafka.topic";
    private static final String KAFKA_TOPIC_DOC = "This is the topic to write to.";

    public static final String FILE_NAME_PARAM_CONFIG = "example.file.name";
    private static final String FILE_NAME_PARAM_DOC = "This is the defined name of the file.";

    public static final String MONITOR_THREAD_TIMEOUT_CONFIG = "monitor.thread.timeout";
    private static final String MONITOR_THREAD_TIMEOUT_DOC = "Timeout used by the monitoring thread";
    private static final int MONITOR_THREAD_TIMEOUT_DEFAULT = 10000;

    public static final ConfigDef CONFIG_DEF = createConfigDef();

    private static ConfigDef createConfigDef() {
        ConfigDef configDef = new ConfigDef();
        addParams(configDef);
        return configDef;
    }

    private static void addParams(final ConfigDef configDef) {
        configDef
                .define(
                        FILE_NAME_PARAM_CONFIG,
                        Type.STRING,
                        Importance.HIGH,
                        FILE_NAME_PARAM_DOC)
                .define(
                        KAFKA_TOPIC_CONFIG,
                        Type.STRING,
                        Importance.HIGH,
                        KAFKA_TOPIC_DOC)
                .define(
                        MONITOR_THREAD_TIMEOUT_CONFIG,
                        Type.INT,
                        MONITOR_THREAD_TIMEOUT_DEFAULT,
                        Importance.HIGH,
                        MONITOR_THREAD_TIMEOUT_DOC);
    }

}
