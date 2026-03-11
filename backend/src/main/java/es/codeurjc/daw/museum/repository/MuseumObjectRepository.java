package es.codeurjc.daw.museum.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import es.codeurjc.daw.museum.model.MuseumObject;

public interface MuseumObjectRepository extends JpaRepository<MuseumObject, Long> {

    List <MuseumObject> findByObjectName(String name);
    //List <MuseumObject> findByType(String type);
    Page <MuseumObject> findByType(String type, Pageable pageable);
    List <MuseumObject> findByCategory(String category);

    //@Query("SELECT DISTINCT m.type FROM MuseumObject m")
    //List<String> findDistinctTypes()

    //@Query("SELECT object FROM museum_object WHERE category = museum_object.category  OR category=NULL OR category=" ")
    //List<MuseumObject> findDistinctTypes();

    /*@Query("SELECT numberObjects FROM museum_object WHERE )
    int countByType(String type);*/

}