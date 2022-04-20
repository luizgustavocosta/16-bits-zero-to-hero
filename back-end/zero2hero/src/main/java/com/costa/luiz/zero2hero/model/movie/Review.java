package com.costa.luiz.zero2hero.model.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "reviews")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "review_id")
    private Author author;
    private String review;
    private boolean archived;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
