package com.project.lay_note_was.lay_note.controller;

import com.project.lay_note_was.lay_note.common.constant.ApiMappingPattern;
import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.response.NoteImageBoxListOneResponseDto;
import com.project.lay_note_was.lay_note.security.PrincipalUser;
import com.project.lay_note_was.lay_note.service.NoteImageBoxListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.NOTE_IMAGE_BOX_List)
@RequiredArgsConstructor
public class NoteImageBoxListController {
    private final NoteImageBoxListService noteImageBoxListService;

    private final String POST = "/{noteProjectId}/create";
    private final String DELETE = "/{noteProjectId}/delete/{noteImageBoxListId}";

    @PostMapping(POST)
    public ResponseEntity<ResponseDto<NoteImageBoxListOneResponseDto>> createImageBox (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable String noteProjectId
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<NoteImageBoxListOneResponseDto> response = noteImageBoxListService.createImageBox(userEmail, noteProjectId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping(DELETE)
    public ResponseEntity<ResponseDto<Void>> deleteImageBoxList (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable String noteProjectId,
            @PathVariable Long noteImageBoxListId
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<Void> response = noteImageBoxListService.deleteImageBoxList(userEmail, noteProjectId, noteImageBoxListId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }
}
