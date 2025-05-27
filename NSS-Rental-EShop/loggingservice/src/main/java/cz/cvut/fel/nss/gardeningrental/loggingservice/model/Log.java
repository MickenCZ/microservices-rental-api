package cz.cvut.fel.nss.gardeningrental.loggingservice.model;

import jakarta.persistence.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.Instant;

@Document(indexName = "log")
public class Log {
    public Log(String message, String level) {
        this.message = message;
        this.level = level;
    }

    @Id
    private String id;
    private String message;
    private String level;
    private Instant timestamp;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
