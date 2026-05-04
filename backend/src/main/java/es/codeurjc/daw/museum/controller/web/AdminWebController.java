package es.codeurjc.daw.museum.controller.web;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminWebController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/users")
    public String listUsers(Model model, HttpServletRequest request, Pageable pageable) {

        // Retrieves all users from the database to display them in the admin panel
        Page<User> users = userService.findAll(pageable);
        model.addAttribute("users", users);

        // Gets the currently authenticated user (admin)
        Principal principal = request.getUserPrincipal();
        if (principal != null) {

            // Loads full user entity from database using username
            User loguedAdmin = userService.findByUsername(principal.getName()).orElse(null);
            if (loguedAdmin != null) {
                 // Adds logged user info to the model for navbar rendering
                model.addAttribute("user", loguedAdmin); 
                model.addAttribute("logged", true); 
                model.addAttribute("admin", true); 
            }
        }

        // Returns the view that displays the user list
        return "admin_list_users"; 
    }

    @PostMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {

        // Deletes a user by its ID
        userService.deleteUser(id);

        // Redirects to confirmation page after deletion
        return "redirect:/confirmation?action=deleteUser";
    }

}
