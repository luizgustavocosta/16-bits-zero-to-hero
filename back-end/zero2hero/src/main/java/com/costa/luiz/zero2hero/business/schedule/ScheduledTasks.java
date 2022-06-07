package com.costa.luiz.zero2hero.business.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "imdb", name = "apiKey")
public class ScheduledTasks {

    @Value("${imdb.url}")
    private String baseURL;
    @Value("${imdb.comingSoon}")
    private String comingSoonEndpoint;
    @Value("${imdb.apiKey}")
    private String apiKey;

    @Scheduled(fixedRate = 120000)
    public void fetchTheComingSoonMovies() {
        String apiKey = null;
        log.info("Calling IMDB api {}", baseURL);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(baseURL + comingSoonEndpoint + "/" + apiKey, String.class);
        log.info("Result {}", result);

    }

}
