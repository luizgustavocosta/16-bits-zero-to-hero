package com.costa.luiz.zero2hero.model.movie;

import com.costa.luiz.zero2hero.model.genre.Genre;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int year;
    private int duration;
    private String country;
    private String language;
    @CreationTimestamp
    private LocalDateTime lastUpdate;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
    })
    @JoinTable(name = "movies_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @ToString.Exclude
    @Builder.Default
    private Set<Genre> genre = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Classification classification;

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "movie")
    @ToString.Exclude
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();
    private Double rating;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Movie movie = (Movie) o;
        return id != null && Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}