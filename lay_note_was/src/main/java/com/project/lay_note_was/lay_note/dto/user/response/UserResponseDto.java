package com.project.lay_note_was.lay_note.dto.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.lay_note_was.lay_note.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private String userId;
    private String userEmail;
    private String userName;
    private String nickName;
    private String userPhone;
    @JsonProperty("profileImageUrl")
    private String profileImageUrl;

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.userEmail = user.getUserEmail();
        this.userName = user.getUserName();
        this.nickName = user.getNickName();
        this.userPhone = user.getUserPhone();
        this.profileImageUrl = user.getProfileImageUrl();
    }
}
