package com.rsi.memegenerator.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
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

    @PostConstruct
    private void initializeAmazon() {
        BasicAWSCredentials creds = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(creds)).withRegion(Regions.US_WEST_2).build();
    }

    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(bucketName + "/", fileName));
        return "Successfully deleted";
    }

    public Meme upload(MultipartFile multipartFile, String toBucketPath) {
        String fileUrl = "";
        try {
            Meme meme = new Meme();
            meme.setFile(convertMultiPartToFile(multipartFile));
            meme.setFilename(generateFileName(multipartFile));
            meme.setUploadDate(new Timestamp(System.currentTimeMillis()));
            meme.setS3url(endpointUrl + "/" + bucketName + toBucketPath + "/" + meme.getFilename());
            uploadFileToS3bucket(toBucketPath, meme.getFilename(), meme.getFile());
            return meme;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile file) {
        return new Date().getTime() + "-" + file.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileToS3bucket(String inBucketPath, String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName + inBucketPath, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }
}
