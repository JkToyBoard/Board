package com.hidevelop.board.service.Impl;

import com.hidevelop.board.exception.error.AuthenticationException;
import com.hidevelop.board.exception.message.AuthErrorMessage;
import com.hidevelop.board.model.dto.BoardDto;
import com.hidevelop.board.model.entity.Board;
import com.hidevelop.board.model.entity.User;
import com.hidevelop.board.model.entity.ViewCount;
import com.hidevelop.board.model.repo.BoardRepository;
import com.hidevelop.board.model.repo.UserRepository;
import com.hidevelop.board.model.repo.ViewCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl {

    private final BoardRepository boardRepository;
    private final ViewCountRepository viewCountRepository;
    private final UserRepository userRepository;
    private final S3ServiceImpl s3ServiceImpl;

    /**
     * 게시판 글 등록
     * @param files (이미지 파일)
     * @param request (게시글 제목 , 내용)
     * @param username (작성자)
     * @return
     */
    public Board saveBoard(List<MultipartFile> files, BoardDto.Request request, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow( () -> new AuthenticationException(AuthErrorMessage.USER_NOT_FOUND));

        List<String> images = s3ServiceImpl.uploadImage(files);

        ViewCount viewCount = viewCountRepository.save(ViewCount.builder().viewCount(0l).build());


        return boardRepository.save(request.toEntity(images, viewCount, user.getUsername()));

    }

    /**
     * 전체 게시글 조회 내림차순이라구~~~!
     * @param pageable (1페이지당 10개)
     * @return
     */
    public Page<BoardDto.simpleBoard> readAllBoard(Pageable pageable){
        Page<Board> boards = boardRepository.findAll(pageable);


        return toSimpleBoardDto(boards);
    }

    /**
     * 페이지 Entity를 필요한 자원만 있는 페이지 DTO로 쏙쏙
     * @param boards
     * @return
     */
    public Page<BoardDto.simpleBoard> toSimpleBoardDto(Page<Board> boards) {
        Page<BoardDto.simpleBoard> simpleBoards =
                boards.map(
                        m -> BoardDto.simpleBoard.builder()
                                .id(m.getId())
                                .title(m.getTitle())
                                .writer(m.getWriter())
                                .viewCount(m.getViewCount().getViewCount())
                                .updateAt(m.getUpdatedAt())
                                .build()
                );

        return simpleBoards;
    }
}
