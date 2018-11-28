package com.rsi.memegenerator.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.rsi.memegenerator.model.Meme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class S3Service {
    private AmazonS3 s3client;

    @Value("${amazonStorageProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonStorageProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;

//    public String deleteFileFromS3Bucket(String fileUrl) {
//        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
//        s3client.deleteObject(new DeleteObjectRequest(bucketName + "/", fileName));
//        return "Successfully deleted";
//    }

    public Meme upload(MultipartFile multipartFile, String toBucketPath) throws IOException {
        String fileUrl = "";
        Meme meme = new Meme();
        meme.setFilename(generateFileName(multipartFile));
        meme.setUploadDate(new Timestamp(System.currentTimeMillis()));
        meme.setS3url(endpointUrl + "/" + bucketName + toBucketPath + "/" + meme.getFilename());
        uploadFileToS3bucket(toBucketPath, meme.getFilename(), multipartFile);
        return meme;
    }

    @PostConstruct
    private void initializeAmazon() {
        BasicAWSCredentials creds = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(creds)).withRegion(Regions.US_WEST_2).build();
    }

    private String generateFileName(MultipartFile file) {
        return new Date().getTime() + "-" + file.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileToS3bucket(String inBucketPath, String fileName, MultipartFile file) throws IOException{
        ObjectMetadata data = new ObjectMetadata();
        data.setContentType(file.getContentType());
        data.setContentLength(file.getSize());
        s3client.putObject(new PutObjectRequest(bucketName + inBucketPath, fileName, file.getInputStream(), data)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }
}
