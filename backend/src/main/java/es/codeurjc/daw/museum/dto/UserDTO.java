package es.codeurjc.daw.museum.dto;

import java.util.List;

public record UserDTO (
    Long id,
    String name,
    String encodedPassword,
    List <String> roles,
    List <ElementDTO> seen,
    ImageDTO userImage
){}
