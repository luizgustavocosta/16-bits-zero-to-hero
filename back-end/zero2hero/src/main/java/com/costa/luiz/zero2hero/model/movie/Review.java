package com.costa.luiz.zero2hero.model.movie;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToMany
    private List<Author> authors;
    private String review;
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
}
