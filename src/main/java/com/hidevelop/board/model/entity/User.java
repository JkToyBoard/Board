package com.hidevelop.board.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hidevelop.board.model.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class User extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToMany(fetch = FetchType.LAZY)
    private Set<Board> boards;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Comment> comments;

}
