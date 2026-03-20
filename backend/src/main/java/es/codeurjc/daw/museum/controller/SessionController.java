package es.codeurjc.daw.museum.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SessionController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();

        if (principal != null) {

            model.addAttribute("logged", true);
            model.addAttribute("userName", principal.getName());
            model.addAttribute("admin", request.isUserInRole("ADMIN"));

        } else {
            model.addAttribute("logged", false);
        }
    }

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
            model.addAttribute("museumHeroImage", "/assets/images/interior-museo.png");
            model.addAttribute("profileImage", "/assets/images/perfil-sin-foto.png");
            return "registration-page";
        }

        user.setEncodedPassword(passwordEncoder.encode(user.getEncodedPassword()));
        user.setRoles(List.of("USER"));

        userService.saveUser(user);

        return "redirect:/welcome-user";
    }

}