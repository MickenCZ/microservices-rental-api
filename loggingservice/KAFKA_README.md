# Logging Service

## Kafka

### server properties:
```properties
process.roles=broker,controller
controller.listener.names=CONTROLLER
node.id=1
controller.quorum.voters=1@127.0.0.1:9093

listeners=PLAINTEXT://127.0.0.1:9092,CONTROLLER://127.0.0.1:9093
listener.security.protocol.map=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT
inter.broker.listener.name=PLAINTEXT

log.dirs=/Users/jakubjanak/kafka-data

offsets.topic.replication.factor=1
transaction.state.log.replication.factor=1
transaction.state.log.min.isr=1
```

### kafka storage format: 
``` bash
kafka-storage format \                                            
--cluster-id $(kafka-storage random-uuid) \
--config /opt/homebrew/etc/kafka/kraft/server.properties
```

### run kafka:
```bash
kafka-server-start /opt/homebrew/etc/kafka/kraft/server.properties
```

### create topic:
```bash
kafka-topics --create \
--topic logging \
--bootstrap-server localhost:9092 \
--partitions 1 \
--replication-factor 1
```

### terminal kafka consumer:
```bash
kafka-console-consumer \
--bootstrap-server localhost:9092 \
--topic logging \
--from-beginning
```