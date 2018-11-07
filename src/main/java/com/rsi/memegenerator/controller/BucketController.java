package com.rsi.memegenerator.controller;

import com.rsi.memegenerator.constant.FileSizeConstants;
import com.rsi.memegenerator.service.S3Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.rsi.memegenerator.constant.URLConstants.*;

@RestController
@RequestMapping(STORAGE)
public class BucketController {

    private S3Client s3Client;

    @Autowired
    BucketController(S3Client amazonClient) {
        this.s3Client = amazonClient;
    }

    @PostMapping(UPLOAD_BLANK_MEME)
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.s3Client.uploadFile(file, BLANK_MEMES);
    }

//    @PostMapping(UPLOAD_BLANK_MEME)
//    public String downloadFile(@RequestBody String urlString) {
//        return this.s3Client.downloadFileFromS3Bucket(urlString);
//    }


    @DeleteMapping(DELETE_FILE)
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.s3Client.deleteFileFromS3Bucket(fileUrl);
    }
}
