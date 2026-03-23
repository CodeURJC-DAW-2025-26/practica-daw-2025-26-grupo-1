package es.codeurjc.daw.museum.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import es.codeurjc.daw.museum.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "favourites", ignore = true)
    @Mapping(target = "seen", ignore = true)
    UserDTO toDTO(User user);

    /*User toEntity(UserDTO userDTO);*/
}