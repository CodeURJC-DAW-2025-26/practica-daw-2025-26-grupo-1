package es.codeurjc.daw.museum.controller.web;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MessageWebController {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request) {

        // Retrieves authenticated user information for global use (navbar, etc.)
        Principal principal = request.getUserPrincipal();

        if (principal != null) {
            model.addAttribute("logged", true);
            model.addAttribute("userName", principal.getName());
            model.addAttribute("admin", request.isUserInRole("ADMIN"));

            // Loads full user entity from database
            Optional<User> userOpt = userService.findByUsername(principal.getName());

            if (userOpt.isPresent()) {
                User user = userOpt.get();

                // Adds user object to model (used for profile/avatar rendering)
                model.addAttribute("user", user); 
            }

        } else {
            // Marks user as not authenticated
            model.addAttribute("logged", false);
        }
    }


    @GetMapping("/confirmation")

    public String showConfirmation(@RequestParam String action, @RequestParam(required = false) Long id,
            @RequestParam(required = false) String section, @RequestParam(required = false) Long objectId,
            Model model) {

        // Generates confirmation message and redirect URL based on action performed
        String message = "";
        String url = "/";
        String museumHeroImage = "/assets/images/interior-museo.png";

        // Handles different successful operations
        switch (action) {
            case "logout":
                message = "Has cerrado sesión correctamente.";
                url = "/";
                break;
            case "updateProfile":
                message = "Tu perfil ha sido actualizado correctamente.";
                url = "/profile";
                break;
            case "addObject":
                message = "El objeto ha sido añadido correctamente.";
                url = "/section/" + section;
                break;
            case "editObject":
                message = "El objeto ha sido actualizado correctamente.";
                url = "/";
                break;
            case "deleteObject":
                message = "El objeto ha sido eliminado correctamente.";
                url = "/";
                break;
            case "addNote":
                message = "La nota ha sido añadida correctamente.";
                url = "/section/" + section + "/" + id;
                break;
            case "editNote":
                message = "La nota ha sido actualizada correctamente.";
                url = "/";
                break;

            case "deleteNote":
                message = "La nota ha sido eliminada correctamente.";
                if (section != null && objectId != null) {
                    url = "/section/" + section + "/" + objectId;
                } else {
                    url = "/";
                }
                break;

            case "deleteUser":
                message = "El usuario ha sido eliminado correctamente.";
                url = "/admin/users";
                break;
            default:
                message = "Operación completada.";
                url = "/";
                break;
        }

        // Adds data to model for rendering confirmation page
        model.addAttribute("museumHeroImage", museumHeroImage);
        model.addAttribute("confirmationText", message);
        model.addAttribute("nextUrl", url);
        return "confirmed-action";

    }

    @GetMapping("/system-error")

    public String showError(@RequestParam String action, Model model) {

        // Generates error message based on failed action
        String message = "";
        String url = "/";
        String museumHeroImage = "/assets/images/interior-museo.png";

        // Handles different failed operations
        switch (action) {
            case "logout":
                message = "Se ha producido un error al iniciar sesión.";
                url = "/";
                break;
            case "updateProfile":
                message = "Se ha producido un error al actualizar tu perfil.";
                url = "/profile";
                break;
            case "addObject":
                message = "Se ha producido un error al añadir el objeto.";
                url = "/";
                break;
            case "editObject":
                message = "Se ha producido un error al editar el objeto.";
                url = "/";
                break;
            case "deleteObject":
                message = "Se ha producido un error al borrar el objeto.";
                url = "/";
                break;
            case "objectNotFound":
                message = "No se ha podido encontrar el objeto.";
                url = "/welcome-user";
                break;
            case "addNote":
                message = "Se ha producido un error al añadir la nota.";
                url = "/";
                break;
            case "editNote":
                message = "Se ha producido un error al editar la nota.";
                url = "/";
                break;
            case "deleteNote":
                message = "Se ha producido un error al borrar la nota.";
                url = "/";
                break;
            case "invalidData":
                message = "Los campos de nombre y datos de interés son obligatorios y no pueden estar vacíos.";
                url = "/";
                break;
            case "invalidAccess":
                message = "No tienes permiso para acceder a esta página.";
                url = "/";
                break;
            default:
                message = "Problema en el sistema.";
                url = "/";
                break;
        }

        // Adds data to model for rendering error page
        model.addAttribute("museumHeroImage", museumHeroImage);
        model.addAttribute("errorText", message);
        model.addAttribute("backLink", url);
        return "error-page";

    }

}
