package cz.cvut.fel.nss.gradeningrental.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private static final String TOPIC = "logging";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        this.kafkaTemplate.send(TOPIC, message);
    }

    public void sendMessage(String... messages) {
        if (messages == null || messages.length == 0) return;

        // combine the messages
        String combinedMessage = String.join(" ", messages);

        sendMessage(combinedMessage);
    }
}
