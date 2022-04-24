package com.costa.luiz.zero2hero.repository;

import com.costa.luiz.zero2hero.model.movie.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
