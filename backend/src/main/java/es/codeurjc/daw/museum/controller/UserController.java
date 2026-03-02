package es.codeurjc.daw.museum.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.service.MuseumObjectService;
import es.codeurjc.daw.museum.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MuseumObjectService objectService;

    @GetMapping("/users/{id}")
    public String viewProfile(@PathVariable Long id, Model model) {
        User user = userService.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);
        return "profile-page";
    }

    @PostMapping("/objects/{id}/favorite")
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

    @GetMapping("/objects/{id}")
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

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("museumHeroImage", "/assets/images/interior-museo.png");
        return "main-page";
    }
}