package es.codeurjc.daw.museum.dto;

public record MuseumObjectBasicDTO(
    Long id,
    String objectName,
    String groupName,
    String technicalData,
    String description,
    String category,
    boolean isSeen
) {}