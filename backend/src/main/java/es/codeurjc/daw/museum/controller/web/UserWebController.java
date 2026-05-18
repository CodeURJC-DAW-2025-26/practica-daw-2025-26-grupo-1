package es.codeurjc.daw.museum.controller.web;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import es.codeurjc.daw.museum.model.Image;
import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.service.ImageService;
import es.codeurjc.daw.museum.service.MuseumObjectService;
import es.codeurjc.daw.museum.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserWebController {

    @Autowired
    private UserService userService;

    @Autowired
    private MuseumObjectService objectService;

    @Autowired
    private ImageService imageService;

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request) {

        // Retrieves authenticated user and adds common attributes for all views
        Principal principal = request.getUserPrincipal();

        if (principal != null) {
            model.addAttribute("logged", true);
            model.addAttribute("userName", principal.getName());
            model.addAttribute("admin", request.isUserInRole("ADMIN"));

            // Loads full user entity from database
            Optional<User> userOpt = userService.findByUsername(principal.getName());

            if (userOpt.isPresent()) {
                User user = userOpt.get();
                // Adds user object to model (used for navbar/profile rendering)
                model.addAttribute("user", user);
            }

        } else {
            // Marks user as not authenticated
            model.addAttribute("logged", false);
        }
    }

    @GetMapping("/")
    public String index(Model model) {
        // Displays main landing page
        model.addAttribute("museumHeroImage", "/assets/images/interior-museo.png");
        return "main-page";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model, Principal principal) {

        // Loads current user's profile information
        User user = userService.findByUsername(principal.getName()).get();
        model.addAttribute("user", user);
        model.addAttribute("museumRoomImage", "/assets/images/sala-del-museo.png");

        return "profile-page";
    }

    @GetMapping("/profile/{id}")
    public String viewOtherProfile(@PathVariable long id, Model model, Principal principal) {

        // Loads another user's profile by ID
        User user = userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        model.addAttribute("user", user);
        model.addAttribute("username", user.getName());
        model.addAttribute("roles", user.getRoles());
        model.addAttribute("museumRoomImage", "/assets/images/sala-del-museo.png");

        // Checks if current user is admin
        if (principal != null) {
            User loggedUser = userService.findByUsername(principal.getName()).get();
            model.addAttribute("isAdmin", loggedUser.getRoles().contains("ADMIN"));
        }

        return "profile-page";
    }

    @PostMapping("/edit-profile")
    public String editUserProcess(Model model, User userModify, boolean removeImage, MultipartFile imageField,
            Principal principal)
            throws IOException, SQLException {

        // Updates user profile data
        // Preserves roles and ID from authenticated user
        User user = userService.findByUsername(principal.getName()).get();

        userModify.setId(user.getId());
        userModify.setRoles(user.getRoles());

        // Handles profile image update
        updateImageUser(userModify, removeImage, imageField);

        userService.saveUser(userModify);

        return "redirect:/welcome-user";
    }

    private void updateImageUser(User user, boolean removeImage, MultipartFile imageField)
            throws IOException, SQLException {

        // Handles image upload, replacement or deletion
        // Keeps previous image if no changes are made
        if (!imageField.isEmpty()) {
            User dbUser = userService.findById(user.getId()).orElseThrow();

            if (dbUser.getUserImage() == null) {
                Image image = imageService.createImage(imageField.getInputStream());
                user.setUserImage(image);
            } else {
                Image image = imageService.replaceImageFile(dbUser.getUserImage().getId(), imageField.getInputStream());
                user.setUserImage(image);
            }
        } else {
            if (removeImage) {
                if (user.getUserImage() != null) {
                    imageService.deleteImage(user.getUserImage().getId());
                    user.setUserImage(null);
                }
            } else {
                // Maintain the same image loading it before updating the user
                User dbUser = userService.findById(user.getId()).orElseThrow();
                user.setUserImage(dbUser.getUserImage());
            }
        }
    }

    @PostMapping("/remove-profile-image")
    public String removeProfileImage(Principal principal) {

        // Removes user's profile image if exists
        User user = userService.findByUsername(principal.getName()).get();

        if (user.getUserImage() != null) {
            user.setUserImage(null);
            userService.saveUser(user);
        }

        return "redirect:/profile";
    }

}