package es.codeurjc.daw.museum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

import java.util.List;

@Controller
public class WelcomeController {

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

        @GetMapping("/welcome-user")
        public String welcomeRegistered(Model model) {

                model.addAttribute("museumHeroImage", "/assets/images/interior-museo.png");
                model.addAttribute("museumRoomImage", "/assets/images/sala-del-museo.png");

                List<UserSection> userSections = new ArrayList<>();

                userSections.add(new UserSection("Peces", "/assets/images/icons/logo-pez.png", "/section/peces",
                                List.of(new Category("bg-secondary", "Mar"),
                                                new Category("bg-secondary", "Agua dulce"),
                                                new Category("bg-secondary", "Abisales"))));

                userSections.add(new UserSection("Insectos", "/assets/images/icons/logo-mariposa.png", "/section/insectos",
                                List.of(new Category("bg-secondary", "Terrestres"),
                                                new Category("bg-secondary", "Aéreos"),
                                                new Category("bg-secondary", "Acuáticos"))));

                userSections.add(new UserSection("Fósiles", "/assets/images/icons/logo-fosil.png", "/section/fosiles",
                                List.of(new Category("bg-secondary", "Prehistóricos"),
                                                new Category("bg-secondary", "Minerales"))));

                userSections.add(new UserSection("Obras de arte", "/assets/images/icons/logo-pintura.png", "/section/arte",
                                List.of(new Category("bg-secondary", "Pintura"),
                                                new Category("bg-secondary", "Escultura"),
                                                new Category("bg-secondary", "Cerámica"))));

                model.addAttribute("userSections", userSections);

                return "welcome-page";
        }

}