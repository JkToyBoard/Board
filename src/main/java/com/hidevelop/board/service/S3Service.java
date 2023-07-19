package com.hidevelop.board.service;


import com.hidevelop.board.model.entity.Board;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface S3Service {
    List<String> uploadImage(List<MultipartFile> multipartFiles);

    void deleteImage(Board board);
}
