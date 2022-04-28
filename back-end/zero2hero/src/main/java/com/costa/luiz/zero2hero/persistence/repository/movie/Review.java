package com.costa.luiz.zero2hero.persistence.repository.movie;

import lombok.*;

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
    private Author author;
    private String review;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Movie movie;
}