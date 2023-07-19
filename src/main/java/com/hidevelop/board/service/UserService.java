package com.hidevelop.board.service;

import com.hidevelop.board.model.dto.UserDto;
import com.hidevelop.board.model.entity.User;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

    User signUp (UserDto.SignUp signUpDto);

    UserDto.SignInResponse signIn (UserDto.SignIn signInDto, HttpServletResponse response);


    String getAccessTokenByUser(String accessToken);

    String getUserInfo(String username);


}
