package com.rsi.memegenerator.controller;

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

    @PostMapping(UPLOAD_BLANK_MEME)
    public void uploadBlankMeme(@RequestPart(value = "file") MultipartFile image) {
        dbService.insertBlank(s3Service.upload(image, BLANK_MEMES));
    }

    @PostMapping(UPLOAD_CREATED_MEME)
    public void uploadCreatedMeme(@RequestPart(value = "file") MultipartFile image) {
        s3Service.upload(image, CREATED_MEMES);
    }

//    @PostMapping(UPLOAD_BLANK_MEME)
//    public String downloadFile(@RequestBody String urlString) {
//        return this.s3Service.downloadFileFromS3Bucket(urlString);
//    }

    @DeleteMapping(DELETE_FILE)
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return s3Service.deleteFileFromS3Bucket(fileUrl);
    }
}
