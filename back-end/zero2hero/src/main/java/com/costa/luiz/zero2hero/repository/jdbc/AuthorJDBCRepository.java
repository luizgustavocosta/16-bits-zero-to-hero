package com.costa.luiz.zero2hero.repository.jdbc;

import com.costa.luiz.zero2hero.model.movie.Author;
import com.costa.luiz.zero2hero.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

@Repository
@Profile("jdbc")
@RequiredArgsConstructor
public class AuthorJDBCRepository implements AuthorRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert authorJdbcInsert;

    @Autowired
    public AuthorJDBCRepository(DataSource dataSource) {

        this.authorJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("authors")
                .usingGeneratedKeyColumns("id");

        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    @Override
    public Author save(Author author) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(author);
        Number key = this.authorJdbcInsert.executeAndReturnKey(parameterSource);
        author.setId(key.longValue());
        return author;
    }

    @Override
    public List<Author> findAll() {
        List<Author> authors = this.namedParameterJdbcTemplate.query(
                "SELECT * FROM authors",
                Collections.emptyMap(),
                BeanPropertyRowMapper.newInstance(Author.class)
        );
        return authors;
    }
}
