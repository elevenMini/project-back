package com.eleven.miniproject.board.createBoard.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.eleven.miniproject.board.entity.UploadImage;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@NoArgsConstructor
@Transactional
public class S3Service {
    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.bucket.folder}")
    private String bucketFolder;

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
        if (file == null) {
            return null;
        }

        String fileName = file.getOriginalFilename();

        String storeImageName = createStoreImageName(fileName);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentDisposition("inline");


        s3Client.putObject(new PutObjectRequest(bucket, getKeyFromImageName(storeImageName), file.getInputStream(), objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return new UploadImage(fileName, s3Client.getUrl(bucket, getKeyFromImageName(storeImageName)).toString());
    }

    public void deleteImage(String currentStoredImageName) {
        if (!s3Client.doesObjectExist(bucket, extractKeyFromFullPath(currentStoredImageName))) {
            throw new IllegalArgumentException("변경할 이미지가 없습니다");
        }
        extractKeyFromFullPath(currentStoredImageName);
        s3Client.deleteObject(bucket, extractKeyFromFullPath(currentStoredImageName));
    }

    public UploadImage updateImage(String currentStoredImageName, MultipartFile image) throws IOException {

        deleteImage(currentStoredImageName);

        return upload(image);
    }


    private String getKeyFromImageName(String storeImageName) {
        return bucketFolder + "/" + storeImageName;
    }
    private String extractKeyFromFullPath(String currentStoredImageName) {
        return currentStoredImageName.substring(currentStoredImageName.lastIndexOf("/") - 11);
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
