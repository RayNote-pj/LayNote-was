package com.project.lay_note_was.lay_note.dto.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.lay_note_was.lay_note.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    private String userId;
    private String userEmail;
    @JsonProperty("nickName")
    private String nickName;
    private String userName;
    private String userPhone;
    private String profileImageUrl;
    private String token;
    private int exprTime;

    public LoginResponseDto(User user, String token, int exprTime) {
        this.userId = user.getUserId();
        this.userEmail = user.getUserEmail();
        this.nickName = user.getNickName();
        this.userName = user.getUserName();
        this.profileImageUrl = user.getProfileImageUrl();
        this.userPhone = user.getUserPhone();
        this.exprTime = exprTime;
        this.token = token;
    }
}
