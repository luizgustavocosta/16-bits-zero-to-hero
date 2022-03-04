package com.costa.luiz.zero2hero.model.movie.dto;

import com.costa.luiz.zero2hero.model.movie.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovieMapper {

    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    @Mapping(source = "createdAt", target = "creationDateAndTime")
    @Mapping(target = "genre", expression = "java(String.join(\",\"," +
            "movie.getGenre().stream()" +
            ".map(com.costa.luiz.zero2hero.model.movie.Genre::getName)" +
            ".collect(java.util.stream.Collectors.toList())))")
    MovieDto toMovieDto(Movie movie);
}
