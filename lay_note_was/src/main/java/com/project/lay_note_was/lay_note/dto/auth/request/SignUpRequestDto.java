package com.project.lay_note_was.lay_note.dto.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank(message = "이메일은 필수입니다.")
    private String userEmail;
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
    @NotBlank(message = "비밀번호확인은 필수입니다.")
    private String confirmPassword;
    @NotBlank(message = "사용자 이름은 필수입니다.")
    private String userName;
    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickName;
    @NotBlank(message = "사용자 전화번호는 필수입니다.")
    private String userPhone;
    private String profileImageUrl;
    private String snsId;
    private String joinPath;

}
