package es.codeurjc.daw.museum.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import es.codeurjc.daw.museum.model.MuseumObject;

@Mapper(componentModel = "spring")
public interface ElementMapper {

    @Mapping(source = "objectName", target = "nameElement")
    @Mapping(source = "image", target = "objectSectionImage") 
    @Mapping(target = "category", ignore = true) 
    @Mapping(target = "goToElement", ignore = true)
	ElementDTO toDTO(MuseumObject museumObject);

}