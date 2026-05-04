package es.codeurjc.daw.museum.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.codeurjc.daw.museum.model.MuseumObject;

public interface MuseumObjectRepository extends JpaRepository<MuseumObject, Long> {

    @Query("SELECT o from MuseumObject o WHERE o.objectName = :name")
    List<MuseumObject> findByObjectName(String name);

    Page<MuseumObject> findByType(String type, Pageable pageable);

    List<MuseumObject> findByType(String category);

    List<MuseumObject> findByCategory(String category);


    Page<MuseumObject> findByTypeOrderByIdAsc(String type, Pageable pageable);
    Page<MuseumObject> findByCategoryOrderByIdAsc(String category, Pageable pageable);

    List<MuseumObject> findByTypeAndObjectNameContainingIgnoreCase(String type, String objectName);

    Page<MuseumObject> findByTypeAndCategoryOrderByIdAsc(String type, String category, Pageable pageable);

    Page<MuseumObject> findByObjectNameContainingIgnoreCase(String objectName, Pageable pageable);

    long countByTypeIgnoreCase(String type);
     

}