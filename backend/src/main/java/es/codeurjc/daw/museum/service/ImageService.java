package es.codeurjc.daw.museum.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Pageable;

import es.codeurjc.daw.museum.model.Image;
import es.codeurjc.daw.museum.repository.ImageRepository;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image getImage(long id) {
        // Retrieves image entity by ID or throws exception if not found
        return imageRepository.findById(id).orElseThrow();
    }

    public Image createImage(InputStream inputStream) throws IOException {

        // Creates a new image from input stream and stores it as a BLOB in database
        Image image = new Image();

        try {
            image.setImageFile(new SerialBlob(inputStream.readAllBytes()));
        } catch (Exception e) {
            throw new IOException("Failed to create image", e);
        }

        imageRepository.save(image);

        return image;
    }

    public Resource getImageFile(long id) throws SQLException {

        // Returns image binary data as a Resource for HTTP response
        Image image = imageRepository.findById(id).orElseThrow();

        if (image.getImageFile() != null) {
            return new InputStreamResource(image.getImageFile().getBinaryStream());
        } else {
            throw new RuntimeException("Image file not found");
        }
    }

    public Page<Image> getImages(Pageable pageable) {
    return imageRepository.findAll(pageable);
}

    public Image replaceImageFile(long id, InputStream inputStream) throws IOException {

        // Replaces existing image binary content with a new one
        Image image = imageRepository.findById(id).orElseThrow();

        try {
            image.setImageFile(new SerialBlob(inputStream.readAllBytes()));
        } catch (Exception e) {
            throw new IOException("Failed to create image", e);
        }

        imageRepository.save(image);
        
        return image;
    }

    public Image deleteImage(long id) {

        // Deletes image from database and returns deleted entity
        Image image = imageRepository.findById(id).orElseThrow();
        imageRepository.deleteById(id);
        return image;
    }
}
 