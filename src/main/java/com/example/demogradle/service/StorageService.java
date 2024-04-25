package com.example.demogradle.service;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StorageService {

    @Value("${application.bucket.name}")
    private String bucketName;

    //@Autowired
    //private AmazonS3 s3Client;
    //Step 2:  Specify the profile to use and create an S3 client
    ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider("ProjectIAMProfile");
    private AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
            .withCredentials(credentialsProvider)
            .withRegion(Regions.US_WEST_2) // Update the region appropriately
            .build();

    public List<String> listAllFiles(){
        List<String> allFiles = new ArrayList<String>();
        ObjectListing objectListing = s3Client.listObjects(bucketName);
        for (S3ObjectSummary os : objectListing.getObjectSummaries()) {
            allFiles.add(os.getKey());
            System.out.println(os.getKey());
        }
        return allFiles;
    }

    public String uploadFile(MultipartFile file) {
        System.out.println(System.getProperty("user.dir"));

       /* Path filePath = Paths.get("path", "to", "7 Habits.png");
        if (Files.exists(filePath)) {
            // Proceed with your file processing
        } else {
            System.out.println("File does not exist: " + filePath.toAbsolutePath());
        }*/
        File fileObj = convertMultiPartFileToFile(file);
       // String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String fileName = file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();
        return "File uploaded : " + fileName;
    }


    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return fileName + " removed ...";
    }


    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        /*try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }*/
        return convertedFile;
    }
}
