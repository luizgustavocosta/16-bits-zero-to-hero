package com.costa.luiz.zero2hero.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTasks {

    @Scheduled(fixedRate = 120000)
    public void toBeDefined() {
        log.info("TBD");
    }

}
