package com.costa.luiz.zero2hero.model.movie.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenreKeyValue {

    private Long id;
    private String name;
}
