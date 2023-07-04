package com.hidevelop.board.service.Impl;


import com.hidevelop.board.config.CookieProvider;
import com.hidevelop.board.exception.error.AuthenticationException;
import com.hidevelop.board.model.dto.UserDto;
import com.hidevelop.board.model.entity.User;
import com.hidevelop.board.model.repo.UserRepository;
import com.hidevelop.board.security.JwtTokenProvider;
import com.hidevelop.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.hidevelop.board.exception.message.AuthErrorMessage.*;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final CookieProvider cookieProvider;
    private final Long refreshTokenValidTime = 2 * 24 * 60 * 60 * 1000L;


    public User signUp (UserDto.SignUp signUpDto) {

        if (userRepository.findByUsername(signUpDto.getUsername()).isPresent()) {
            throw new AuthenticationException(ALREADY_REGISTERED);
        }

        return userRepository.save(signUpDto.toEntity(passwordEncoder.encode(signUpDto.getPassword())));
    }

    public String signIn (UserDto.SignIn signInDto, HttpServletResponse response) {
        User user = userRepository.findByUsername(signInDto.getUsername())
                .orElseThrow(() -> new AuthenticationException(USER_NOT_FOUND));

        if(!passwordEncoder.matches(signInDto.getPassword(), user.getPassword())) {
            throw new AuthenticationException(MISMATCH_PASSWORD);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword()));


        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);
        cookieProvider.setRefreshTokenCookie(refreshToken, response);

        redisTemplate.opsForValue().set(
                authentication.getName(),
                refreshToken,
                refreshTokenValidTime,
                TimeUnit.MILLISECONDS);

        return user.getRoleKey() + accessToken;
    }


    public String getAccessTokenByUser(String accessToken) {
        String username = jwtTokenProvider.getPayloadSub(accessToken);
        Optional<User> user = userRepository.findByUsername(username);
        String password = user.get().getPassword();
        if(!redisTemplate.opsForValue().get(username).isEmpty()) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            return user.get().getRoleKey() + jwtTokenProvider.generateAccessToken(authentication);
        }
        throw new AuthenticationException(INVALID_TOKEN);
    }

    public User getUserInfo(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationException(USER_NOT_FOUND));
    }

    public User getUserIdByAccessToken(String accessToken) {
        String payload = jwtTokenProvider.getPayloadSub(accessToken);
        return getUserInfo(payload);
    }

}