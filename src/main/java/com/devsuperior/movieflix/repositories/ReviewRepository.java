package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.projections.ReviewProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(nativeQuery = true, value = """
    SELECT tb_review.id,tb_review.text,tb_review.movie_id,tb_review.user_id, tb_user.name, tb_user.email FROM TB_REVIEW
    INNER JOIN tb_user ON tb_review.user_id = tb_user.id
    WHERE tb_review.movie_id = :movieId
    """)
    List<ReviewProjection> searchReviewByMovieId(Long movieId);
}
