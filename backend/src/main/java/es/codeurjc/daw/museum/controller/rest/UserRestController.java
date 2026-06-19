package es.codeurjc.daw.museum.controller.rest;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.sql.SQLException;
import java.util.NoSuchElementException;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import es.codeurjc.daw.museum.dto.ImageDTO;
import es.codeurjc.daw.museum.dto.ImageMapper;
import es.codeurjc.daw.museum.dto.MuseumObjectBasicDTO;
import es.codeurjc.daw.museum.dto.MuseumObjectMapper;
import es.codeurjc.daw.museum.dto.UserBasicDTO;
import es.codeurjc.daw.museum.dto.UserDTO;
import es.codeurjc.daw.museum.dto.UserMapper;
import es.codeurjc.daw.museum.dto.UserStatisticsDTO;
import es.codeurjc.daw.museum.model.Image;
import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.service.ImageService;
import es.codeurjc.daw.museum.service.MuseumObjectService;
import es.codeurjc.daw.museum.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private MuseumObjectService objectService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MuseumObjectMapper objectMapper;

    @Autowired
    private ImageMapper imageMapper;

    @GetMapping("/me")
    public UserBasicDTO me(HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();

        if (principal != null) {
            User user = userService.findByUsername(principal.getName()).orElseThrow();
            
            if (user.getSeen() != null) {
                user.getSeen().size(); 
            }
            
            return userMapper.toBasicDTO(user);
        } else {
            throw new NoSuchElementException();
        }

    }

    @PostMapping("/")
    public ResponseEntity<UserBasicDTO> register(@RequestBody UserDTO userDTO) throws IOException {

        User userEntity = userMapper.toEntity(userDTO);

        User newUser = userService.registerNewUser(userEntity, null);

        URI location = fromCurrentRequest().path("/me").build().toUri();
        return ResponseEntity.created(location).body(userMapper.toBasicDTO(newUser));
    }

    /*PutMapping("/me")
    public ResponseEntity<UserBasicDTO> updateProfile(
            @RequestBody UserBasicDTO userModifyDTO, 
            Principal principal) throws IOException, SQLException {

        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No autenticado");
        }

        User existingUser = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        existingUser.setName(userModifyDTO.name());

        User updatedUser = userService.editUser(
                principal.getName(), 
                principal.getName(),
                existingUser, 
                false, 
                null
        );

        return ResponseEntity.ok(userMapper.toBasicDTO(updatedUser));
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<UserBasicDTO> updateUser(
            @PathVariable long id,
            @RequestBody UserBasicDTO userDTO, 
            Principal principal) throws IOException, SQLException {

        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No autenticado");
        }

        User userToEdit = userService.findById(id);

        String nombreAntiguo = userToEdit.getName();

        userToEdit.setName(userDTO.name());

        User updatedUser = userService.editUser(
                nombreAntiguo, 
                principal.getName(),
                userToEdit, 
                false, 
                null
        );

        return ResponseEntity.ok(userMapper.toBasicDTO(updatedUser));
    }



    @PutMapping("/{id}/media")
    public ResponseEntity<ImageDTO> updateUserImage(@PathVariable long id, @RequestParam MultipartFile imageFile)
            throws IOException {

        if (imageFile.isEmpty())
            throw new IllegalArgumentException();

       User user = userService.findById(id);
        Image oldImage = user.getUserImage();

        Image newImage = imageService.createImage(imageFile.getInputStream());
        userService.addImageToUser(id, newImage);

        if (oldImage != null) {
            imageService.deleteImage(oldImage.getId());
        }

        return ResponseEntity.ok(imageMapper.toDTO(newImage));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<UserBasicDTO> deleteUser(@PathVariable long id) {
        User user = userService.findById(id);
        userService.deleteUser(id);
        return ResponseEntity.ok(userMapper.toBasicDTO(user));
    }

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

        MuseumObjectBasicDTO originalDTO = objectMapper.toBasicDTO(seenObject);

        MuseumObjectBasicDTO responseDTO = new MuseumObjectBasicDTO(
                originalDTO.id(),
                originalDTO.objectName(),
                originalDTO.groupName(),
                originalDTO.technicalData(),
                originalDTO.description(),
                originalDTO.type(),
                originalDTO.category(),
                true,
                originalDTO.image());

        return ResponseEntity.created(location).body(responseDTO);
    }


    //endpoint para comprobar objetos vistos por un usuario

    @GetMapping("/me/statistics")
    public ResponseEntity<UserStatisticsDTO> getMyStats(Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserStatisticsDTO stats = userService.getUserStats(principal.getName());
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserBasicDTO> getUser(@PathVariable long id) {
        User user = userService.findById(id);
                
        return ResponseEntity.ok(userMapper.toBasicDTO(user));
    }

    // List of all users
    @GetMapping("/")
    public ResponseEntity<Page<UserBasicDTO>> getAllUsers(Pageable pageable) {
        Page<User> users = userService.findAll(pageable);

        Page<UserBasicDTO> usersDTOs = users.map(userMapper::toBasicDTO);

        return ResponseEntity.ok(usersDTOs);
    }

}
