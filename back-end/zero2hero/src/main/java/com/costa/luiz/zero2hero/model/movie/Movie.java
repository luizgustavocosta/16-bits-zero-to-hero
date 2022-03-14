package com.costa.luiz.zero2hero.model.movie;

import com.costa.luiz.zero2hero.model.genre.Genre;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    @ManyToMany(cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private List<Genre> genre;

    @Enumerated(EnumType.STRING)
    private Rating classification;

//    @OneToMany(
//            mappedBy = "review",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    @ToString.Exclude
//    private List<Review> comments;

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
