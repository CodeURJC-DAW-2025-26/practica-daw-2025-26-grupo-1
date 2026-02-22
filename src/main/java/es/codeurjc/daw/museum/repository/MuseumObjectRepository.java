package es.codeurjc.daw.museum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.daw.museum.model.MuseumObject;

public interface MuseumObjectRepository extends JpaRepository<MuseumObject, Long> {

}