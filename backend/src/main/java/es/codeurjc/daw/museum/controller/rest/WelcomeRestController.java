package es.codeurjc.daw.museum.controller.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.daw.museum.dto.SectionDTO;
import es.codeurjc.daw.museum.service.WelcomePageService;

@RestController
@RequestMapping("/api/v1/sections")
public class WelcomeRestController {
    
    @Autowired
    private WelcomePageService welcomeService;

    @GetMapping("/welcome-user")
    public Collection <SectionDTO> getSections() {

        return welcomeService.getWelcomeSections();
        
    }

}
