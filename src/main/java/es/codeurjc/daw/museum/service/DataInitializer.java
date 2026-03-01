package es.codeurjc.daw.museum.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import java.sql.SQLException;
import javax.sql.rowset.serial.SerialBlob;

import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.model.Image;
import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.repository.MuseumObjectRepository;
import es.codeurjc.daw.museum.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Service
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MuseumObjectRepository objectRepository;

    @Autowired
    private MuseumObjectService objectService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * @EventListener(ApplicationReadyEvent.class)
     * public void init() throws IOException, URISyntaxException, SQLException {
     * // Create only demo users - the app will have data from other sources
     * if (userRepository.count() == 0) {
     * userRepository.save(new User("user", passwordEncoder.encode("pass"),
     * List.of("USER")));
     * userRepository.save(new User("admin", passwordEncoder.encode("adminpass"),
     * List.of("USER", "ADMIN")));
     * }
     * }
     */

    @PostConstruct
    public void init() throws IOException, URISyntaxException {
        if (userRepository.count() == 0) {
            userRepository.save(new User("user", passwordEncoder.encode("pass"), List.of("USER")));
            userRepository.save(new User("admin", passwordEncoder.encode("adminpass"), List.of("USER", "ADMIN")));
        }

        if (objectService.findAll().isEmpty()) {

            Image imagePezNapoleon = imageService
                    .createImage(getClass().getResourceAsStream("/proyect-images/fish/sea/pez-napoleon.png"));

            MuseumObject pezNapoleon = new MuseumObject();
            pezNapoleon.setObjectName("Pez Napoleón");
            pezNapoleon.setGroupName("Cheilinus undulatus");
            pezNapoleon.setType("peces");
            pezNapoleon.setCategory("Mar");
            pezNapoleon.setTechnicalData(
                    "Tamaño: hasta 2.3 metros. Peso: hasta 190 kg. Hábitat: arrecifes coralinos del Indo-Pacífico.");
            pezNapoleon.setDescription(
                    "El pez Napoleón es una especie de pez loro conocido por su gran tamaño y su distintiva protuberancia en la cabeza.");
            pezNapoleon.setImage(imagePezNapoleon);
            objectService.saveObject(pezNapoleon);

            Image imageMariposaMonarca = imageService
                    .createImage(getClass().getResourceAsStream("/proyect-images/insects/flyers/mariposa-monarca.png"));

            MuseumObject mariposaMonarca = new MuseumObject();
            mariposaMonarca.setObjectName("Mariposa Monarca");
            mariposaMonarca.setGroupName("Danaus plexippus");
            mariposaMonarca.setType("insectos");
            mariposaMonarca.setCategory("Aéreos");
            mariposaMonarca.setTechnicalData("Tamaño: 8-10 cm. Peso: 0.5-1.5 g. Hábitat: bosques y praderas.");
            mariposaMonarca.setDescription(
                    "La mariposa monarca es una especie de mariposa conocida por su migración anual y sus colores naranja y negros.");
            mariposaMonarca.setImage(imageMariposaMonarca);
            objectService.saveObject(mariposaMonarca);

            Image imageTrilobite = imageService.createImage(
                    getClass().getResourceAsStream("/proyect-images/fossils/prehistoric/trilobite.png"));

            MuseumObject trilobite = new MuseumObject();
            trilobite.setObjectName("Trilobite");
            trilobite.setGroupName("Trilobita");
            trilobite.setType("fósiles");
            trilobite.setCategory("Marinos");
            trilobite.setTechnicalData(
                    "Edad: 521-250 millones de años. Longitud: 2-70 cm. Ubicación: sedimentos marinos fósiles.");
            trilobite.setDescription(
                    "Los trilobites son artrópodos marinos extintos muy conocidos por su caparazón segmentado y su amplia diversidad durante el Paleozoico.");
            trilobite.setImage(imageTrilobite);
            objectService.saveObject(trilobite);

        }

    }
}
