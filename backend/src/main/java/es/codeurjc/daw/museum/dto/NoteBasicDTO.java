package es.codeurjc.daw.museum.dto;

public record NoteBasicDTO (
    Long id,
    String text,
    UserBasicDTO user
) {}
