package com.costa.luiz.zero2hero.service.dto;

import com.costa.luiz.zero2hero.model.movie.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GenreMapper {

    GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);

    @Mapping(source = "value", target = "id")
    @Mapping(source = "label", target = "name")
    Genre toGenre(GenreDto genreDto);
}
