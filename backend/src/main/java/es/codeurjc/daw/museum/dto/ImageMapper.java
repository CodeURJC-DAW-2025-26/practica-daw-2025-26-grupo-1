package es.codeurjc.daw.museum.dto;

import org.mapstruct.Mapper;

import es.codeurjc.daw.museum.model.Image;

@Mapper(componentModel = "spring")
public interface ImageMapper {

	ImageDTO toDTO(Image image);
}
