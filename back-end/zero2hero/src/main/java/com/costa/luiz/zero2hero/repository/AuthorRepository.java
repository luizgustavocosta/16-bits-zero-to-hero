package com.costa.luiz.zero2hero.repository;

import com.costa.luiz.zero2hero.model.movie.Author;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository {

    Author save(Author author);
    List<Author> findAll();
}
