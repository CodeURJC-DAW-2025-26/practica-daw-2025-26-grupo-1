package es.codeurjc.daw.museum.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import java.sql.SQLException;
import javax.sql.rowset.serial.SerialBlob;

import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.model.Image;
import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.repository.MuseumObjectRepository;
import es.codeurjc.daw.museum.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Service
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MuseumObjectRepository objectRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*@EventListener(ApplicationReadyEvent.class)
    public void init() throws IOException, URISyntaxException, SQLException {
        // Create only demo users - the app will have data from other sources
        if (userRepository.count() == 0) {
            userRepository.save(new User("user", passwordEncoder.encode("pass"), List.of("USER")));
            userRepository.save(new User("admin", passwordEncoder.encode("adminpass"), List.of("USER", "ADMIN")));
        }
    }*/

    @PostConstruct
    public void init() throws IOException, URISyntaxException {
        if (userRepository.count() == 0) {
            userRepository.save(new User("user", passwordEncoder.encode("pass"), List.of("USER")));
		userRepository.save(new User("admin", passwordEncoder.encode("adminpass"), List.of("USER", "ADMIN")));
        }
    }
}
