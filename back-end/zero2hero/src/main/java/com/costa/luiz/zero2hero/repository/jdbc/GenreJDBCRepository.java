package com.costa.luiz.zero2hero.repository.jdbc;

import com.costa.luiz.zero2hero.model.genre.Genre;
import com.costa.luiz.zero2hero.repository.GenreRepository;
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
import java.util.*;

import static java.util.Objects.isNull;

@Repository
@Profile("jdbc")
@RequiredArgsConstructor
public class GenreJDBCRepository implements GenreRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert genreJdbcInsert;

    @Autowired
    public GenreJDBCRepository(DataSource dataSource) {
        this.genreJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("genres")
                .usingGeneratedKeyColumns("id");
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Genre> findAll() {
        List<Genre> genres = this.namedParameterJdbcTemplate.query(
                "SELECT * FROM genres",
                Collections.emptyMap(),
                BeanPropertyRowMapper.newInstance(Genre.class)
        );
        return genres;
    }

    @Override
    public Optional<Genre> findById(Long id) {
        Genre genre;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            genre = this.namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM genres WHERE id= :id",
                    params,
                    BeanPropertyRowMapper.newInstance(Genre.class)
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new ObjectRetrievalFailureException(Genre.class, id);
        }
        return Optional.of(genre);
    }

    @Override
    public Genre findByName(String name) {
        Genre genre;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("name", name);
            genre = this.namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM genres WHERE name = :name",
                    params,
                    BeanPropertyRowMapper.newInstance(Genre.class)
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
        return genre;
    }

    @Override
    public Genre save(Genre genre) {
        Genre savedGenre = findByName(genre.getName());
        if (isNull(savedGenre)) {
            BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(genre);
            Number key = this.genreJdbcInsert.executeAndReturnKey(parameterSource);
            genre.setId(key.longValue());
            return genre;
        }
        return savedGenre;
    }

}
