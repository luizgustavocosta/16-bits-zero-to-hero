package com.costa.luiz.zero2hero.beans;

import com.costa.luiz.zero2hero.model.movie.dto.GenreMapper;
import com.costa.luiz.zero2hero.model.movie.dto.MovieMapper;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public GenreMapper genreMapper() {
        return GenreMapper.INSTANCE;
    }

    @Bean
    public MovieMapper movieMapper() {
        return MovieMapper.INSTANCE;
    }
}
