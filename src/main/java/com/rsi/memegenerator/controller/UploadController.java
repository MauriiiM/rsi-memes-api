package com.rsi.memegenerator.controller;

import com.rsi.memegenerator.model.Meme;
import com.rsi.memegenerator.service.DatabaseService;
import com.rsi.memegenerator.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.rsi.memegenerator.constant.URLConstants.*;

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
    public void uploadMeme(@RequestPart(value = "file") MultipartFile image, @RequestPart(value = "tags") String tags) {
        Meme uploadedMeme = s3Service.upload(image, IMAGES);
        uploadedMeme.setTags(tags.toLowerCase().split(""));
        dbService.insert(uploadedMeme);
    }

    @GetMapping(IMAGES)
    public String[] downloadURLs(@RequestPart String tags) {
        return dbService.selectTags(tags.toLowerCase().split(" "));
    }

    @DeleteMapping(DELETE_FILE)
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return s3Service.deleteFileFromS3Bucket(fileUrl);
    }
}
