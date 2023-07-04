package com.hidevelop.board.service;

import com.hidevelop.board.model.dto.FileDetail;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface S3Service {
    public List<String> uploadImage(List<MultipartFile> multipartFiles);
}
