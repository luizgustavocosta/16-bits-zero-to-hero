package com.costa.luiz.zero2hero.repository.springdata;

import com.costa.luiz.zero2hero.model.movie.Author;
import com.costa.luiz.zero2hero.repository.AuthorRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("spring-data")
public interface AuthorSpringRepository extends AuthorRepository, JpaRepository<Author, Long> {
}
