package com.costa.luiz.zero2hero.business.service.dto;

import com.costa.luiz.zero2hero.persistence.repository.movie.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    private Long id;
    private String name;
    private List<Review> reviews;
}
