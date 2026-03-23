package es.codeurjc.daw.museum.dto;

import java.util.List;

public record UserStatisticsDTO (
    String userName,
    Long globalTotalSeen,
    Long globalTotalFavourites,
    List <CategoryStatsDTO> statsByCategory
) {}

