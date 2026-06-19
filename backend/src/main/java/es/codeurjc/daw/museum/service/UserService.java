package es.codeurjc.daw.museum.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import es.codeurjc.daw.museum.dto.CategoryStatsDTO;
import es.codeurjc.daw.museum.dto.UserStatisticsDTO;
import es.codeurjc.daw.museum.model.Image;
import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ImageService imageService;

    @Autowired
    private MuseumObjectService objectService;

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByName(username);
    }

    public boolean exist(Long id) {
        return userRepository.existsById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User registerNewUser(User user, MultipartFile imageField) throws IOException {
        if (userRepository.findByName(user.getName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya existe.");
        }
        user.setEncodedPassword(passwordEncoder.encode(user.getEncodedPassword()));
        user.setRoles(List.of("USER"));

        if (imageField != null && !imageField.isEmpty()) {
            user.setUserImage(imageService.createImage(imageField.getInputStream()));
        }

        return userRepository.save(user);
    }

    public User editUser(String username, String authUsername, User userModify, boolean removeImage,
            MultipartFile imageField)
            throws IOException, SQLException {

        User userToEdit = userRepository.findByName(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        User currentUser = userRepository.findByName(authUsername)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sesión inválida"));

        boolean isAdmin = currentUser.getRoles().contains("ADMIN");

        if (!currentUser.getId().equals(userToEdit.getId()) && !isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "No tienes permiso para editar el perfil de otro usuario");
        }

        userToEdit.setName(userModify.getName());

        if (imageField != null && !imageField.isEmpty()) {
            if (userToEdit.getUserImage() != null) {
                userToEdit.getUserImage().getId();
            }

            userToEdit.setUserImage(imageService.createImage(imageField.getInputStream()));

            userRepository.save(userToEdit);


        } else if (removeImage && userToEdit.getUserImage() != null) {
            Long imageId = userToEdit.getUserImage().getId();
            userToEdit.setUserImage(null);
            userRepository.save(userToEdit);
            imageService.deleteImage(imageId);
        }

        return userRepository.save(userToEdit);
    }


    public User addImageToUser(long id, Image image) {
		User user = userRepository.findById(id).orElseThrow();
		user.setUserImage(image);
		return userRepository.save(user);
	}



    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        userRepository.delete(user);

    }

    public User getUserProfile(String username) {
        return userRepository.findByName(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    public MuseumObject markSeen(User user, MuseumObject object) {
        if (!user.getSeen().contains(object)) {
            user.getSeen().add(object);
            userRepository.save(user);
        }
        return object;
    }

    public UserStatisticsDTO getUserStats(String username) {
        User user = userRepository.findByName(username).orElseThrow();
        List<String> sections = List.of("peces", "insectos", "fosiles", "arte");

        List<CategoryStatsDTO> categoryStats = sections.stream().map(section -> {

            int seen = (int) user.getSeen().stream().filter(obj -> obj.getType().equalsIgnoreCase(section)).count();

            int totalInSec = (int) objectService.countByType(section);

            double percentage;

            if (totalInSec > 0) {
                percentage = (seen * 100.0) / totalInSec;
            } else {
                percentage = 0.0;
            }

            percentage = Math.round(percentage * 100.0) / 100.0;

            return new CategoryStatsDTO(section, seen, totalInSec, percentage);
        }).toList();

        Map<String, Long> globalTotals = new HashMap<>();
        globalTotals.put("peces", objectService.countByType("peces"));
        globalTotals.put("insectos", objectService.countByType("insectos"));
        globalTotals.put("fosiles", objectService.countByType("fosiles"));
        globalTotals.put("arte", objectService.countByType("arte"));

        return new UserStatisticsDTO(
                user.getName(),
                (long) user.getSeen().size(),
                categoryStats,
                globalTotals);

    }

}