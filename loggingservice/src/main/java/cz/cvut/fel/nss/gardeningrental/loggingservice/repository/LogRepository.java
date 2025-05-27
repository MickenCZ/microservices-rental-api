package cz.cvut.fel.nss.gardeningrental.loggingservice.repository;

import cz.cvut.fel.nss.gardeningrental.loggingservice.model.Log;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LogRepository extends ElasticsearchRepository<Log, String> {
}
