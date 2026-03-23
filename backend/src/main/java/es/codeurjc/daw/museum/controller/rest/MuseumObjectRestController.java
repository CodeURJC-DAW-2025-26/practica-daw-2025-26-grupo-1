package es.codeurjc.daw.museum.controller.rest;

import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.daw.museum.dto.ElementDTO;
import es.codeurjc.daw.museum.dto.ElementMapper;
import es.codeurjc.daw.museum.dto.ImageDTO;
import es.codeurjc.daw.museum.dto.ImageMapper;
import es.codeurjc.daw.museum.dto.MuseumObjectDTO;
import es.codeurjc.daw.museum.dto.MuseumObjectMapper;
import es.codeurjc.daw.museum.model.Image;
import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.service.ImageService;
import es.codeurjc.daw.museum.service.MuseumObjectService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/v1/objects")
public class MuseumObjectRestController {

    @Autowired
    private MuseumObjectService objectService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private MuseumObjectMapper objectMapper;

    @Autowired
    private ElementMapper elementMapper;

    @Autowired
    private ImageMapper imageMapper;


    @GetMapping("/")
    public ResponseEntity<Page<ElementDTO>> getObjects(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String name, 
            Pageable pageable) {

        Page<MuseumObject> objects;
        
        if (name != null) {
            objects = objectService.findByNamePageable(name, pageable);
        } else if (type != null && category != null) {
            objects = objectService.findByTypeAndCategory(type, category, pageable);
        } else if (type != null) {
            objects = objectService.findByType(type, pageable);
        } else if (category != null) {
            objects = objectService.findByCategory(category, pageable);
        } else {
            objects = objectService.findAllPageable(pageable);
        }

        Page<ElementDTO> dtoPage = objects.map(obj -> elementMapper.toDTO(obj));
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/{id}")
    public MuseumObjectDTO getObject(@PathVariable long id) {
        return objectMapper.toDTO(objectService.getObject(id));
    }

    @PostMapping("/")
    public ResponseEntity<MuseumObjectDTO> createObject(@RequestBody MuseumObjectDTO objectDTO) {
        MuseumObject obj = objectMapper.toEntity(objectDTO);
        MuseumObject newObj = objectService.createObject(obj);
        MuseumObjectDTO resultDTO = objectMapper.toDTO(newObj);

        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(resultDTO.id()).toUri();
        return ResponseEntity.created(location).body(resultDTO);
    }

    @PutMapping("/{id}")
    public MuseumObjectDTO replaceObject(@PathVariable long id, @RequestBody MuseumObjectDTO updatedDTO) {

        MuseumObject updatedObject = objectMapper.toEntity(updatedDTO);
        updatedObject = objectService.replaceObject(id, updatedObject);
        return objectMapper.toDTO(updatedObject);
    }

    @DeleteMapping("/{id}")
    public MuseumObjectDTO deleteObject(@PathVariable long id) {

        return objectMapper.toDTO(objectService.deleteObject(id));
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<ImageDTO> createObjectImage(@PathVariable long id, @RequestParam MultipartFile imageFile)
            throws IOException {

        if (imageFile.isEmpty())
            throw new IllegalArgumentException();

        Image image = imageService.createImage(imageFile.getInputStream());
        objectService.addImageToObject(id, image);

        URI location = fromCurrentContextPath()
                .path("/api/v1/images/{imageId}")
                .buildAndExpand(image.getId())
                .toUri();

        return ResponseEntity.created(location).body(imageMapper.toDTO(image));
    }

    @DeleteMapping("/{id}/image")
    public ImageDTO deleteObjectImage(@PathVariable long id) {
        MuseumObject obj = objectService.getObject(id);
        Image image = obj.getImage();

        objectService.removeImageFromObject(id);
        if (image != null) {
            imageService.deleteImage(image.getId());
        }

        return imageMapper.toDTO(image);
    }
}
