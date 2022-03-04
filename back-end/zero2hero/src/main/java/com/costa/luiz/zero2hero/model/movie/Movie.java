package com.costa.luiz.zero2hero.model.movie;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@RequiredArgsConstructor
@EqualsAndHashCode
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private int year;
    private String title;
    private String originalTitle;
    private int duration;
    private String country;
    private String language;
    private LocalDateTime createdAt;
    @OneToMany
    private List<Genre> genre;
    @Enumerated(EnumType.STRING)
    private Classification classification;
}
