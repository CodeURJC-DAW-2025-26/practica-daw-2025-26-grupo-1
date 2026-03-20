package es.codeurjc.daw.museum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MessageController {

    @GetMapping("/confirmation") 

    public String showConfirmation(@RequestParam String action, @RequestParam (required = false) Long id, @RequestParam (required = false) String section, Model model) {

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


    @GetMapping("/system-error") 

    public String showError(@RequestParam String action, Model model) {

        String message = "";
        String url = "/";
        String museumHeroImage = "/assets/images/interior-museo.png";

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
            default:
                message = "Problema en el sistema.";
                url = "/";
                break;
        }

        model.addAttribute("museumHeroImage", museumHeroImage);
        model.addAttribute("errorText", message);
        model.addAttribute("backLink", url);
        return "error-page";

    }
    
}
