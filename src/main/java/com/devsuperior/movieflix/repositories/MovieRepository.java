package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(nativeQuery = true, value = """
            SELECT tb_movie.id, tb_movie.title, tb_movie.sub_title, tb_movie.movie_year, tb_movie.img_url, tb_genre.name FROM tb_movie
            INNER JOIN tb_genre ON tb_movie.genre_id = tb_genre.id
            WHERE (:genreIds IS NULL OR tb_genre.id IN :genreIds)
            ORDER BY tb_movie.title
    """, countQuery = """
            SELECT COUNT(*) FROM (
            SELECT tb_movie.id, tb_movie.title, tb_movie.sub_title, tb_movie.movie_year, tb_movie.img_url, tb_genre.name FROM tb_movie
            INNER JOIN tb_genre ON tb_movie.genre_id = tb_genre.id
            WHERE (:genreIds IS NULL OR tb_genre.id IN :genreIds)) AS tb_result
    """)
    Page<MovieProjection> searchMoviesByGenre(List<Long> genreIds, Pageable pageable);
}
