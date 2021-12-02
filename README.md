# Kafka Connect Sample Connectora

This repo contains few sample Connectors for Kafka. 

The file connector reads a CSV file from disk and forwards it line-by-line to a kafka topics. 

The HTTP connector reads a an API (which return a CSV) and forward the content to a kafka topics. 

The mySQL connector reads a table of subscriptions from a database, forwarding them to a kafka topics. 

## Building the connector

The first thing you need to do to start using this connector is building it. In order to do that, you need to install the following dependencies:

- [Java 1.8+](https://openjdk.java.net/)
- [Apache Maven](https://maven.apache.org/)

After installing these dependencies, execute the following command:

```bash
mvn clean package
```

## Trying the connector

After building the connector you can try it by using the Docker-based installation from this repository.

### 1 - Starting the environment

Start the environment with the following command:

```bash
docker-compose up
```

**NB: Wait until all containers are up so you can start the testing.**
In particular, in order to keep track of the connect building, use the log on the connect container. 

```bash
docker-compose logs -f connect
```


### 2 - Install the connector

Open a terminal to execute the following command for executing the mysql connector:

```bash
curl -X POST -H "Content-Type:application/json" -d @configs/{location}/mysql-connector-config.json http://localhost:8083/connectors
```

{location} can be exercises or solutions.

Open a terminal to execute the following command for executing the file connector:

```bash
curl -X POST -H "Content-Type:application/json" -d @configs/{location}/file-connector-config.json http://localhost:8083/connectors
```

{location} can be exercises or solutions.

### 4 - Check the data in Kafka

Open a terminal to execute the following command:

```bash
docker exec kafka kafka-console-consumer --bootstrap-server kafka:9092 --topic {topic} --from-beginning
```

{topic} can be either subscriptionT, fileT, httpT.


NB: Indicate the right topic according to the right Connector.

# License

This project is licensed under the [Apache 2.0 License](./LICENSE).
