package com.devsuperior.movieflix.projections;

public interface ReviewProjection {

    Long getId();
    String getText();
    Long getMovie_Id();
    Long getUser_Id();
    String getName();
    String getEmail();
}
