package es.codeurjc.daw.museum.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.daw.museum.model.Image;
import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.service.ImageService;
import es.codeurjc.daw.museum.service.MuseumObjectService;
import es.codeurjc.daw.museum.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MuseumObjectService objectService;

    @Autowired
    private ImageService imageService;


    public static class Progress {
        private String sectionName;
        private int percentage;

        public Progress(String sectionName, int percentage) {
            this.sectionName = sectionName;
            this.percentage = percentage;
        }

        public String getSectionName() {
            return sectionName;
        }

        public int getPercentage() {
            return percentage;
        }
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("museumHeroImage", "/assets/images/interior-museo.png");
        return "main-page";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName()).get();
        model.addAttribute("user", user);
        model.addAttribute("museumRoomImage", "/assets/images/sala-del-museo.png");
        
        return "profile-page";
    }

    /*@PostMapping("")
    public String editBookProcess(HttpSession session, User user, boolean removeImage, MultipartFile imageField)
			throws IOException, SQLException {

		updateImage(user, removeImage, imageField);

		userService.save(user);

		model.addAttribute("userId", user.getId());

		return "redirect:/users/" + user.getId();
	}*/


    @PostMapping("/edit-profile")
	public String editUserProcess(Model model, User userModify, boolean removeImage, MultipartFile imageField, Principal principal)
			throws IOException, SQLException {

        User user = userService.findByUsername(principal.getName()).get();
        
        userModify.setId(user.getId());
        userModify.setRoles(user.getRoles());

		updateImageUser(userModify, removeImage, imageField);

		userService.saveUser(userModify);

        return "redirect:/welcome-user";
	}


    private void updateImageUser(User user, boolean removeImage, MultipartFile imageField)
			throws IOException, SQLException {

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
				// Maintain the same image loading it before updating the book
				User dbUser = userService.findById(user.getId()).orElseThrow();
				user.setUserImage(dbUser.getUserImage());
			}
		}
	}

    @PostMapping("/remove-profile-image")
    public String removeProfileImage(Principal principal) {

        User user = userService.findByUsername(principal.getName()).get();

        if (user.getUserImage() != null) {
            user.setUserImage(null);
            userService.saveUser(user);
        }

        return "redirect:/profile";
    }

    /*@GetMapping("/statistics")
    public String viewStatistics(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName()).get();
        model.addAttribute("user", user);

        List<Progress> progressList = new ArrayList<>();

        progressList = List.of(
            new Progress("Peces", 80),
            new Progress("Insectos", 50),
            new Progress("Fósiles", 30),
            new Progress("Obras de arte", 70)
        );

        model.addAttribute("progress", progressList);

        return "data-graphics";
    }*/














    /*@PostMapping("/objects/{id}/favorite")
    public String addFavorite(@PathVariable long id, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = userService.findByUsername(principal.getName()).get();
        MuseumObject object = objectService.findById(id).orElseThrow();
        userService.addFavourite(user, object);
        // Volver a la ficha del objeto
        return "redirect:/objects/" + id;
    }

    @PostMapping("/objects/{id}/unfavorite")
    public String removeFavorite(@PathVariable long id, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = userService.findByUsername(principal.getName()).get();
        MuseumObject object = objectService.findById(id).orElseThrow();
        userService.removeFavourite(user, object);
        return "redirect:/objects/" + id;
    }

    @PostMapping("/objects/{id}/seen")
    public String markSeen(@PathVariable long id, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = userService.findByUsername(principal.getName()).get();
        MuseumObject object = objectService.findById(id).orElseThrow();
        userService.markSeen(user, object);
        return "redirect:/objects/" + id;
    }

    /*@GetMapping("/objects/{id}")
    public String viewObject(@PathVariable long id, Model model, Principal principal) {
        MuseumObject object = objectService.findById(id)
                .orElseThrow(() -> new RuntimeException("Object not found"));
        model.addAttribute("object", object);

        String userType = "anonymous";
        User user = null;
        if (principal != null) {
            user = userService.findByUsername(principal.getName()).get();
            model.addAttribute("user", user);
            if (user.getRoles().contains("ADMIN")) {
                userType = "admin";
            } else {
                userType = "registered";
            }
        }

        // URL de “Volver” según tipo de usuario
        String backUrl;
        if ("admin".equals(userType)) {
            backUrl = "/sections/admin";
        } else if ("registered".equals(userType)) {
            backUrl = "/sections/registered";
        } else {
            backUrl = "/sections/anonymous";
        }
        model.addAttribute("backUrl", backUrl);

        // Escoger la plantilla según tipo de usuario
        if ("admin".equals(userType)) {
            return "edit-information-page"; // admin
        } else if ("registered".equals(userType)) {
            return "informative-page"; // registrado
        } else {
            return "informative-anonymous-page"; // anónimo
        }
    } 
        */

}