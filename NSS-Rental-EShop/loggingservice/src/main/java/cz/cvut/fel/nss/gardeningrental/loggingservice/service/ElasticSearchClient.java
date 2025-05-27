package cz.cvut.fel.nss.gardeningrental.loggingservice.service;

import cz.cvut.fel.nss.gardeningrental.loggingservice.model.Log;
import cz.cvut.fel.nss.gardeningrental.loggingservice.repository.LogRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ElasticSearchClient {

    private final LogRepository logRepo;

    public ElasticSearchClient(LogRepository logRepo) {
        this.logRepo = logRepo;
    }

    public void log(String message, String level) {
        Log log = new Log(message, level);
        log.setTimestamp(Instant.now());
        logRepo.save(log);
    }
}
