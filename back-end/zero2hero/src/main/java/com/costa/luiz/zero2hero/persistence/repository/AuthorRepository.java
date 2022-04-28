package com.costa.luiz.zero2hero.persistence.repository;

import com.costa.luiz.zero2hero.persistence.repository.movie.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
