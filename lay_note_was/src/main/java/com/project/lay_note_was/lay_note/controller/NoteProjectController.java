package com.project.lay_note_was.lay_note.controller;

import com.project.lay_note_was.lay_note.common.constant.ApiMappingPattern;
import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_project.request.NoteProjectCreateRequestDto;
import com.project.lay_note_was.lay_note.dto.note_project.request.NoteProjectImageRequestDto;
import com.project.lay_note_was.lay_note.dto.note_project.request.NoteProjectUpdateRequestDto;
import com.project.lay_note_was.lay_note.dto.note_project.response.NoteProjectListResponseDto;
import com.project.lay_note_was.lay_note.dto.note_project.response.NoteProjectResponseDto;
import com.project.lay_note_was.lay_note.security.PrincipalUser;
import com.project.lay_note_was.lay_note.service.NoteProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(ApiMappingPattern.NOTE_PROJECT)
@RequiredArgsConstructor
public class NoteProjectController {
    private final NoteProjectService noteProjectService;

    private final String GET_ALL = "/all";
    private final String GET_ONE = "/{noteProjectId}";
    private final String GET_BASKET = "/waste-basket";
    private final String CREATE = "/create";
    private final String UPDATE_TITLE = "/{noteProjectId}/update/title";
    private final String UPDATE_IMAGE = "/{noteProjectId}/update/image";
    private final String DELETE = "/{noteProjectId}/delete";
    private final String DELETE_DATE = "/waste-basket/{noteProjectId}/deleteAt";
    private final String DELETE_COMPLETE = "/{noteProjectId}/complete-delete";

    @GetMapping(GET_ALL)
    public ResponseEntity<ResponseDto<NoteProjectListResponseDto>> getNoteProjectAll (
            @AuthenticationPrincipal PrincipalUser principalUser
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<NoteProjectListResponseDto> response = noteProjectService.getNoteProjectAll(userEmail);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping(GET_ONE)
    public ResponseEntity<ResponseDto<NoteProjectResponseDto>> getNoteProjectOne (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable String noteProjectId
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<NoteProjectResponseDto> response = noteProjectService.getNoteProjectOne(userEmail, noteProjectId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping(GET_BASKET)
    public ResponseEntity<ResponseDto<NoteProjectListResponseDto>> getWasteBasket (
            @AuthenticationPrincipal PrincipalUser principalUser
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<NoteProjectListResponseDto> response = noteProjectService.getWasteBasket(userEmail);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @PostMapping(CREATE)
    public ResponseEntity<ResponseDto<NoteProjectResponseDto>> createNoteProject (
            @AuthenticationPrincipal PrincipalUser principalUser
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<NoteProjectResponseDto> response = noteProjectService.createNoteProject(userEmail);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping(UPDATE_TITLE)
    public ResponseEntity<ResponseDto<NoteProjectResponseDto>> updateNoteProjectTitle (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable String noteProjectId,
            @RequestBody NoteProjectUpdateRequestDto dto
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<NoteProjectResponseDto> response = noteProjectService.updateNoteProjectTitle(userEmail, noteProjectId, dto);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @PostMapping(UPDATE_IMAGE)
    public ResponseEntity<ResponseDto<NoteProjectResponseDto>> upDateNoteProjectImage (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable String noteProjectId,
            @RequestPart MultipartFile noteProjectImageUrl
            ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<NoteProjectResponseDto> response = noteProjectService.updateNoteProjectImage(userEmail, noteProjectId, noteProjectImageUrl);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping(DELETE)
    public ResponseEntity<ResponseDto<NoteProjectResponseDto>> updateDeleteAt (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable String noteProjectId
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<NoteProjectResponseDto> response = noteProjectService.updateDeleteAt(userEmail, noteProjectId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping(DELETE_DATE)
    public ResponseEntity<ResponseDto<NoteProjectResponseDto>> deleteDeleteAt (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable String noteProjectId
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<NoteProjectResponseDto> response = noteProjectService.deleteDeleteAt(userEmail, noteProjectId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping(DELETE_COMPLETE)
    public ResponseEntity<ResponseDto<Void>> deleteNoteProject (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable String noteProjectId
    ) {
        String userEmail = principalUser.getUsername();
        ResponseDto<Void> response = noteProjectService.deleteNoteProject(userEmail, noteProjectId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }


}
