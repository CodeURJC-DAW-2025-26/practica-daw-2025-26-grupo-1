package es.codeurjc.daw.museum.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.daw.museum.model.MuseumUser;

public interface MuseumUserRepository extends JpaRepository<MuseumUser, Long> {

    Optional<MuseumUser> findByName(String name);

}
