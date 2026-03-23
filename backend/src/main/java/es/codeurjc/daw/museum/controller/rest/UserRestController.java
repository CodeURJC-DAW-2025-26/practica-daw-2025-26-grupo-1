package es.codeurjc.daw.museum.controller.rest;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import es.codeurjc.daw.museum.dto.MuseumObjectDTO;
import es.codeurjc.daw.museum.dto.MuseumObjectMapper;
import es.codeurjc.daw.museum.dto.UserDTO;
import es.codeurjc.daw.museum.dto.UserMapper;
import es.codeurjc.daw.museum.dto.UserStatisticsDTO;
import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.service.MuseumObjectService;
import es.codeurjc.daw.museum.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/v1/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private MuseumObjectService objectService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MuseumObjectMapper objectMapper;

    @GetMapping("/me")
    public UserDTO me(HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();

        if (principal != null) {
            User user = userService.findByUsername(principal.getName()).orElseThrow();
            return userMapper.toDTO(user);
        } else {
            throw new NoSuchElementException();
        }
        
    }

    @PostMapping("/")
    public ResponseEntity<UserDTO> register(@RequestBody User user) {

        User newUser = userService.registerNewUser(user);
        URI location = fromCurrentRequest().path("/me").build().toUri();
        return ResponseEntity.created(location).body(userMapper.toDTO(newUser));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserDTO> updateProfile(@RequestBody User userModify, Principal principal) throws IOException, SQLException {
        
        User updatedUser = userService.editUser(principal.getName(), userModify, false, null);
        return ResponseEntity.ok(userMapper.toDTO(updatedUser));
    }

    @DeleteMapping("/no-account")
    public ResponseEntity<UserDTO> deleteMyAccount(Principal principal) {

        User user = userService.findByUsername(principal.getName()).orElseThrow();
        userService.deleteUser(user.getId());

        return ResponseEntity.ok(userMapper.toDTO(user));
    }

    @PostMapping("/me/favourites/{id}") 
    public ResponseEntity<MuseumObjectDTO> addFavourite(@PathVariable Long id, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();

        if (principal == null) {
            throw new NoSuchElementException();
        }

        User user = userService.findByUsername(principal.getName()).orElseThrow();
        MuseumObject object = objectService.findById(id).orElseThrow();

        MuseumObject favObject = userService.addFavourite(user, object);

        return ResponseEntity.ok(objectMapper.toDTO(favObject));
    }

    @DeleteMapping("/me/favourites/{id}") 
    public ResponseEntity<MuseumObjectDTO> removeFavourite(@PathVariable Long id, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();

        if (principal == null) {
            throw new NoSuchElementException();
        }

        User user = userService.findByUsername(principal.getName()).orElseThrow();
        MuseumObject object = objectService.findById(id).orElseThrow();

        MuseumObject notFavObject = userService.removeFavourite(user, object);

        return ResponseEntity.ok(objectMapper.toDTO(notFavObject));
    }

    @PostMapping("/me/seen/{id}")
    public ResponseEntity<MuseumObjectDTO> markSeen(@PathVariable Long id, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();

        if (principal == null) {
            throw new NoSuchElementException();
        }

        User user = userService.findByUsername(principal.getName()).orElseThrow();
        MuseumObject object = objectService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Objeto no encontrado"));

        MuseumObject seenObject = userService.markSeen(user, object);

        return ResponseEntity.ok(objectMapper.toDTO(seenObject));
    }

    @GetMapping("/me/statistics")
    public UserStatisticsDTO getMyStats(Principal principal) {

        UserStatisticsDTO stats = userService.getUserStats(principal.getName());
        return stats;
    }
    

}
