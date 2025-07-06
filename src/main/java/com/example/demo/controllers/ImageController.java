package com.example.demo.controllers;


import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.sql.SQLException;
import java.util.List;

import com.example.demo.dto.ImageDTO;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.Image;
import com.example.demo.response.APIResponse;
import com.example.demo.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {

    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<APIResponse> saveImages(@RequestParam List<MultipartFile> files,@RequestParam Long productId) {
       try {
           List<ImageDTO> imageDtos = imageService.saveImage(files, productId);
           return ResponseEntity.ok(new APIResponse("Upload Success!", imageDtos));
       }
       catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse("Upload Failed!", e.getMessage()));
       }
     }

     @GetMapping("/image/download/{imageId}")
     public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId)throws SQLException {
        //The @PathVariable annotation in Spring is used to
         // extract values from the URL path and
         // bind them to method parameters in a controller.
         //GET /products/42 - imageId automatically becomes 42
         Image image = imageService.getImageById(imageId);
         ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1,(int) image.getImage().length()));
         return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ image.getFileName() + "\"").body(resource);
     }

     @PutMapping("/image/{imageId}/update")
     public ResponseEntity<APIResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
            Image image=imageService.getImageById(imageId);
            //wherever there uis a possibility of encountering
         //an exception, put a try catch statement
         try{
            if(image!=null){
                imageService.updateImage(file,imageId);
                return ResponseEntity.ok(new APIResponse("Update Success!", null));

            }
         } catch(ResourceNotFoundException e){
             return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));
         }

         return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("Update Failed!",INTERNAL_SERVER_ERROR));

     }

     @DeleteMapping("/image/{imageId}/delete")
     public ResponseEntity<APIResponse> deleteImage(@PathVariable Long imageId) {
            Image image=imageService.getImageById(imageId);
            //wherever there uis a possibility of encountering
         //an exception, put a try catch statement
         try{
            if(image!=null){
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new APIResponse("Delete Success!", null));

            }
         } catch(ResourceNotFoundException e){
             return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));
         }

         return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("Delete Failed!",INTERNAL_SERVER_ERROR));

     }



}
