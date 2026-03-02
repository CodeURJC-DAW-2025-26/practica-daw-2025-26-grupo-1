package es.codeurjc.daw.museum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.daw.museum.model.Image;

public interface SectionRepository extends JpaRepository<Image, Long> {

}