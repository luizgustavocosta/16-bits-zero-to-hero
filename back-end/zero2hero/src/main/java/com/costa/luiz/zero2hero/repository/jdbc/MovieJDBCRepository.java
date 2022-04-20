package com.costa.luiz.zero2hero.repository.jdbc;

import com.costa.luiz.zero2hero.model.movie.Movie;
import com.costa.luiz.zero2hero.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;

@Repository
@Profile("jdbc")
@RequiredArgsConstructor
public class MovieJDBCRepository implements MovieRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert movieJdbcInsert;
    private final SimpleJdbcInsert reviewJdbcInsert;

    @Autowired
    public MovieJDBCRepository(DataSource dataSource) {

        this.movieJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("movies")
                .usingGeneratedKeyColumns("id");
        this.reviewJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reviews")
                .usingGeneratedKeyColumns("id");

        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Movie> findAll() {
        Map<String, Object> params = new HashMap<>();
        List<Movie> movies = this.namedParameterJdbcTemplate.query(
                "SELECT * FROM movies",
                params,
                BeanPropertyRowMapper.newInstance(Movie.class)
        );
        return movies;
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Movie save(Movie movie) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(movie);
        Number key = this.movieJdbcInsert.executeAndReturnKey(parameterSource);
        movie.setId(key.longValue());

        if (isNull(movie.getReviews())) {
            return movie;
        }
        movie.getReviews().forEach(review -> {
            BeanPropertySqlParameterSource beanReview = new BeanPropertySqlParameterSource(review);
            Number returnKey = this.reviewJdbcInsert.executeAndReturnKey(beanReview);
            review.setId(returnKey.longValue());
        });
        return movie;
    }

    @Override
    public void deleteById(Long id) {

    }

}
