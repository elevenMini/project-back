package com.eleven.miniproject.board.createBoard.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.eleven.miniproject.board.entity.UploadImage;
import com.eleven.miniproject.board.util.FileUtil;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@NoArgsConstructor
public class S3Service {
    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    public UploadImage upload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        String storeImageName = createStoreImageName(fileName);

        s3Client.putObject(new PutObjectRequest(bucket, storeImageName, file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return new UploadImage(fileName, s3Client.getUrl(bucket, storeImageName).toString());
    }

    private String createStoreImageName(String originalImageName) {
        String extName = extractExt(originalImageName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + extName;
    }

    private String extractExt(String originalImageName) {
        int pos = originalImageName.lastIndexOf(".");
        String extName = originalImageName.substring(pos + 1);
        return extName;
    }
}