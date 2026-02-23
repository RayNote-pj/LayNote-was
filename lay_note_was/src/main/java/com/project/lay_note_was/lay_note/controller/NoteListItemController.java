package com.project.lay_note_was.lay_note.controller;

import com.project.lay_note_was.lay_note.common.constant.ApiMappingPattern;
import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_list.request.NoteListItemRequestDto;
import com.project.lay_note_was.lay_note.dto.note_list.response.NoteListItemResponseDto;
import com.project.lay_note_was.lay_note.security.PrincipalUser;
import com.project.lay_note_was.lay_note.service.NoteListItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.NOTE_LIST_ITEM)
@RequiredArgsConstructor
public class NoteListItemController {
    private final NoteListItemService noteListItemService;

    private final String POST = "/{noteProjectId}/create/{noteListId}";
    private final String PUT = "/{noteProjectId}/update/{noteListItemId}";
    private final String CHECK_PUT = "/{noteProjectId}/check/{noteListItemId}";
    private final String DELETE = "/{noteProjectId}/delete/{noteListItemId}";

    @PostMapping(POST)
    public ResponseEntity<ResponseDto<NoteListItemResponseDto>> createNoteListItem (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable Long noteListId,
            @PathVariable String noteProjectId
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<NoteListItemResponseDto> response = noteListItemService.createNoteListItem(userEmail, noteProjectId, noteListId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping(CHECK_PUT)
    public ResponseEntity<ResponseDto<NoteListItemResponseDto>> updateCheck (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable Long noteListItemId,
            @PathVariable String noteProjectId,
            @RequestParam Boolean noteListCheck
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<NoteListItemResponseDto> response = noteListItemService.updateCheck(userEmail, noteProjectId, noteListItemId, noteListCheck);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping(PUT)
    public ResponseEntity<ResponseDto<NoteListItemResponseDto>> updateNoteListItem (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable Long noteListItemId,
            @PathVariable String noteProjectId,
            @RequestBody NoteListItemRequestDto dto
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<NoteListItemResponseDto> response = noteListItemService.updateNoteListItem(userEmail, noteProjectId, noteListItemId, dto);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping(DELETE)
    public ResponseEntity<ResponseDto<Void>> deleteNoteListItem (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable String noteProjectId,
            @PathVariable Long noteListItemId
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<Void> response = noteListItemService.deleteNoteListItem(userEmail, noteListItemId, noteProjectId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }
}
