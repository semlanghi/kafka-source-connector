package com.acme.kafka.connect.solutions.mysql;

import java.util.Map;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;

public class SampleMySQLConnectorConfig extends AbstractConfig {

    public SampleMySQLConnectorConfig(final Map<?, ?> originalProps) {
        super(CONFIG_DEF, originalProps);
    }

    public static final String SQL_QUERY_CONFIG = "example.db.query";
    private static final String SQL_QUERY_DOC = "The SQL query to run";

    public static final String KAFKA_TOPIC_CONFIG = "example.kafka.topic";
    private static final String KAFKA_TOPIC_DOC = "This is the topic to write to.";

    public static final String HOST_PARAM_CONFIG = "example.db.hostname";
    private static final String HOST_PARAM_DOC = "This is the defined name of the host.";

    public static final String PORT_PARAM_CONFIG = "example.db.port";
    private static final String PORT_PARAM_DOC = "This is the defined port.";

    public static final String USER_PARAM_CONFIG = "example.db.user";
    private static final String USER_PARAM_DOC = "This is the defined name of the user.";

    public static final String DB_PARAM_CONFIG = "example.db.database";
    private static final String DB_PARAM_DOC = "This is the defined name of the database.";

    public static final String PASSWORD_PARAM_CONFIG = "example.db.password";
    private static final String PASSWORD_PARAM_DOC = "This is the password.";

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
        configDef.define(
            HOST_PARAM_CONFIG,
            Type.STRING,
            Importance.HIGH,
            HOST_PARAM_DOC)
        .define(
            PORT_PARAM_CONFIG,
            Type.INT,
            Importance.HIGH,
            PORT_PARAM_DOC)
        .define(
            USER_PARAM_CONFIG,
            Type.STRING,
            Importance.HIGH,
            USER_PARAM_DOC)
        .define(
            DB_PARAM_CONFIG,
            Type.STRING,
            Importance.HIGH,
            DB_PARAM_DOC)
        .define(
            PASSWORD_PARAM_CONFIG,
            Type.STRING,
            Importance.HIGH,
            PASSWORD_PARAM_DOC)
        .define(
            MONITOR_THREAD_TIMEOUT_CONFIG,
            Type.INT,
            MONITOR_THREAD_TIMEOUT_DEFAULT,
            Importance.HIGH,
            MONITOR_THREAD_TIMEOUT_DOC)
        .define(
            KAFKA_TOPIC_CONFIG,
            Type.STRING,
            Importance.HIGH,
            KAFKA_TOPIC_DOC)
        .define(
            SQL_QUERY_CONFIG,
            Type.STRING,
            Importance.HIGH,
            SQL_QUERY_DOC);
    }

}
