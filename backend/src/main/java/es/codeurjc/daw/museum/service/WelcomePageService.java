package es.codeurjc.daw.museum.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import es.codeurjc.daw.museum.dto.SectionDTO;

@Service
public class WelcomePageService {
    
    public List<SectionDTO> getWelcomeSections() {

        List<SectionDTO> sections = new ArrayList<>();

                sections.add(new SectionDTO("Peces", "/assets/images/icons/logo-pez.png", "/section/peces",
                                List.of("Mar", "Agua dulce", "Abisales")));
                                    
                sections.add(new SectionDTO("Insectos", "/assets/images/icons/logo-mariposa.png", "/section/insectos",
                                List.of("Terrestres", "Aéreos", "Acuáticos")));
                                   
                sections.add(new SectionDTO("Fósiles", "/assets/images/icons/logo-fosil.png", "/section/fosiles",
                                List.of("Prehistóricos", "Minerales")));
                                    
                sections.add(new SectionDTO("Obras de arte", "/assets/images/icons/logo-pintura.png", "/section/arte",
                                List.of("Pintura", "Escultura", "Cerámica")));       

        return sections;
    }
    
}
