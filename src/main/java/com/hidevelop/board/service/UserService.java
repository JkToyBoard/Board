package com.hidevelop.board.service;

import com.hidevelop.board.model.dto.UserDto;
import com.hidevelop.board.model.entity.User;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

    public User signUp (UserDto.SignUp signUpDto);

    public UserDto.SignInResponse signIn (UserDto.SignIn signInDto, HttpServletResponse response);


    public String getAccessTokenByUser(String accessToken);

    public String getUserInfo(String username);


}
