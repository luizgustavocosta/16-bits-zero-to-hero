package com.costa.luiz.zero2hero.presentation.web.movie;

import com.costa.luiz.zero2hero.business.service.GenreService;
import com.costa.luiz.zero2hero.business.service.dto.GenreDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/genres")
@Tag(name="Genres")
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public List<GenreDto> all() {
        return genreService.findAll();
    }
}
