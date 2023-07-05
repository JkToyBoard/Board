package com.hidevelop.board.service;

import com.hidevelop.board.exception.error.ApplicationException;
import com.hidevelop.board.exception.error.AuthenticationException;
import com.hidevelop.board.exception.message.ApplicationErrorMessage;
import com.hidevelop.board.exception.message.AuthErrorMessage;
import com.hidevelop.board.model.dto.BoardDto;
import com.hidevelop.board.model.entity.Board;
import com.hidevelop.board.model.entity.User;
import com.hidevelop.board.model.entity.ViewCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {


    public BoardDto.Response saveBoard(List<MultipartFile> files, BoardDto.Request request, String username);

    public Page<BoardDto.simpleBoard> readAllBoard(Pageable pageable);

    public Page<BoardDto.simpleBoard> toSimpleBoardDto(Page<Board> boards);

    public BoardDto.Response readPerOneBoard(Long boardId);

    public BoardDto.Response updateBoard(BoardDto.UpdateRequest request, List<MultipartFile> files);

    public void deleteBoard(Long boardId);
}

