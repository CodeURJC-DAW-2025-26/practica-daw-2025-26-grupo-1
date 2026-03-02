package es.codeurjc.daw.museum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.service.UserService;

@Controller
public class SessionController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("museumHeroImage", "/assets/images/interior-museo.png");
        return "log-in-page";
    }

    @GetMapping("/loginerror")
    public String loginerror(Model model) {
        model.addAttribute("errorMessage", "Usuario o contraseña incorrectos.");
        return "error-page";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("museumHeroImage", "/assets/images/interior-museo.png");
        model.addAttribute("user", new User());
        model.addAttribute("profileImage", "/assets/images/perfil-sin-foto.png");
        return "registration-page";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {

        if (userService.findByUsername(user.getName()).isPresent()) {
            model.addAttribute("registrationError", "El usuario ya existe.");
            return "registration-page"; 
        }

        user.setEncodedPassword(passwordEncoder.encode(user.getEncodedPassword()));
        user.setRoles(List.of("USER"));

        userService.saveUser(user);

        return "redirect:/welcome-registered";
    }

    @GetMapping("/welcome-registered")
    public String welcomeRegistered(Model model) {
        model.addAttribute("museumHeroImage", "/assets/images/interior-museo.png");
        model.addAttribute("museumRoomImage", "/assets/images/sala-del-museo.png");
        return "welcome-page-registered-user";
    }

    @GetMapping("/welcome-admin")
    public String welcomeAdmin(Model model) {
        model.addAttribute("museumHeroImage", "/assets/images/interior-museo.png");
        model.addAttribute("museumRoomImage", "/assets/images/sala-del-museo.png");
        return "welcome-page-admin";
    }
}