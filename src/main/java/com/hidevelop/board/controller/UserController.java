package com.hidevelop.board.controller;

import com.hidevelop.board.model.dto.UserDto;
import com.hidevelop.board.model.entity.User;
import com.hidevelop.board.service.Impl.UserServiceImpl;
import com.hidevelop.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody @Valid UserDto.SignUp request) {
        return ResponseEntity.ok(userService.signUp(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<UserDto.SignInResponse> signIn(@RequestBody @Valid UserDto.SignIn request, HttpServletResponse response) {
        return ResponseEntity.ok(userService.signIn(request, response));
    }

    /**
     * Refresh Token 확인 후 유효한 토큰이면
     * Access Token 재발급 해주는 로직
     * *** Refresh Token 은 재발급 안됨 ! ( 보안 > 편의 ) ***
     */
    @GetMapping("/token")
    public ResponseEntity<String> getAccessToken(@RequestParam @Valid String refreshToken) {
        return ResponseEntity.ok(userService.getAccessTokenByUser(refreshToken));
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestParam @Valid String userId) {
        return ResponseEntity.ok(userService.getUserInfo(userId));
    }

}
