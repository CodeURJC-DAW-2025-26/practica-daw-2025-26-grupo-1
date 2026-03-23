package es.codeurjc.daw.museum.dto;

public record NoteDTO (
    Long id,
    String text,
    UserDTO user,
    MuseumObjectDTO museumObject
) {}
