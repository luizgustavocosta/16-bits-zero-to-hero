package com.costa.luiz.zero2hero.model.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "authors")
@Builder
@AllArgsConstructor
public class Author {

    @Id
    private Long id;
    private String name;
    @ManyToMany
    private List<Review> reviews;

    public Author() {
    }
}


