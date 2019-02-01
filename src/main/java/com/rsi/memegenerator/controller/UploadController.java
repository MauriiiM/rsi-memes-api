package com.rsi.memegenerator.controller;

import com.rsi.memegenerator.model.Meme;
import com.rsi.memegenerator.service.DatabaseService;
import com.rsi.memegenerator.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.sql.SQLException;

import static com.rsi.memegenerator.constant.Routes.*;

@RestController
@RequestMapping(STORAGE)
public class UploadController {
    private S3Service s3Service;
    private DatabaseService dbService;

    @Autowired
    UploadController(S3Service s3Service, DatabaseService dbService) {
        this.s3Service = s3Service;
        this.dbService = dbService;
    }

    @PostMapping(IMAGES)
    public String uploadMeme(@RequestPart(value = "file") MultipartFile image, @RequestPart String tags) {
        String response = null;
        try {
            Meme uploadedMeme = s3Service.upload(image, IMAGES);
            uploadedMeme.setTags(tags.toLowerCase().split(" "));
            dbService.insert(uploadedMeme);
            response = "uploaded";
        } catch (IOException | SQLException e){
            response = null; //@TODO research more into this
        }
        return response;
    }

    @GetMapping(IMAGES + "/**")
    public String[] downloadURLs() {
        UriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        String requestedValue = builder.buildAndExpand().getPath();
        String tags = requestedValue.substring(requestedValue.indexOf("&q=") + 3);
        return dbService.selectTags(tags.toLowerCase().split(","));
    }

//    @DeleteMapping(DELETE_FILE)
//    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
//        return s3Service.deleteFileFromS3Bucket(fileUrl);
//    }
}
