package com.hidevelop.board.service.Impl;

import com.hidevelop.board.exception.error.ApplicationException;
import com.hidevelop.board.exception.error.AuthenticationException;
import com.hidevelop.board.exception.message.ApplicationErrorMessage;
import com.hidevelop.board.exception.message.AuthErrorMessage;
import com.hidevelop.board.model.dto.BoardDto;
import com.hidevelop.board.model.dto.CommentDto;
import com.hidevelop.board.model.entity.Board;
import com.hidevelop.board.model.entity.User;
import com.hidevelop.board.model.entity.ViewCount;
import com.hidevelop.board.model.repo.BoardRepository;
import com.hidevelop.board.model.repo.UserRepository;
import com.hidevelop.board.model.repo.ViewCountRepository;
import com.hidevelop.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@org.springframework.transaction.annotation.Transactional(isolation = Isolation.READ_COMMITTED)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ViewCountRepository viewCountRepository;
    private final UserRepository userRepository;
    private final S3ServiceImpl s3Service;

    /**
     * 게시판 글 등록
     *
     * @param files    (이미지 파일)
     * @param request  (게시글 제목 , 내용)
     * @param username (작성자)
     * @return
     */
    public BoardDto.Response saveBoard(List<MultipartFile> files, BoardDto.Request request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationException(AuthErrorMessage.USER_NOT_FOUND));

        ViewCount viewCount = viewCountRepository.save(ViewCount.builder().viewCount(0l).build());

        List<String> images = new ArrayList<>();
        Board board = new Board();

        if(files == null) {
            board = boardRepository.save(request.toEntity(viewCount, user.getUsername()));
        } else {
            images = s3Service.uploadImage(files);
            board = boardRepository.save(request.toEntity(images, viewCount, user.getUsername()));
        }

        return board.Of();
    }

    /**
     * 전체 게시글 조회 내림차순이라구~~~!
     *
     * @param pageable (1페이지당 10개)
     * @return
     */
    public Page<BoardDto.simpleBoard> readAllBoard(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);


        return toSimpleBoardDto(boards);
    }

    /**
     * 페이지 Entity를 필요한 자원만 있는 페이지 DTO로 쏙쏙
     *
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

    /**
     * 게시글 하나 조회~!
     *
     * @param boardId
     * @return
     */

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public BoardDto.Response readPerOneBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ApplicationException(ApplicationErrorMessage.NOT_REGISTERED_BOARD));
        board.getViewCount().updateViewCount();
        viewCountRepository.save(board.getViewCount());

        return board.Of();
    }

    /**
     * 게시글 수정
     *
     * @param request
     * @param files
     * @return
     */
    public BoardDto.Response updateBoard(BoardDto.UpdateRequest request, List<MultipartFile> files) {
        Board board = boardRepository.findById(request.getId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorMessage.NOT_REGISTERED_BOARD));

        List<String> images = new ArrayList<>();
        s3Service.deleteImage(board);
        if (files != null){
            images = s3Service.uploadImage(files);
        }
        board.update(request, images);
        Board resultBoard = boardRepository.save(board);
        return resultBoard.Of();
    }

    /**
     * 게시글 삭제
     *
     * @param boardId
     */
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ApplicationException(ApplicationErrorMessage.NOT_REGISTERED_BOARD));

        s3Service.deleteImage(board);
        boardRepository.deleteById(boardId);
    }

}
