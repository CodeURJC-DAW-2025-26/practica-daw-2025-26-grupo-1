package es.codeurjc.daw.museum.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import es.codeurjc.daw.museum.model.MuseumObject;

@Mapper(componentModel = "spring", uses={ImageMapper.class, NoteMapper.class})
public interface MuseumObjectMapper {

    MuseumObjectBasicDTO toBasicDTO(MuseumObject museumObject);

    @Mapping(target = "category", source = "category")
    @Mapping(target = "notes", source = "objectNotes")  
    @Mapping(target = "isSeen", ignore = true)
	MuseumObjectDTO toDTO(MuseumObject museumObject);

    @Mapping(target = "category", source = "category")
    @Mapping(target = "objectNotes", source = "notes") 
    @Mapping(target = "image", ignore = true)
    MuseumObject toEntity (MuseumObjectDTO museumObjectDTO);
}
