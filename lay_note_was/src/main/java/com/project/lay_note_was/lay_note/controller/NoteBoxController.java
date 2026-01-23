package com.project.lay_note_was.lay_note.controller;

import com.project.lay_note_was.lay_note.common.constant.ApiMappingPattern;
import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_box.request.NoteBoxCreateRequestDto;
import com.project.lay_note_was.lay_note.dto.note_box.request.NoteBoxUpdateRequestDto;
import com.project.lay_note_was.lay_note.dto.note_box.response.NoteBoxListResponseDto;
import com.project.lay_note_was.lay_note.dto.note_box.response.NoteBoxResponseDto;
import com.project.lay_note_was.lay_note.security.PrincipalUser;
import com.project.lay_note_was.lay_note.service.NoteBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(ApiMappingPattern.NOTE_BOX)
@RequiredArgsConstructor
public class NoteBoxController {
    private final NoteBoxService noteBoxService;

    private final String POST = "/{noteProjectId}/create";
    private final String PUT = "/{noteProjectId}/update/{noteBoxId}";
    private final String PUT_IMG = "/{noteProjectId}/update-img/{noteBoxId}";
    private final String DELETE = "/{noteProjectId}/delete/{noteBoxId}";
    private final String GET = "/{noteProjectId}/all";

    @PostMapping(POST)
    public ResponseEntity<ResponseDto<NoteBoxResponseDto>> createNoteBox (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable String noteProjectId
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<NoteBoxResponseDto> response = noteBoxService.createNoteBox(userEmail, noteProjectId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping(PUT)
    public ResponseEntity<ResponseDto<NoteBoxResponseDto>> updateNoteBox (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable String noteProjectId,
            @RequestBody NoteBoxUpdateRequestDto dto,
            @PathVariable Long noteBoxId
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<NoteBoxResponseDto> response = noteBoxService.updateNoteBox(userEmail, noteProjectId, dto, noteBoxId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping(PUT_IMG)
    public ResponseEntity<ResponseDto<NoteBoxResponseDto>> updateNoteBoxImg (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable String noteProjectId,
            @PathVariable Long noteBoxId,
            @RequestPart MultipartFile imageUrl
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<NoteBoxResponseDto> response = noteBoxService.updateNoteBoxImg(userEmail, noteProjectId, imageUrl, noteBoxId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping(GET)
    public ResponseEntity<ResponseDto<NoteBoxListResponseDto>> getNoteBox (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable String noteProjectId
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<NoteBoxListResponseDto> response = noteBoxService.getNoteBox(userEmail, noteProjectId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping(DELETE)
    public ResponseEntity<ResponseDto<Void>> deleteNoteBox (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable String noteProjectId,
            @PathVariable Long noteBoxId
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<Void>response = noteBoxService.deleteNoteBox(userEmail, noteProjectId, noteBoxId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }
}
