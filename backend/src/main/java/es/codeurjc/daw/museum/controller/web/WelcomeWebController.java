package es.codeurjc.daw.museum.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

import java.security.Principal;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@Controller
public class WelcomeWebController {

        @Autowired
        private UserService userService;

        // Represents a category label with style and text for UI display
        public class Category {
                private String type;
                private String text;

                public Category(String type, String text) {
                        this.type = type;
                        this.text = text;
                }

                public String getType() {
                        return type;
                }

                public String getText() {
                        return text;
                }
        }

        // Represents a section shown in the welcome page
        // Includes name, image, link and categories
        public class UserSection {
                private String nameSection;
                private String image;
                private String link;
                private List<Category> categories;

                public UserSection(String nameSection, String image, String link, List<Category> categories) {
                        this.nameSection = nameSection;
                        this.image = image;
                        this.link = link;
                        this.categories = categories;
                }

                public String getNameSection() {
                        return nameSection;
                }

                public String getImage() {
                        return image;
                }

                public String getLink() {
                        return link;
                }

                public List<Category> getCategories() {
                        return categories;
                }

        }

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

        @GetMapping("/welcome-user")
        public String welcomeStart(Model model) {

                // Builds the list of sections available to the user
                // Each section contains categories and navigation links

                model.addAttribute("museumHeroImage", "/assets/images/interior-museo.png");
                model.addAttribute("museumRoomImage", "/assets/images/sala-del-museo.png");

                List<UserSection> userSections = new ArrayList<>();

                userSections.add(new UserSection("Peces", "/assets/images/icons/logo-pez.png", "section/peces",
                                List.of(new Category("bg-secondary", "Mar"),
                                                new Category("bg-secondary", "Agua dulce"),
                                                new Category("bg-secondary", "Abisales"))));

                userSections.add(new UserSection("Insectos", "/assets/images/icons/logo-mariposa.png",
                                "/section/insectos",
                                List.of(new Category("bg-secondary", "Terrestres"),
                                                new Category("bg-secondary", "Aéreos"),
                                                new Category("bg-secondary", "Acuáticos"))));

                userSections.add(new UserSection("Fósiles", "/assets/images/icons/logo-fosil.png", "/section/fosiles",
                                List.of(new Category("bg-secondary", "Prehistóricos"),
                                                new Category("bg-secondary", "Minerales"))));

                userSections.add(new UserSection("Obras de arte", "/assets/images/icons/logo-pintura.png",
                                "/section/arte",
                                List.of(new Category("bg-secondary", "Pintura"),
                                                new Category("bg-secondary", "Escultura"),
                                                new Category("bg-secondary", "Cerámica"))));

                model.addAttribute("userSections", userSections);

                return "welcome-page";
        }

}