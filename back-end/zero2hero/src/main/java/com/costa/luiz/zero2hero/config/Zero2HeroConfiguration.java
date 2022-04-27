package com.costa.luiz.zero2hero.config;

import com.costa.luiz.zero2hero.service.dto.GenreMapper;
import com.costa.luiz.zero2hero.service.dto.MovieMapper;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Zero2HeroConfiguration {

    @Bean
    public GenreMapper genreMapper() {
        return GenreMapper.INSTANCE;
    }

    @Bean
    public MovieMapper movieMapper() {
        return MovieMapper.INSTANCE;
    }

    @Bean
    public GroupedOpenApi groupedOpenApiForMovie() {
        return GroupedOpenApi.builder()
                .group("Movie")
                .packagesToScan("com.costa.luiz.zero2hero.actions")
                .pathsToMatch("/**")//Any endpoint
                .build();
    }
}
