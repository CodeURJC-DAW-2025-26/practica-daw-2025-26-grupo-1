package es.codeurjc.daw.museum.dto;

import java.util.List;

public record MuseumObjectDTO(
    Long id,
    String objectName,
    String groupName,
    String technicalData,
    String description,
    String type,
    String category,
    List <NoteDTO> notes,
    boolean isSeen,
    ImageDTO image
) {}
