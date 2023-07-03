package com.hidevelop.board.service;

import com.hidevelop.board.model.dto.BoardDto;
import com.hidevelop.board.model.repo.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void saveBoard(List<MultipartFile> images, BoardDto.Request request){

    }
}
