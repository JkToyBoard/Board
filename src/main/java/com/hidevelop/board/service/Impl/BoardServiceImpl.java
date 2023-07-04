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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl {

    private final BoardRepository boardRepository;
    private final ViewCountRepository viewCountRepository;
    private final UserRepository userRepository;
    private final S3ServiceImpl s3ServiceImpl;

    public Board saveBoard(List<MultipartFile> files, BoardDto.Request request, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow( () -> new AuthenticationException(AuthErrorMessage.USER_NOT_FOUND));

        List<String> images = s3ServiceImpl.uploadImage(files);

        ViewCount viewCount = viewCountRepository.save(ViewCount.builder().viewCount(0l).build());


        return boardRepository.save(request.toEntity(images, viewCount, user.getUsername()));

    }
}
