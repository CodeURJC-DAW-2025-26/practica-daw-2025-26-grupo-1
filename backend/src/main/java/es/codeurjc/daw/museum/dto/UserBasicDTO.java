package es.codeurjc.daw.museum.dto;

import java.util.List;

public record UserBasicDTO (
    Long id,
    String name,
    List <String> roles
) {}
