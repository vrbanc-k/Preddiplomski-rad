package hr.fer.zavrsni.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class StorageService {



    public void uploadFile(MultipartFile file ,String filename, String bucketName) {

        AWSCredentials credentials = new BasicAWSCredentials(
                "AKIAISN5DJNKCFHNB3AQ",
                "Pd7zkl9aX7xDR5hegt5chly6wxcIXgkmL3Ty/D9v"
        );

        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_CENTRAL_1)
                .build();

        try{
            InputStream is=file.getInputStream();
            s3client.putObject(new PutObjectRequest(bucketName,filename,is,new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead));

        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
