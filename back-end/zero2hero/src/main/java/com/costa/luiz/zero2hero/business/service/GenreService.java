package com.costa.luiz.zero2hero.business.service;

import com.costa.luiz.zero2hero.business.service.dto.GenreDto;
import com.costa.luiz.zero2hero.business.service.dto.GenreMapper;
import com.costa.luiz.zero2hero.persistence.repository.GenreRepository;
import com.costa.luiz.zero2hero.persistence.repository.movie.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;


    public List<GenreDto> findAll() {
        return genreRepository.findAll().stream().map(genreMapper::toDto).collect(Collectors.toUnmodifiableList());
    }
}
