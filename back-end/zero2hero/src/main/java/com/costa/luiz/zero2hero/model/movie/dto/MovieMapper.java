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
    @Mapping(target = "genreList", expression = "java(movie.getGenre().stream().map(genre -> GenreDto.builder().value(genre.getId()).label(genre.getName()).build()).collect(java.util.stream.Collectors.toList()))")
    MovieDto toDto(Movie movie);

    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "genre", ignore = true)
    @Mapping(target = "genreList", expression = "java(movie.getGenre().stream().map(genre -> GenreDto.builder().value(genre.getId()).label(genre.getName()).build()).collect(java.util.stream.Collectors.toList()))")
    MovieDto toDtoForReview(Movie movie);
}
