package com.indigo.controller;

import com.indigo.entity.Images;
import com.indigo.entity.Property;
import com.indigo.entity.PropertyUser;
import com.indigo.repository.ImagesRepository;
import com.indigo.repository.PropertyRepository;
import com.indigo.service.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

private ImagesRepository imagesRepository;
private BucketService bucketService;


    public ImageController(ImagesRepository imagesRepository, BucketService bucketService, PropertyRepository propertyRepository) {
        this.imagesRepository = imagesRepository;
        this.bucketService = bucketService;
        this.propertyRepository = propertyRepository;
    }
    private PropertyRepository propertyRepository;

    @PostMapping(path = "/upload/file/{bucketName}/property/{propertyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file,
                                             @PathVariable String bucketName,
                                             @PathVariable long propertyId,
                                             @AuthenticationPrincipal PropertyUser user) {

        String imageUrl = bucketService.uploadFile(file, bucketName);
        Property property = propertyRepository.findById(propertyId).get();
        Images img = new Images();
        img.setImageUrl(imageUrl);
        img.setProperty(property);
        img.setPropertyUser(user);
        Images savedImage = imagesRepository.save(img);

        return new ResponseEntity<>(savedImage, HttpStatus.OK);
    }
}
