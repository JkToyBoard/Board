package com.hidevelop.board.model.repo;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hidevelop.board.utile.MultipartUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Component
@RequiredArgsConstructor
public class AmazonS3ResourceStorage {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;
    public void imageStore(String fullPath, MultipartFile multipartFile) {
        String path = MultipartUtil.getLocalHomeDirectory()+File.separator+fullPath; // mac = /
        File file = new File(path);


        try {
            System.out.println(path);
            multipartFile.transferTo(file);
            amazonS3Client.putObject(new PutObjectRequest(bucket, "images/"+fullPath, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            throw new IllegalArgumentException("MultiPart -> File 전환 실패 ");
        } finally {
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
