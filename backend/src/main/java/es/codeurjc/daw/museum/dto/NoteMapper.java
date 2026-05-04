package es.codeurjc.daw.museum.dto;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import es.codeurjc.daw.museum.model.Note;

@Mapper(componentModel = "spring", uses = {UserMapper.class, MuseumObjectMapper.class})
public interface NoteMapper {

	NoteDTO toDTO(Note note);

    NoteBasicDTO toBasicDTO(Note note);

    List <NoteDTO> toDTOs (Collection <Note> notes);

    
    @Mapping(target = "user.seen", ignore = true)
    Note toEntity(NoteDTO noteDTO);

}