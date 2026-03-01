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

        @GetMapping("/welcome-anonymous")
        public String welcomeAnonymous(Model model) {

                model.addAttribute("museumHeroImage", "/assets/images/interior-museo.png");
                model.addAttribute("museumRoomImage", "/assets/images/sala-del-museo.png");

                List<UserSection> userSections = new ArrayList<>();

                userSections.add(new UserSection("Peces", "/assets/images/icons/logo-pez.png", "/section/peces",
                                List.of(new Category("badge-primary", "Mar"),
                                                new Category("badge-info", "Agua dulce"),
                                                new Category("badge-dark", "Abisales"))));

                userSections.add(new UserSection("Insectos", "/assets/images/icons/logo-mariposa.png", "/section/insectos",
                                List.of(new Category("badge-primary", "Terrestres"),
                                                new Category("badge-info", "Aéreos"),
                                                new Category("badge-dark", "Acuáticos"))));

                userSections.add(new UserSection("Fósiles", "/assets/images/icons/logo-fosil.png", "/section/fosiles",
                                List.of(new Category("badge-primary", "Prehistóricos"),
                                                new Category("badge-info", "Minerales"))));

                userSections.add(new UserSection("Obras de arte", "/assets/images/icons/logo-pintura.png", "/section/arte",
                                List.of(new Category("badge-primary", "Pintura"),
                                                new Category("badge-info", "Escultura"),
                                                new Category("badge-dark", "Cerámica"))));

                model.addAttribute("userSections", userSections);

                return "welcome-page-anonymous";
        }

}