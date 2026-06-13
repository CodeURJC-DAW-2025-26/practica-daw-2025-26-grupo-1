package es.codeurjc.daw.museum.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import es.codeurjc.daw.museum.model.User;

@Mapper(componentModel = "spring", uses={ImageMapper.class})
public interface UserMapper {

    
    @Mapping(target = "seen", ignore = true)
    @Mapping(target = "userImage", source = "userImage")
    UserDTO toDTO(User user);

    @Mapping(target = "userImage", source = "userImage")
    UserBasicDTO toBasicDTO(User user);

    @Mapping(target = "userImage", source = "userImage")
    User toEntity(UserDTO userDTO);
}