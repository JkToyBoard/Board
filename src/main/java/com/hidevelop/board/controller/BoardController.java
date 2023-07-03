package com.hidevelop.board.controller;


import com.hidevelop.board.model.dto.BoardDto;
import com.hidevelop.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<?> saveBoard(@Valid @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                       @Valid @RequestPart(value = "request")BoardDto.Request request){
        boardService.saveBoard(images, request);
        return ResponseEntity.ok("result");
    }

}
