package es.codeurjc.daw.museum.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByName(username);
    }

    public boolean exist(Long id) {
        return userRepository.existsById(id);
    }

    public User saveUser(User user) {
        if (user.getEncodedPassword() != null) {
            user.setEncodedPassword(passwordEncoder.encode(user.getEncodedPassword()));
        }
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }

    public void addFavourite(User user, MuseumObject object) {
        if (!user.getFavourites().contains(object)) {
            user.getFavourites().add(object);
            userRepository.save(user);
        }
    }

    public void removeFavourite(User user, MuseumObject object) {
        if (user.getFavourites().contains(object)) {
            user.getFavourites().remove(object);
            userRepository.save(user);
        }
    }

    public void markSeen(User user, MuseumObject object) {
        if (!user.getSeen().contains(object)) {
            user.getSeen().add(object);
            userRepository.save(user);
        }
    }

    

}