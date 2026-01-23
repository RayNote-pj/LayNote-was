package com.project.lay_note_was.lay_note.controller;

import com.project.lay_note_was.lay_note.common.constant.ApiMappingPattern;
import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.user.request.UserDeleteRequestDto;
import com.project.lay_note_was.lay_note.dto.user.request.UserUpdateRequestDto;
import com.project.lay_note_was.lay_note.dto.user.response.UserResponseDto;
import com.project.lay_note_was.lay_note.security.PrincipalUser;
import com.project.lay_note_was.lay_note.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final String MY_PAGE_GET = "/my-page";
    private final String ACCOUNT_PUT = "/account-update";
    private final String ACCOUNT_DELETE = "/account-delete";

    @GetMapping(MY_PAGE_GET)
    public ResponseEntity<ResponseDto<UserResponseDto>> getUserInfo (
            @AuthenticationPrincipal PrincipalUser principalUser
    ) {

        String userEmail = principalUser.getUsername();
        ResponseDto<UserResponseDto> response = userService.getMyAccount(userEmail);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping(ACCOUNT_PUT)
    public ResponseEntity<ResponseDto<UserResponseDto>> updateUserInfo (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @ModelAttribute UserUpdateRequestDto dto
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<UserResponseDto> response = userService.updateMyAccount(userEmail, dto);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping(ACCOUNT_DELETE)
    public ResponseEntity<ResponseDto<Void>> deleteUser (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @RequestBody UserDeleteRequestDto dto
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<Void> response = userService.deleteUser(userEmail, dto);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

}
