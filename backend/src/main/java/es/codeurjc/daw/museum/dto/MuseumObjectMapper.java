package es.codeurjc.daw.museum.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import es.codeurjc.daw.museum.model.MuseumObject;

@Mapper(componentModel = "spring")
public interface MuseumObjectMapper {

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "notes", source = "objectNotes") 
    @Mapping(target = "isFavourite", ignore = true)   
    @Mapping(target = "isSeen", ignore = true)
	MuseumObjectDTO toDTO(MuseumObject museumObject);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "objectNotes", source = "notes") 
    @Mapping(target = "image", ignore = true)
    MuseumObject toEntity (MuseumObjectDTO museumObjectDTO);
}
