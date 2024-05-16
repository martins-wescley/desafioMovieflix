package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Transactional(readOnly = true)
    public MovieDetailsDTO findById(Long id) {
        Movie entity = movieRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Entity not found"));
        return new MovieDetailsDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<MovieCardDTO> findAllPaged(String genreId, Pageable pageable) {
        String[] vet = genreId.split(",");
        List<String> list = Arrays.asList(vet);
        List<Long> genreIds = Arrays.asList();

        if (!"0".equals(genreId)) {
            genreIds = list.stream().map(x -> Long.parseLong(x)).toList();
        }

        Page<MovieProjection> projection = movieRepository.searchMoviesByGenre(genreIds, pageable);

        return projection.map(x-> new MovieCardDTO(x));
    }
}
