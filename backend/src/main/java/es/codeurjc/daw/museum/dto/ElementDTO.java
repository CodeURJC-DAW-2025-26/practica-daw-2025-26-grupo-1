package es.codeurjc.daw.museum.dto;

public record ElementDTO(
    Long id,
    String nameElement,
    ImageDTO objectSectionImage,
    String category,
    String goToElement
) {}