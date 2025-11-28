package com.nt.service.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.nt.config.AWSConfig;
import com.nt.service.FileUploadService;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

	private final S3Client s3Client;

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;


	
    @Override
    public String uploadFile(MultipartFile file) {
        String extension = file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        String key = UUID.randomUUID() + "." + extension;

        try {
        	
			/*System.out.println(bucketName+"backet Name is Printing");
			System.out.println(region+" is Pritnting");
			System.out.println(key +"is Printing ");*/
            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket("software-building-name12")
                    .key(key)
                    .acl("public-read")
                    .contentType(file.getContentType())
                    .build();

            PutObjectResponse response = 
                    s3Client.putObject(putRequest, RequestBody.fromBytes(file.getBytes()));

            if (response.sdkHttpResponse().isSuccessful()) {
                return   "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + key;
                
            }

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error while uploading file");

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error while uploading file");
        }
    }

	@Override
	public boolean deleteFile(String imgUrl) {

		String fileName = imgUrl.substring(imgUrl.lastIndexOf("/")+1);
		DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
				.bucket(bucketName)
				.key(fileName)
				.build();
		s3Client.deleteObject(deleteObjectRequest);
		
		return true;
	}

}
