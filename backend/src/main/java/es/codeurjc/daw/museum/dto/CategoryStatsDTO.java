package es.codeurjc.daw.museum.dto;

public record CategoryStatsDTO (
    String categoryName,
    int seenCount,
    int favouriteCount,
    int totalInCategory,
    double percentage
){}
