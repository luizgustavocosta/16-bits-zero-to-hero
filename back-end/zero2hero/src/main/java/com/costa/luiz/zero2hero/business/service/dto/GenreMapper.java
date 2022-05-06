package com.costa.luiz.zero2hero.business.service.dto;

import com.costa.luiz.zero2hero.persistence.repository.movie.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GenreMapper {

    GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);

    GenreDto toDto(Genre genre);
}
