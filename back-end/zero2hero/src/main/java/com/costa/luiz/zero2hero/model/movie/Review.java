package com.costa.luiz.zero2hero.model.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Author author;
    private String review;
    private boolean archived;
    @ManyToOne
    private Movie movie;
}
