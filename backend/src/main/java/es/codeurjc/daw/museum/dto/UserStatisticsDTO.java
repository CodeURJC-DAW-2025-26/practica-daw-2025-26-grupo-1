package es.codeurjc.daw.museum.dto;

import java.util.List;
import java.util.Map;

public record UserStatisticsDTO (
    String userName,
    Long globalTotalSeen,
    List <CategoryStatsDTO> statsByCategory,

    Map <String, Long> globalTotals
) {}

