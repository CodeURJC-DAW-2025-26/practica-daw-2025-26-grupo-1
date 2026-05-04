package es.codeurjc.daw.museum.dto;

public record CategoryStatsDTO (
    String categoryName,
    int seenCount,
    int totalInCategory,
    double percentage
){}
