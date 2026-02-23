package com.project.lay_note_was.lay_note.controller;

import com.project.lay_note_was.lay_note.common.constant.ApiMappingPattern;
import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_project_user.response.NoteProjectUserListResponseDto;
import com.project.lay_note_was.lay_note.dto.note_project_user.response.NoteProjectUserResponseDto;
import com.project.lay_note_was.lay_note.entity.note_project_user.UserRole;
import com.project.lay_note_was.lay_note.security.PrincipalUser;
import com.project.lay_note_was.lay_note.service.NoteProjectUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.NOTE_PROJECT_USER)
@RequiredArgsConstructor
public class NoteProjectUserController {
    private final NoteProjectUserService noteProjectUserService;

    private final String GET = "/user-list/{noteProjectId}";
    private final String GET_CHECK_MEMBER = "/check-member/{noteProjectId}";
    private final String PUT = "/authority-change/{noteProjectId}";

    @GetMapping(GET)
    public ResponseEntity<ResponseDto<NoteProjectUserListResponseDto>> getProjectJoinUser (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable String noteProjectId
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<NoteProjectUserListResponseDto> response = noteProjectUserService.getProjectJoinUser(userEmail, noteProjectId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping(GET_CHECK_MEMBER)
    public ResponseEntity<ResponseDto<Boolean>> isMember (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable String noteProjectId
    ){
        String userEmail = principalUser.getUsername();
        ResponseDto<Boolean> response = noteProjectUserService.isMember(userEmail, noteProjectId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping(PUT)
    public ResponseEntity<ResponseDto<NoteProjectUserResponseDto>> changeUserRole (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @RequestParam String memberEmail,
            @RequestParam UserRole userRole,
            @PathVariable String noteProjectId
            ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<NoteProjectUserResponseDto> response = noteProjectUserService.changeUserRole(userEmail, memberEmail, userRole, noteProjectId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }
}
