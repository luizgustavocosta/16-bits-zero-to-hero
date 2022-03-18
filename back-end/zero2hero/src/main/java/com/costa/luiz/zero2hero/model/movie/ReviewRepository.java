package com.costa.luiz.zero2hero.model.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "select reviews0_.author_id as author_i1_1_0_, reviews0_.reviews_id as reviews_2_1_0_, review1_.id as id1_5_1_, review1_.archived as archived2_5_1_, review1_.author_id as author_i4_5_1_, review1_.movie_id as movie_id5_5_1_, review1_.review as review3_5_1_, author2_.id as id1_0_2_, author2_.name as name2_0_2_ from authors_reviews reviews0_ inner join reviews review1_ on reviews0_.reviews_id=review1_.id left outer join authors author2_ on " +
            "review1_.author_id=author2_.id where reviews0_.author_id=?1", nativeQuery = true)
    Review findTheReviewBy(Long id);
}
