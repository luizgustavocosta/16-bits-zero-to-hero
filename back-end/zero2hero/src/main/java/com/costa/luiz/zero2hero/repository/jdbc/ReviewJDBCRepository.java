package com.costa.luiz.zero2hero.repository.jdbc;

import com.costa.luiz.zero2hero.model.genre.Genre;
import com.costa.luiz.zero2hero.model.movie.Movie;
import com.costa.luiz.zero2hero.model.movie.Review;
import com.costa.luiz.zero2hero.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Profile("jdbc")
@RequiredArgsConstructor
public class ReviewJDBCRepository implements ReviewRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert reviewJdbcInsert;

    @Autowired
    public ReviewJDBCRepository(DataSource dataSource) {
        this.reviewJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reviews")
                .usingGeneratedKeyColumns("id");
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Review> findAll() {
        Map<String, Object> params = new HashMap<>();
        List<Review> reviews = this.namedParameterJdbcTemplate.query(
                "SELECT * FROM reviews",
                params,
                BeanPropertyRowMapper.newInstance(Review.class)
        );
        return reviews;
    }

    @Override
    public Optional<Review> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Review save(Review review) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(review);
        Number key = this.reviewJdbcInsert.executeAndReturnKey(parameterSource);
        review.setId(key.longValue());
        return review;
    }

    @Override
    public List<Review> findAllByMovie(Movie movie) {
        List<Review> reviews;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("id", movie.getId());
            reviews = this.namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM reviews WHERE movie_id= :id",
                    params,
                    BeanPropertyRowMapper.newInstance(List.class)
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new ObjectRetrievalFailureException(Movie.class, movie.getId());
        }
        return Collections.unmodifiableList(reviews);
    }

    @Override
    public List<Review> findAllByMovieIsNull() {
        return null;
    }

    @Override
    public void deleteAllByMovie_Id(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllMovieIdIsNull() {

    }
}
