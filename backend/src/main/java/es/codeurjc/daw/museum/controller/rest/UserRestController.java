package es.codeurjc.daw.museum.controller.rest;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
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

import es.codeurjc.daw.museum.dto.MuseumObjectBasicDTO;
import es.codeurjc.daw.museum.dto.MuseumObjectDTO;
import es.codeurjc.daw.museum.dto.MuseumObjectMapper;
import es.codeurjc.daw.museum.dto.NoteDTO;
import es.codeurjc.daw.museum.dto.UserBasicDTO;
import es.codeurjc.daw.museum.dto.UserDTO;
import es.codeurjc.daw.museum.dto.UserMapper;
import es.codeurjc.daw.museum.dto.UserStatisticsDTO;
import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.model.Note;
import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.service.MuseumObjectService;
import es.codeurjc.daw.museum.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/users")
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
    public ResponseEntity<UserBasicDTO> register(@RequestBody UserDTO user) { 

        User newUser = userService.registerNewUser(userMapper.toEntity(user));
        URI location = fromCurrentRequest().path("/me").build().toUri();
        return ResponseEntity.created(location).body(userMapper.toBasicDTO(newUser)); 
    }

    @PutMapping("/profile")
    public ResponseEntity<UserBasicDTO> updateProfile(@RequestBody UserDTO userModifyDTO, Principal principal)
            throws IOException, SQLException {

        User updatedUser = userService.editUser(principal.getName(), userMapper.toEntity(userModifyDTO), false, null);

        return ResponseEntity.ok(userMapper.toBasicDTO(updatedUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity <UserBasicDTO> updateUser (@PathVariable long id, @RequestBody UserDTO userDTO) throws IOException, SQLException {

        User userToEdit = userService.findById(id).orElseThrow();
        User updatedUser = userService.editUser(userToEdit.getName(), userMapper.toEntity(userDTO), false, null);

        return ResponseEntity.ok(userMapper.toBasicDTO(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserBasicDTO> deleteUser(@PathVariable long id) {
        User user = userService.findById(id).orElseThrow();
        userService.deleteUser(id);
        return ResponseEntity.ok(userMapper.toBasicDTO(user));
    }

    /*@DeleteMapping("/me")
    public ResponseEntity<UserBasicDTO> deleteMyAccount(Principal principal) {

        User user = userService.findByUsername(principal.getName()).orElseThrow();
        userService.deleteUser(user.getId());

        return ResponseEntity.ok(userMapper.toBasicDTO(user));
    }*/

    @PostMapping("/me/seen/{id}")
    public ResponseEntity<MuseumObjectBasicDTO> markSeen(@PathVariable Long id, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();

        if (principal == null) {
            throw new NoSuchElementException();
        }

        User user = userService.findByUsername(principal.getName()).orElseThrow();
        MuseumObject object = objectService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Objeto no encontrado"));

        MuseumObject seenObject = userService.markSeen(user, object);


        URI location = fromCurrentRequest().build().toUri();

        return ResponseEntity.created(location).body(objectMapper.toBasicDTO(seenObject));
    }

    @GetMapping("/me/statistics")
    public ResponseEntity<UserStatisticsDTO> getMyStats(Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserStatisticsDTO stats = userService.getUserStats(principal.getName());
        return ResponseEntity.ok(stats);
    }

    // List of all users
    @GetMapping("/all")
    public ResponseEntity<Page<UserBasicDTO>> getAllUsers(Pageable pageable) {
        Page<User> users = userService.findAll(pageable);

        Page<UserBasicDTO> usersDTOs = users.map(userMapper::toBasicDTO);

        return ResponseEntity.ok(usersDTOs);
    }

}
