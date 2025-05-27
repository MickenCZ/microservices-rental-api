package cz.cvut.fel.nss.gardeningrental.loggingservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private final ElasticSearchClient elasticSearchClient;

    public KafkaConsumer(ElasticSearchClient elasticSearchClient) {
        this.elasticSearchClient = elasticSearchClient;
    }

    @KafkaListener(topics = "logging", groupId = "logging-service-group")
    public void consume(String message) {
        logger.info("Consumer received message: {}", message);
        elasticSearchClient.log(message, "INFO");
    }
}
