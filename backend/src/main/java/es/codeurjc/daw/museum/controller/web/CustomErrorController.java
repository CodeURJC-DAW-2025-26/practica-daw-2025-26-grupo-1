package es.codeurjc.daw.museum.controller.web; // He puesto tu paquete

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.webmvc.error.ErrorAttributes;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

       // Retrieves HTTP error status code (e.g., 404, 403, 500)
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        int statusCode = (status != null) ? Integer.parseInt(status.toString()) : 500;

        // Default attributes for UI rendering (background image, navigation)
        model.addAttribute("museumHeroImage", "/assets/images/interior-museo.png");
        model.addAttribute("backLink", "/");

        // Sets user-friendly error message based on status code
        if (statusCode == 404) {
            model.addAttribute("errorText", "La página que buscas no existe en este museo.");
        } else if (statusCode == 403) {
            model.addAttribute("errorText", "Acceso denegado: No tienes permisos de administrador.");
        } else if (statusCode == 401) {
            model.addAttribute("errorText", "Debes iniciar sesión para acceder a este recurso.");
        } else {
            model.addAttribute("errorText", "Se ha producido un error inesperado en el sistema.");
        }

         // Returns custom error view
        return "error-page"; 
    }
}