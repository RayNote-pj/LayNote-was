package com.project.lay_note_was.lay_note.controller;

import com.project.lay_note_was.lay_note.common.constant.ApiMappingPattern;
import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.NoteImageBoxListOneDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.request.NoteImageBoxRequestDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.response.NoteImageBoxResponseDto;
import com.project.lay_note_was.lay_note.security.PrincipalUser;
import com.project.lay_note_was.lay_note.service.NoteImageBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(ApiMappingPattern.NOTE_IMAGE_BOX)
@RequiredArgsConstructor
public class NoteImageBoxController {
    private final NoteImageBoxService noteImageBoxService;

    private final String POST = "/{noteProjectId}/create/{noteImageBoxListId}";
    private final String PUT_CAPTION = "/{noteProjectId}/update-caption/{noteImageBoxId}";
    private final String POST_IMG = "/{noteProjectId}/update-img/{noteImageBoxId}";
    private final String DELETE = "/{noteProjectId}/delete/{noteImageBoxId}";

    @PostMapping(POST)
    public ResponseEntity<ResponseDto<NoteImageBoxResponseDto>> createImageBox (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable String noteProjectId,
            @PathVariable Long noteImageBoxListId,
            @RequestBody NoteImageBoxRequestDto dto
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<NoteImageBoxResponseDto> response = noteImageBoxService.createImageBox(userEmail, noteProjectId, noteImageBoxListId, dto);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping(PUT_CAPTION)
    public ResponseEntity<ResponseDto<NoteImageBoxListOneDto>> updateCaption (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable String noteProjectId,
            @PathVariable Long noteImageBoxId,
            @RequestBody NoteImageBoxRequestDto dto
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<NoteImageBoxListOneDto> response = noteImageBoxService.updateCaption(userEmail, noteProjectId, noteImageBoxId, dto);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @PostMapping(POST_IMG)
    public ResponseEntity<ResponseDto<NoteImageBoxListOneDto>> updateImage (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable String noteProjectId,
            @PathVariable Long noteImageBoxId,
            @RequestPart MultipartFile imageUrl
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<NoteImageBoxListOneDto> response = noteImageBoxService.updateImage(userEmail, noteProjectId, noteImageBoxId, imageUrl);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping(DELETE)
    public ResponseEntity<ResponseDto<Void>> deleteImageBox (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable String noteProjectId,
            @PathVariable Long noteImageBoxId
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<Void> response = noteImageBoxService.deleteImageBox(userEmail, noteProjectId, noteImageBoxId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }
}
