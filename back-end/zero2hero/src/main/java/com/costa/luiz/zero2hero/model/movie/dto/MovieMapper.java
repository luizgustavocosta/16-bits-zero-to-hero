package com.costa.luiz.zero2hero.model.movie.dto;

import com.costa.luiz.zero2hero.model.movie.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovieMapper {

    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "genre", ignore = true)
    MovieDto toDto(Movie movie);

//    @Mapping(target = "genre", expression = "java(String.join(\",\"," +
//            "movie.getGenre().stream()" +
//            ".map(com.costa.luiz.zero2hero.model.genre.Genre::getName)" +
//            ".collect(java.util.stream.Collectors.toList())))")
    @Mapping(target = "comments", ignore = true)
    MovieDto toDtoForReview(Movie movie);
}
