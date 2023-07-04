package com.hidevelop.board.controller;


import com.hidevelop.board.model.dto.BoardDto;
import com.hidevelop.board.model.entity.Board;
import com.hidevelop.board.service.Impl.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardServiceImpl boardServiceImpl;

    @PostMapping
    public ResponseEntity<?> saveBoard(@Valid @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                       @Valid @RequestPart(value = "request")BoardDto.Request request,
                                       Principal principal
    ){

        var result = boardServiceImpl.saveBoard(images, request, principal.getName());
        return ResponseEntity.ok(result);
    }

}
