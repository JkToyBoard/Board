package com.hidevelop.board.controller;


import com.hidevelop.board.model.dto.BoardDto;
import com.hidevelop.board.model.entity.Board;
import com.hidevelop.board.service.Impl.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    private final BoardServiceImpl boardService;

    @PostMapping
    public ResponseEntity<?> saveBoard(@Valid @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                       @Valid @RequestPart(value = "request")BoardDto.Request request,
                                       Principal principal
    ){

        var result = boardService.saveBoard(images, request, principal.getName());
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<?> readAllBoard(@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10) Pageable pageable){
        var result = boardService.readAllBoard(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/info")
    public ResponseEntity<?> readPerOneBoard(@RequestParam Long boardId){
        var result = boardService.readPerOneBoard(boardId);
        return ResponseEntity.ok(result);
    }


    @PutMapping
    public ResponseEntity<?> updateBoard(@Valid @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                       @Valid @RequestPart(value = "request")BoardDto.UpdateRequest request){
        var result = boardService.updateBoard(request, images);
        return ResponseEntity.ok(result);
    }
}
