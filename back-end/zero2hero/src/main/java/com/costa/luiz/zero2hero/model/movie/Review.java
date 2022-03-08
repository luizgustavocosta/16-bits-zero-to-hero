package com.costa.luiz.zero2hero.model.movie;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue
    private Long id;
    private String author;
    private String review;
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
}
