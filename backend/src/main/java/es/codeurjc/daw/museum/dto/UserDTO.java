package es.codeurjc.daw.museum.dto;

import java.util.List;

public record UserDTO (
    Long id,
    String name,
    String password,
    List <String> roles,
    List <ElementDTO> favourites,
    List <ElementDTO> seen
){}
