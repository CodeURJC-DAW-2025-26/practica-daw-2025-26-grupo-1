package es.codeurjc.daw.museum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {

    @GetMapping("/confirmation") 

    public String showConfirmation(@RequestParam String action, Model model) {

        String message = "";
        String url = "/";
        String museumHeroImage = "/assets/images/interior-museo.png";

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
                url = "/";
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
                url = "/";
                break;
            case "editNote":
                message = "La nota ha sido actualizada correctamente.";
                url = "/";
                break;
            case "deleteNote":
                message = "La nota ha sido eliminada correctamente.";
                url = "/";
                break;
            default:
                message = "Operación completada.";
                url = "/";
                break;
        }

        model.addAttribute("museumHeroImage", museumHeroImage);
        model.addAttribute("confirmationText", message);
        model.addAttribute("nextUrl", url);
        return "confirmed-action";

    }
    
}
