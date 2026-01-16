package com.project.lay_note_was.lay_note.service;

import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_project.request.NoteProjectCreateRequestDto;
import com.project.lay_note_was.lay_note.dto.note_project.request.NoteProjectImageRequestDto;
import com.project.lay_note_was.lay_note.dto.note_project.request.NoteProjectUpdateRequestDto;
import com.project.lay_note_was.lay_note.dto.note_project.response.NoteProjectListResponseDto;
import com.project.lay_note_was.lay_note.dto.note_project.response.NoteProjectResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface NoteProjectService {
    ResponseDto<NoteProjectListResponseDto> getNoteProjectAll(String userEmail);

    ResponseDto<NoteProjectResponseDto> getNoteProjectOne(String userEmail, String noteProjectId);

    ResponseDto<NoteProjectListResponseDto> getWasteBasket(String userEmail);

    ResponseDto<NoteProjectResponseDto> createNoteProject(String userEmail);

    ResponseDto<NoteProjectResponseDto> updateNoteProjectTitle(String userEmail, String noteProjectId, NoteProjectUpdateRequestDto dto);

    ResponseDto<NoteProjectResponseDto> updateNoteProjectImage(String userEmail, String noteProjectId, MultipartFile noteProjectImageUrl);

    ResponseDto<NoteProjectResponseDto> updateDeleteAt(String userEmail, String noteProjectId);

    ResponseDto<NoteProjectResponseDto> deleteDeleteAt(String userEmail, String noteProjectId);

    ResponseDto<Void> deleteNoteProject(String userEmail, String noteProjectId);
}
