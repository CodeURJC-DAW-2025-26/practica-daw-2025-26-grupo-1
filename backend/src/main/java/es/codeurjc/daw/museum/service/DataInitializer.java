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
                        userRepository.save(new User("admin", passwordEncoder.encode("adminpass"),
                                        List.of("USER", "ADMIN")));
                }

                if (objectService.findAll().isEmpty()) {

                        /*
                         * var stream = getClass().getClassLoader()
                         * .getResourceAsStream("project-images/fish/sea/pez-napoleon.png");
                         */

                        // FISH
                        Image imagePezNapoleon = imageService
                                        .createImage(getClass().getResourceAsStream(
                                                        "/project-images/fish/sea/pez-napoleon.png"));

                        MuseumObject pezNapoleon = new MuseumObject();
                        pezNapoleon.setObjectName("Pez napoleón");
                        pezNapoleon.setGroupName("Cheilinus undulatus");
                        pezNapoleon.setType("peces");
                        pezNapoleon.setCategory("Mar");
                        pezNapoleon.setTechnicalData(
                                        "Tamaño: hasta 2.3 metros. Peso: hasta 190 kg. Hábitat: arrecifes coralinos del Indo-Pacífico.");
                        pezNapoleon.setDescription(
                                        "El pez Napoleón es una especie de pez loro conocido por su gran tamaño y su distintiva protuberancia en la cabeza.");
                        pezNapoleon.setImage(imagePezNapoleon);
                        objectService.saveObject(pezNapoleon);

                        Image imageKoi = imageService
                                        .createImage(getClass().getResourceAsStream(
                                                        "/project-images/fish/freshwater/koi.png"));

                        MuseumObject koi = new MuseumObject();
                        koi.setObjectName("Koi");
                        koi.setGroupName("Cyprinus carpio");
                        koi.setType("peces");
                        koi.setCategory("Agua dulce");
                        koi.setTechnicalData(
                                        "Tamaño: hasta 1 metro. Peso: hasta 10 kg. Hábitat: estanques y ríos.");
                        koi.setDescription(
                                        "El koi es una especie de pez ornamental muy popular en jardines y estanques.");
                        koi.setImage(imageKoi);
                        objectService.saveObject(koi);

                        Image imagePezCabezaTransparente = imageService
                                        .createImage(getClass().getResourceAsStream(
                                                        "/project-images/fish/deep-sea/pez-cabeza-transparente.png"));

                        MuseumObject pezCabezaTransparente = new MuseumObject();
                        pezCabezaTransparente.setObjectName("Pez cabeza transparente");
                        pezCabezaTransparente.setGroupName("Hemiscyllium ocellatum");
                        pezCabezaTransparente.setType("peces");
                        pezCabezaTransparente.setCategory("Abisales");
                        pezCabezaTransparente.setTechnicalData(
                                        "Tamaño: hasta 1 metro. Peso: hasta 10 kg. Hábitat: fondos marinos.");
                        pezCabezaTransparente.setDescription(
                                        "El pez cabeza transparente es una especie de pez que vive en los fondos marinos y tiene una cabeza transparente.");
                        pezCabezaTransparente.setImage(imagePezCabezaTransparente);
                        objectService.saveObject(pezCabezaTransparente);

                        Image imageTetraNeon = imageService
                                        .createImage(getClass().getResourceAsStream(
                                                        "/project-images/fish/freshwater/tetra-neon.png"));

                        MuseumObject tetraNeon = new MuseumObject();
                        tetraNeon.setObjectName("Tetra neón");
                        tetraNeon.setGroupName("Paracheirodon innesi");
                        tetraNeon.setType("peces");
                        tetraNeon.setCategory("Agua dulce");
                        tetraNeon.setTechnicalData(
                                        "Tamaño: hasta 1 metro. Peso: hasta 10 kg. Hábitat: estanques y ríos.");
                        tetraNeon.setDescription(
                                        "El tetra neón es una especie de pez ornamental muy popular en jardines y estanques.");
                        tetraNeon.setImage(imageTetraNeon);
                        objectService.saveObject(tetraNeon);

                        Image imageCelacanto = imageService
                                        .createImage(getClass().getResourceAsStream(
                                                        "/project-images/fish/deep-sea/celacanto.png"));

                        MuseumObject celacanto = new MuseumObject();
                        celacanto.setObjectName("Celacanto");
                        celacanto.setGroupName("Latimeria chalumnae");
                        celacanto.setType("peces");
                        celacanto.setCategory("Abisales");
                        celacanto.setTechnicalData(
                                        "Tamaño: hasta 2 metros. Peso: hasta 200 kg. Hábitat: fondos marinos.");
                        celacanto.setDescription(
                                        "El celacanto es una especie de pez que vive en los fondos marinos y es considerado un fósil vivo.");
                        celacanto.setImage(imageCelacanto);
                        objectService.saveObject(celacanto);

                        Image imageCalamarVampiro = imageService
                                        .createImage(getClass().getResourceAsStream(
                                                        "/project-images/fish/deep-sea/calamar-vampiro.png"));

                        MuseumObject calamarVampiro = new MuseumObject();
                        calamarVampiro.setObjectName("Calamar vampiro");
                        calamarVampiro.setGroupName("Architeuthis dux");
                        calamarVampiro.setType("peces");
                        calamarVampiro.setCategory("Abisales");
                        calamarVampiro.setTechnicalData(
                                        "Tamaño: hasta 2 metros. Peso: hasta 200 kg. Hábitat: fondos marinos.");
                        calamarVampiro.setDescription(
                                        "El calamar vampiro es una especie de calamar que vive en los fondos marinos y es conocido por su gran tamaño y poderosa mandíbula.");
                        calamarVampiro.setImage(imageCalamarVampiro);
                        objectService.saveObject(calamarVampiro);

                        Image imagePezCirujano = imageService
                                        .createImage(getClass().getResourceAsStream(
                                                        "/project-images/fish/sea/pez-cirujano.png"));

                        MuseumObject pezCirujano = new MuseumObject();
                        pezCirujano.setObjectName("Pez cirujano");
                        pezCirujano.setGroupName("Paracanthopterus");
                        pezCirujano.setType("peces");
                        pezCirujano.setCategory("Mar");
                        pezCirujano.setTechnicalData(
                                        "Tamaño: hasta 1 metro. Peso: hasta 10 kg. Hábitat: fondos marinos.");
                        pezCirujano.setDescription(
                                        "El pez cirujano es una especie de pez que vive en los fondos marinos y es conocido por su capacidad de camuflaje.");
                        pezCirujano.setImage(imagePezCirujano);
                        objectService.saveObject(pezCirujano);

                        Image imagePezLuna = imageService
                                        .createImage(getClass().getResourceAsStream(
                                                        "/project-images/fish/sea/pez-luna.png"));

                        MuseumObject pezLuna = new MuseumObject();
                        pezLuna.setObjectName("Pez luna");
                        pezLuna.setGroupName("Selene setapinnis");
                        pezLuna.setType("peces");
                        pezLuna.setCategory("Mar");
                        pezLuna.setTechnicalData(
                                        "Tamaño: hasta 1 metro. Peso: hasta 10 kg. Hábitat: fondos marinos.");
                        pezLuna.setDescription(
                                        "El pez luna es una especie de pez que vive en los fondos marinos y es conocido por su forma lunar.");
                        pezLuna.setImage(imagePezLuna);
                        objectService.saveObject(pezLuna);

                        Image imagePezLinterna = imageService
                                        .createImage(getClass().getResourceAsStream(
                                                        "/project-images/fish/deep-sea/pez-linterna.png"));

                        MuseumObject pezLinterna = new MuseumObject();
                        pezLinterna.setObjectName("Pez linterna");
                        pezLinterna.setGroupName("Photostomias guernei");
                        pezLinterna.setType("peces");
                        pezLinterna.setCategory("Abisales");
                        pezLinterna.setTechnicalData(
                                        "Tamaño: hasta 1 metro. Peso: hasta 10 kg. Hábitat: fondos marinos.");
                        pezLinterna.setDescription(
                                        "El pez linterna es una especie de pez que vive en los fondos marinos y es conocido por su capacidad de producir luz.");
                        pezLinterna.setImage(imagePezLinterna);
                        objectService.saveObject(pezLinterna);

                        /*Image imageCanastaFloresVenus = imageService
                                        .createImage(getClass().getResourceAsStream(
                                                        "/project-images/fish/deep-sea/canasta-flores-venus.png"));

                        MuseumObject canastaFloresVenus = new MuseumObject();
                        canastaFloresVenus.setObjectName("Canasta de flores de Venus");
                        canastaFloresVenus.setGroupName("Passiflora caerulea");
                        canastaFloresVenus.setType("plantas");
                        canastaFloresVenus.setCategory("Abisales");
                        canastaFloresVenus.setTechnicalData(
                                        "Tamaño: hasta 1 metro. Peso: hasta 10 kg. Hábitat: bosques y praderas.");
                        canastaFloresVenus.setDescription(
                                        "La canasta de flores de Venus es una especie de planta conocida por sus flores grandes y vistosas.");
                        canastaFloresVenus.setImage(imageCanastaFloresVenus);
                        objectService.saveObject(canastaFloresVenus);*/

                        // INSECTS
                        Image imageMariposaMonarca = imageService
                                        .createImage(getClass().getResourceAsStream(
                                                        "/project-images/insects/flyers/mariposa-monarca.png"));

                        MuseumObject mariposaMonarca = new MuseumObject();
                        mariposaMonarca.setObjectName("Mariposa monarca");
                        mariposaMonarca.setGroupName("Danaus plexippus");
                        mariposaMonarca.setType("insectos");
                        mariposaMonarca.setCategory("Aéreos");
                        mariposaMonarca.setTechnicalData(
                                        "Tamaño: 8-10 cm. Peso: 0.5-1.5 g. Hábitat: bosques y praderas.");
                        mariposaMonarca.setDescription(
                                        "La mariposa monarca es una especie de mariposa conocida por su migración anual y sus colores naranja y negros.");
                        mariposaMonarca.setImage(imageMariposaMonarca);
                        objectService.saveObject(mariposaMonarca);

                        Image imageEscorpion = imageService
                                        .createImage(getClass().getResourceAsStream(
                                                        "/project-images/insects/terrestrial/escorpion.png"));

                        MuseumObject escorpion = new MuseumObject();
                        escorpion.setObjectName("Escorpión");
                        escorpion.setGroupName("Vaejovis nelsoni");
                        escorpion.setType("insectos");
                        escorpion.setCategory("Terrestres");
                        escorpion.setTechnicalData(
                                        "Tamaño: 8-10 cm. Peso: 0.5-1.5 g. Hábitat: bosques y praderas.");
                        escorpion.setDescription(
                                        "El escorpión es un arácnido conocido por su cola con púa venenosa.");
                        escorpion.setImage(imageEscorpion);
                        objectService.saveObject(escorpion);

                        Image imageEscarabajoGoliat = imageService
                                        .createImage(getClass().getResourceAsStream(
                                                        "/project-images/insects/terrestrial/escarabajo-goliat.png"));

                        MuseumObject escarabajoGoliat = new MuseumObject();
                        escarabajoGoliat.setObjectName("Escarabajo goliat");
                        escarabajoGoliat.setGroupName("Promacrus goliat");
                        escarabajoGoliat.setType("insectos");
                        escarabajoGoliat.setCategory("Terrestres");
                        escarabajoGoliat.setTechnicalData(
                                        "Tamaño: 8-10 cm. Peso: 0.5-1.5 g. Hábitat: bosques y praderas.");
                        escarabajoGoliat.setDescription(
                                        "El escarabajo goliat es un insecto conocido por su gran tamaño y coloración llamativa.");
                        escarabajoGoliat.setImage(imageEscarabajoGoliat);
                        objectService.saveObject(escarabajoGoliat);

                        Image imageZapatero = imageService
                                        .createImage(getClass().getResourceAsStream(
                                                        "/project-images/insects/acuatic/zapatero.png"));

                        MuseumObject zapatero = new MuseumObject();
                        zapatero.setObjectName("Zapatero");
                        zapatero.setGroupName("Notonecta glauca");
                        zapatero.setType("insectos");
                        zapatero.setCategory("Acuáticos");
                        zapatero.setTechnicalData(
                                        "Tamaño: 8-10 cm. Peso: 0.5-1.5 g. Hábitat: bosques y praderas.");
                        zapatero.setDescription(
                                        "El zapatero es un insecto acuático conocido por su capacidad de caminar sobre el agua.");
                        zapatero.setImage(imageZapatero);
                        objectService.saveObject(zapatero);

                        Image imageMariposaAtlas = imageService
                                        .createImage(getClass().getResourceAsStream(
                                                        "/project-images/insects/flyers/mariposa-atlas.png"));

                        MuseumObject mariposaAtlas = new MuseumObject();
                        mariposaAtlas.setObjectName("Mariposa atlas");
                        mariposaAtlas.setGroupName("Attacus atlas");
                        mariposaAtlas.setType("insectos");
                        mariposaAtlas.setCategory("Aéreos");
                        mariposaAtlas.setTechnicalData(
                                        "Tamaño: 15-20 cm. Peso: 1-3 g. Hábitat: bosques tropicales.");
                        mariposaAtlas.setDescription(
                                        "La mariposa atlas es una especie de mariposa conocida por su gran tamaño y coloración vistosa.");
                        mariposaAtlas.setImage(imageMariposaAtlas);
                        objectService.saveObject(mariposaAtlas);

                        Image imageChincheAcuatica = imageService
                                        .createImage(getClass().getResourceAsStream(
                                                        "/project-images/insects/acuatic/chinche-acuatica.png"));

                        MuseumObject chincheAcuatica = new MuseumObject();
                        chincheAcuatica.setObjectName("Chinche acuática");
                        chincheAcuatica.setGroupName("Notonecta glauca");
                        chincheAcuatica.setType("insectos");
                        chincheAcuatica.setCategory("Acuáticos");
                        chincheAcuatica.setTechnicalData(
                                        "Tamaño: 8-10 cm. Peso: 0.5-1.5 g. Hábitat: bosques y praderas.");
                        chincheAcuatica.setDescription(
                                        "La chinche acuática es un insecto acuático conocido por su capacidad de caminar sobre el agua.");
                        chincheAcuatica.setImage(imageChincheAcuatica);
                        objectService.saveObject(chincheAcuatica);

                        Image imageMantisReligiosa = imageService
                                        .createImage(getClass().getResourceAsStream(
                                                        "/project-images/insects/terrestrial/mantis-religiosa.png"));

                        MuseumObject mantisReligiosa = new MuseumObject();
                        mantisReligiosa.setObjectName("Mantis religiosa");
                        mantisReligiosa.setGroupName("Mantis religiosa");
                        mantisReligiosa.setType("insectos");
                        mantisReligiosa.setCategory("Terrestres");
                        mantisReligiosa.setTechnicalData(
                                        "Tamaño: 8-10 cm. Peso: 0.5-1.5 g. Hábitat: bosques y praderas.");
                        mantisReligiosa.setDescription(
                                        "La mantis religiosa es un insecto conocido por su apariencia similar a una religiosa y su comportamiento depredador.");
                        mantisReligiosa.setImage(imageMantisReligiosa);
                        objectService.saveObject(mantisReligiosa);

                        Image imageLibelulaTigre = imageService
                                        .createImage(getClass().getResourceAsStream(
                                                        "/project-images/insects/flyers/libelula-tigre.png"));

                        MuseumObject libelulaTigre = new MuseumObject();
                        libelulaTigre.setObjectName("Libélula tigre");
                        libelulaTigre.setGroupName("Libélula tigre");
                        libelulaTigre.setType("insectos");
                        libelulaTigre.setCategory("Aéreos");
                        libelulaTigre.setTechnicalData(
                                        "Tamaño: 8-10 cm. Peso: 0.5-1.5 g. Hábitat: bosques y praderas.");
                        libelulaTigre.setDescription(
                                        "La libélula tigre es un insecto volador conocido por su apariencia similar a una tigre y su comportamiento depredador.");
                        libelulaTigre.setImage(imageLibelulaTigre);
                        objectService.saveObject(libelulaTigre);

                        Image imageMariquita = imageService
                                        .createImage(getClass().getResourceAsStream(
                                                        "/project-images/insects/terrestrial/mariquita.png"));

                        MuseumObject mariquita = new MuseumObject();
                        mariquita.setObjectName("Mariquita");
                        mariquita.setGroupName("Mariquita");
                        mariquita.setType("insectos");
                        mariquita.setCategory("Terrestres");
                        mariquita.setTechnicalData(
                                        "Tamaño: 8-10 cm. Peso: 0.5-1.5 g. Hábitat: bosques y praderas.");
                        mariquita.setDescription(
                                        "La mariquita es un insecto conocido por su apariencia similar a una mariquita y su comportamiento depredador.");
                        mariquita.setImage(imageMariquita);
                        objectService.saveObject(mariquita);

                        // FOSSILS AND MINERALS
                        Image imageTrilobite = imageService.createImage(
                                        getClass().getResourceAsStream(
                                                        "/project-images/fossils/prehistoric/trilobite.png"));

                        MuseumObject trilobite = new MuseumObject();
                        trilobite.setObjectName("Trilobite");
                        trilobite.setGroupName("Trilobita");
                        trilobite.setType("fosiles");
                        trilobite.setCategory("Prehistóricos");
                        trilobite.setTechnicalData(
                                        "Edad: 521-250 millones de años. Longitud: 2-70 cm. Ubicación: sedimentos marinos fósiles.");
                        trilobite.setDescription(
                                        "Los trilobites son artrópodos marinos extintos muy conocidos por su caparazón segmentado y su amplia diversidad durante el Paleozoico.");
                        trilobite.setImage(imageTrilobite);
                        objectService.saveObject(trilobite);

                        Image imageDienteTiburon = imageService.createImage(
                                        getClass().getResourceAsStream(
                                                        "/project-images/fossils/prehistoric/diente-tiburon.png"));

                        MuseumObject dienteTiburon = new MuseumObject();
                        dienteTiburon.setObjectName("Diente de Tiburón");
                        dienteTiburon.setGroupName("Tiburón");
                        dienteTiburon.setType("fosiles");
                        dienteTiburon.setCategory("Prehistóricos");
                        dienteTiburon.setTechnicalData(
                                        "Edad: 10-20 millones de años. Longitud: 10-20 cm. Ubicación: sedimentos marinos fósiles.");
                        dienteTiburon.setDescription(
                                        "El diente de tiburón es un fósil conocido por su forma distintiva y su uso en la identificación de especies prehistóricas.");
                        dienteTiburon.setImage(imageDienteTiburon);
                        objectService.saveObject(dienteTiburon);

                        Image imageAmatista = imageService.createImage(
                                        getClass().getResourceAsStream(
                                                        "/project-images/fossils/minerals/amatista.png"));

                        MuseumObject amatista = new MuseumObject();
                        amatista.setObjectName("Amatista");
                        amatista.setGroupName("Minerales");
                        amatista.setType("fosiles");
                        amatista.setCategory("Minerales");
                        amatista.setTechnicalData(
                                        "Edad: 10-20 millones de años. Longitud: 10-20 cm. Ubicación: sedimentos marinos fósiles.");
                        amatista.setDescription(
                                        "La amatista es un mineral conocido por su color púrpura y su uso en la joyería.");
                        amatista.setImage(imageAmatista);
                        objectService.saveObject(amatista);

                        Image imageAmmonites = imageService.createImage(
                                        getClass().getResourceAsStream(
                                                        "/project-images/fossils/prehistoric/ammonites.png"));

                        MuseumObject ammonites = new MuseumObject();
                        ammonites.setObjectName("Ammonites");
                        ammonites.setGroupName("Prehistóricos");
                        ammonites.setType("fosiles");
                        ammonites.setCategory("Prehistóricos");
                        ammonites.setTechnicalData(
                                        "Edad: 10-20 millones de años. Longitud: 10-20 cm. Ubicación: sedimentos marinos fósiles.");
                        ammonites.setDescription(
                                        "Los ammonites son fósiles de moluscos marinos conocidos por su forma espiralada y su uso en la identificación de especies prehistóricas.");
                        ammonites.setImage(imageAmmonites);
                        objectService.saveObject(ammonites);

                        Image imageOlivino = imageService.createImage(
                                        getClass().getResourceAsStream(
                                                        "/project-images/fossils/minerals/olivino.png"));

                        MuseumObject olivino = new MuseumObject();
                        olivino.setObjectName("Olivino");
                        olivino.setGroupName("Minerales");
                        olivino.setType("fosiles");
                        olivino.setCategory("Minerales");
                        olivino.setTechnicalData(
                                        "Edad: 10-20 millones de años. Longitud: 10-20 cm. Ubicación: sedimentos marinos fósiles.");
                        olivino.setDescription(
                                        "El olivino es un mineral conocido por su color verde y su uso en la joyería.");
                        olivino.setImage(imageOlivino);
                        objectService.saveObject(olivino);

                        Image imageHelecho = imageService.createImage(
                                        getClass().getResourceAsStream(
                                                        "/project-images/fossils/prehistoric/helecho.png"));

                        MuseumObject helecho = new MuseumObject();
                        helecho.setObjectName("Helecho");
                        helecho.setGroupName("Prehistóricos");
                        helecho.setType("fosiles");
                        helecho.setCategory("Prehistóricos");
                        helecho.setTechnicalData(
                                        "Edad: 10-20 millones de años. Longitud: 10-20 cm. Ubicación: sedimentos marinos fósiles.");
                        helecho.setDescription(
                                        "El helecho es un fósil de planta prehistórica conocido por su forma y su uso en la identificación de especies.");
                        helecho.setImage(imageHelecho);
                        objectService.saveObject(helecho);

                        Image imageDientesDeSable = imageService.createImage(
                                        getClass().getResourceAsStream(
                                                        "/project-images/fossils/prehistoric/dientes-de-sable.png"));

                        MuseumObject dientesDeSable = new MuseumObject();
                        dientesDeSable.setObjectName("Tigre dientes de sable");
                        dientesDeSable.setGroupName("Prehistóricos");
                        dientesDeSable.setType("fosiles");
                        dientesDeSable.setCategory("Prehistóricos");
                        dientesDeSable.setTechnicalData(
                                        "Edad: 10-20 millones de años. Longitud: 10-20 cm. Ubicación: sedimentos marinos fósiles.");
                        dientesDeSable.setDescription(
                                        "El tigre dientes de sable es un fósil de animal prehistórico conocido por su tamaño y forma distintiva.");
                        dientesDeSable.setImage(imageDientesDeSable);
                        objectService.saveObject(dientesDeSable);

                        Image imageMamut = imageService.createImage(
                                        getClass().getResourceAsStream(
                                                        "/project-images/fossils/prehistoric/mamut.png"));

                        MuseumObject mamut = new MuseumObject();
                        mamut.setObjectName("Mamut");
                        mamut.setGroupName("Prehistóricos");
                        mamut.setType("fosiles");
                        mamut.setCategory("Prehistóricos");
                        mamut.setTechnicalData(
                                        "Edad: 10-20 millones de años. Longitud: 10-20 cm. Ubicación: sedimentos marinos fósiles.");
                        mamut.setDescription(
                                        "El mamut es un fósil de animal prehistórico conocido por su tamaño y forma distintiva.");
                        mamut.setImage(imageMamut);
                        objectService.saveObject(mamut);

                        // ART, SCULPTURE AND CERAMICS
                        Image imageMeninas = imageService.createImage(
                                        getClass().getResourceAsStream(
                                                        "/project-images/art/paintings/las-meninas.png"));

                        MuseumObject lasMeninas = new MuseumObject();
                        lasMeninas.setObjectName("Las Meninas");
                        lasMeninas.setGroupName("Diego Velázquez");
                        lasMeninas.setType("arte");
                        lasMeninas.setCategory("Pintura");
                        lasMeninas.setTechnicalData(
                                        "Tamaño: 318 x 276 cm. Año: 1656. Ubicación: Museo del Prado, Madrid.");
                        lasMeninas.setDescription(
                                        "Las Meninas es una pintura del pintor español Diego Velázquez, considerada una de las obras maestras de la pintura española.");
                        lasMeninas.setImage(imageMeninas);
                        objectService.saveObject(lasMeninas);

                        Image imageVenusDeMilo = imageService.createImage(
                                        getClass().getResourceAsStream(
                                                        "/project-images/art/sculptures/venus-milo.png"));

                        MuseumObject venusDeMilo = new MuseumObject();
                        venusDeMilo.setObjectName("Venus de Milo");
                        venusDeMilo.setGroupName("Escultura");
                        venusDeMilo.setType("arte");
                        venusDeMilo.setCategory("Escultura");
                        venusDeMilo.setTechnicalData(
                                        "Tamaño: 203 x 44 cm. Año: 130 a.C. Ubicación: Museo del Prado, Madrid.");
                        venusDeMilo.setDescription(
                                        "La Venus de Milo es una estatua antigua griega, considerada una de las obras maestras de la escultura clásica.");
                        venusDeMilo.setImage(imageVenusDeMilo);
                        objectService.saveObject(venusDeMilo);

                        Image imageVerano = imageService.createImage(
                                        getClass().getResourceAsStream(
                                                        "/project-images/art/paintings/verano.png"));

                        MuseumObject verano = new MuseumObject();
                        verano.setObjectName("El Verano (L'Estate)");
                        verano.setGroupName("Pintura");
                        verano.setType("arte");
                        verano.setCategory("Pintura");

                        verano.setTechnicalData(
                                        "Tamaño: 67 x 50.8 cm. Año: 1563. Técnica: Óleo sobre tabla. Ubicación: Kunsthistorisches Museum, Viena.");

                        verano.setDescription(
                                        "Obra maestra del pintor italiano Giuseppe Arcimboldo, compuesto íntegramente por frutas y vegetales de la estación.");

                        verano.setImage(imageVerano);
                        objectService.saveObject(verano);

                        Image imageOla = imageService.createImage(
                                        getClass().getResourceAsStream(
                                                        "/project-images/art/paintings/ola-kanagawa.png"));

                        MuseumObject ola = new MuseumObject();
                        ola.setObjectName("La gran ola de Kanagawa");
                        ola.setGroupName("Pintura");
                        ola.setType("arte");
                        ola.setCategory("Pintura");

                        ola.setTechnicalData(
                                        "Tamaño: 25.7 x 37.9 cm. Año: 1831. Técnica: Xilografía. Ubicación: Museo Metropolitano de Arte, Nueva York.");

                        ola.setDescription(
                                        "La gran ola de Kanagawa es una xilografía del artista japonés Katsushika Hokusai, considerada una de las obras más famosas del arte japonés y un icono de la cultura popular.");

                        ola.setImage(imageOla);
                        objectService.saveObject(ola);

                        Image imageAlada = imageService.createImage(
                                        getClass().getResourceAsStream(
                                                        "/project-images/art/sculptures/victoria-samotracia.png"));

                        MuseumObject alada = new MuseumObject();
                        alada.setObjectName("La Victoria Alada de Samotracia");
                        alada.setGroupName("Escultura");
                        alada.setType("arte");
                        alada.setCategory("Escultura");

                        alada.setTechnicalData(
                                        "Tamaño: 245 cm. Año: 190 a.C. Ubicación: Museo del Louvre, París.");

                        alada.setDescription(
                                        "La Victoria Alada de Samotracia es una escultura del artista griego Anónimo, considerada una de las obras más famosas del arte clásico y un icono de la cultura popular.");

                        alada.setImage(imageAlada);
                        objectService.saveObject(alada);

                        Image imageMonaLisa = imageService.createImage(
                                        getClass().getResourceAsStream(
                                                        "/project-images/art/paintings/mona-lisa.png"));

                        MuseumObject monaLisa = new MuseumObject();
                        monaLisa.setObjectName("La Mona Lisa (La Gioconda)");
                        monaLisa.setGroupName("Pintura");
                        monaLisa.setType("arte");
                        monaLisa.setCategory("Pintura");

                        monaLisa.setTechnicalData(
                                        "Tamaño: 77 x 53 cm. Año: 1503-1506. Técnica: Óleo sobre tabla. Ubicación: Museo del Louvre, París.");

                        monaLisa.setDescription(
                                        "La Mona Lisa (La Gioconda) es una pintura del artista italiano Leonardo da Vinci, considerada una de las obras más famosas del arte renacentista y un icono de la cultura popular.");

                        monaLisa.setImage(imageMonaLisa);
                        objectService.saveObject(monaLisa);

                        Image imageVasoGuerreros = imageService.createImage(
                                        getClass().getResourceAsStream(
                                                        "/project-images/art/ceramics/vaso-guerreros.png"));

                        MuseumObject vasoGuerreros = new MuseumObject();
                        vasoGuerreros.setObjectName("Vaso de los Guerreros");
                        vasoGuerreros.setGroupName("Cerámica");
                        vasoGuerreros.setType("arte");
                        vasoGuerreros.setCategory("Cerámica");

                        vasoGuerreros.setTechnicalData(
                                        "Tamaño: 20 cm. Año: 700 a.C. Ubicación: Museo del Louvre, París.");

                        vasoGuerreros.setDescription(
                                        "El Vaso de los Guerreros es una cerámica del artista griego Anónimo, considerada una de las obras más famosas del arte clásico y un icono de la cultura popular.");

                        vasoGuerreros.setImage(imageVasoGuerreros);
                        objectService.saveObject(vasoGuerreros);

                        Image imageNocheEstrellada = imageService.createImage(
                                        getClass().getResourceAsStream(
                                                        "/project-images/art/paintings/noche-estrellada.png"));

                        MuseumObject nocheEstrellada = new MuseumObject();
                        nocheEstrellada.setObjectName("La Noche Estrellada");
                        nocheEstrellada.setGroupName("Pintura");
                        nocheEstrellada.setType("arte");
                        nocheEstrellada.setCategory("Pintura");

                        nocheEstrellada.setTechnicalData(
                                        "Tamaño: 73 x 92 cm. Año: 1889. Técnica: Óleo sobre lienzo. Ubicación: Museo de Arte Moderno, Nueva York.");

                        nocheEstrellada.setDescription(
                                        "La Noche Estrellada es una pintura del artista neerlandés Vincent van Gogh, considerada una de las obras más famosas del arte moderno y un icono de la cultura popular.");

                        nocheEstrellada.setImage(imageNocheEstrellada);
                        objectService.saveObject(nocheEstrellada);

                        Image imageGuernica = imageService.createImage(
                                        getClass().getResourceAsStream(
                                                        "/project-images/art/paintings/guernica.png"));

                        MuseumObject guernica = new MuseumObject();
                        guernica.setObjectName("El Guernica");
                        guernica.setGroupName("Pintura");
                        guernica.setType("arte");
                        guernica.setCategory("Pintura");

                        guernica.setTechnicalData(
                                        "Tamaño: 349 x 776 cm. Año: 1937. Técnica: Óleo sobre lienzo. Ubicación: Museo Reina Sofía, Madrid.");

                        guernica.setDescription(
                                        "El Guernica es una pintura del artista español Pablo Picasso, considerada una de las obras más famosas del arte moderno y un icono de la cultura popular.");

                        guernica.setImage(imageGuernica);
                        objectService.saveObject(guernica);
                }

        }
}
