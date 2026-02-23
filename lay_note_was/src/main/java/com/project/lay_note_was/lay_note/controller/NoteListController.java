package com.project.lay_note_was.lay_note.controller;

import com.project.lay_note_was.lay_note.common.constant.ApiMappingPattern;
import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_list.request.NoteListRequestDto;
import com.project.lay_note_was.lay_note.dto.note_list.response.NoteListOneResponseDto;
import com.project.lay_note_was.lay_note.security.PrincipalUser;
import com.project.lay_note_was.lay_note.service.NoteListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.NOTE_LIST)
@RequiredArgsConstructor
public class NoteListController {
    private final NoteListService noteListService;

    private final String POST = "/{noteProjectId}/create";
    private final String PUT = "/{noteProjectId}/update/{noteListId}";
    private final String DELETE = "{noteProjectId}/delete/{noteListId}";

    @PostMapping(POST)
    public ResponseEntity<ResponseDto<NoteListOneResponseDto>> crateNoteList (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable String noteProjectId
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<NoteListOneResponseDto> response = noteListService.createNoteList(userEmail, noteProjectId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping(PUT)
    public ResponseEntity<ResponseDto<NoteListOneResponseDto>> updateNoteList (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @RequestBody NoteListRequestDto dto,
            @PathVariable Long noteListId,
            @PathVariable String noteProjectId
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<NoteListOneResponseDto> response = noteListService.updateNoteList(userEmail, dto, noteListId, noteProjectId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping(DELETE)
    public ResponseEntity<ResponseDto<Void>> deleteNoteList (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable String noteProjectId,
            @PathVariable Long noteListId
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<Void> response = noteListService.deleteNoteList(userEmail, noteListId, noteProjectId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }
}
