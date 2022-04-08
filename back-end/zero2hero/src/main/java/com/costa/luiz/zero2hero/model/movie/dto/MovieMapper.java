package com.costa.luiz.zero2hero.model.movie.dto;

import com.costa.luiz.zero2hero.model.movie.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovieMapper {

    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    @Mapping(target = "genreAsString", expression = "java(String.join(\",\"," +
            "movie.getGenre().stream()" +
            ".map(com.costa.luiz.zero2hero.model.genre.Genre::getName)" +
            ".collect(java.util.stream.Collectors.toList())))")
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "genreIds", expression = "java(movie.getGenre().stream().map(com.costa.luiz.zero2hero.model.genre.Genre::getId).collect(java.util.stream.Collectors.toList()))")
    MovieDto toDto(Movie movie);

    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "genreAsString", ignore = true)
    @Mapping(target = "genreIds", expression = "java(movie.getGenre().stream().map(com.costa.luiz.zero2hero.model.genre.Genre::getId).collect(java.util.stream.Collectors.toList()))")
    MovieDto toDtoForReview(Movie movie);
}
