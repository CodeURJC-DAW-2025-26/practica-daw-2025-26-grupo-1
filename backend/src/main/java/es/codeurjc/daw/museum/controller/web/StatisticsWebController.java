package es.codeurjc.daw.museum.controller.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.service.MuseumObjectService;
import es.codeurjc.daw.museum.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class StatisticsWebController {

    @Autowired
    private MuseumObjectService objectService;

    @Autowired
    private UserService userService;

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

    @GetMapping("/statistics")
    public String countObjects(Model model, HttpServletRequest request) {

        // Retrieves total number of objects per section (fish, insects, fossils, art)
        long fishObjects = objectService.countByType("peces");
        long insectObjects = objectService.countByType("insectos");
        long fossilObjects = objectService.countByType("fosiles");
        long artObjects = objectService.countByType("arte");

        model.addAttribute("fishObjects", fishObjects);
        model.addAttribute("insectObjects", insectObjects);
        model.addAttribute("fossilObjects", fossilObjects);
        model.addAttribute("artObjects", artObjects);

        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            User user = userService.findByUsername(principal.getName()).orElseThrow();

            // Calculates user progress percentage for each section based on how many objects have been marked as seen
            // Stores progress data in a map (section -> percentage) to be used in charts or UI rendering
            Map<String, Integer> progressMap = new HashMap<>();
            String[] sections = { "peces", "insectos", "fosiles", "arte" };

            for (String s : sections) {
                long total = objectService.countByType(s);
                int percentage = 0;

                if (total > 0) {

                    long seenInSection = user.getSeen().stream()
                            .filter(obj -> obj.getType().equalsIgnoreCase(s))
                            .count();

                    percentage = (int) ((seenInSection * 100) / total);
                }
                progressMap.put(s, percentage);
            }

            model.addAttribute("progressMap", progressMap);
        }

        return "data-graphics";
    }

}
