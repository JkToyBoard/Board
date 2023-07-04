package com.hidevelop.board.model.dto;

import com.hidevelop.board.model.entity.User;
import com.hidevelop.board.model.type.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class UserDto {

    @Getter
    @Setter
    public static class SignUp{

        @NotBlank
        private String username;
        @NotBlank
        private String password;

        public User toEntity(String password){
            return User.builder()
                    .username(this.username)
                    .password(password)
                    .role(Role.USER)
                    .build();
        }


    }

    @Getter
    public static class SignIn{
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }
}
