package es.codeurjc.daw.museum.controller.web;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import es.codeurjc.daw.museum.service.ImageService;

@Controller
public class ImageWebController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/images/{id}")
    public ResponseEntity<Object> getImageFile(@PathVariable long id) throws SQLException {

        // Retrieves image file from database/service by ID
        Resource imageFile = imageService.getImageFile(id);

        // Determines the media type (PNG, JPEG, etc.)
        MediaType mediaType = MediaTypeFactory
                .getMediaType(imageFile)
                .orElse(MediaType.IMAGE_PNG); 

        // Returns the image as HTTP response with correct content type
        return ResponseEntity
                .ok()
                .contentType(mediaType)
                .body(imageFile);
    }
}