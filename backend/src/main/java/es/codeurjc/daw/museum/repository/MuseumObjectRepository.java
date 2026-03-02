package es.codeurjc.daw.museum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import es.codeurjc.daw.museum.model.MuseumObject;

public interface MuseumObjectRepository extends JpaRepository<MuseumObject, Long> {

    List <MuseumObject> findByObjectName(String name);
    List <MuseumObject> findByType(String type);
    List <MuseumObject> findByCategory(String category);

    @Query("SELECT DISTINCT m.type FROM MuseumObject m")
    List<String> findDistinctTypes();

}