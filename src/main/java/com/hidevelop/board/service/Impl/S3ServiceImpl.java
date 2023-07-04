package com.hidevelop.board.service.Impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.hidevelop.board.model.dto.FileDetail;
import com.hidevelop.board.model.repo.AmazonS3ResourceStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final AmazonS3ResourceStorage amazonS3ResourceStorage;
    private final AmazonS3Client amazonS3Client;

    /**
     * S3 이미지 여러장 업로드
     * @param multipartFiles
     * @return
     */
    public List<String> uploadImage(List<MultipartFile> multipartFiles) {
        List<String> images = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles){
            FileDetail fileDetail = FileDetail.multiPartOf(multipartFile);
            String path = fileDetail.getId()+"."+fileDetail.getFormat();
            System.out.println(path);
            amazonS3ResourceStorage.imageStore(path, multipartFile);
            String dbpath = "images/"+fileDetail.getId()+"."+fileDetail.getFormat();
            images.add(String.valueOf(amazonS3Client.getUrl(bucketName,dbpath)));
        }

        return images;
    }
}
