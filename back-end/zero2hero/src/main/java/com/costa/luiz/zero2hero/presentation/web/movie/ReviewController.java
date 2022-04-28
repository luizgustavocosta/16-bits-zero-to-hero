package com.costa.luiz.zero2hero.presentation.web.movie;

import com.costa.luiz.zero2hero.business.service.ReviewService;
import com.costa.luiz.zero2hero.business.service.dto.ReviewDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/reviews")
@RestController
@RequiredArgsConstructor
@Tag(name="Reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBy(@PathVariable Long id) {
        reviewService.deleteById(id);
    }

    @GetMapping
    public List<ReviewDto> findAll() {
        return reviewService.findAll();
    }

    @GetMapping(path = "/{id}")
    public ReviewDto findById(@PathVariable Long id) {
        return reviewService.findById(id);
    }

}
