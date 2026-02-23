package com.project.lay_note_was.lay_note.service;

import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_project_composition.CompositionDto;
import com.project.lay_note_was.lay_note.dto.note_project_composition.request.CompositionPositionRequestDto;
import com.project.lay_note_was.lay_note.dto.note_project_composition.response.CompositionResponseDto;

import java.util.List;

public interface NoteProjectCompositionService {
    ResponseDto<List<CompositionDto>> getComposition(String userEmail, String noteProjectId);

    ResponseDto<CompositionResponseDto> updatePositionComposition(String userEmail, String noteCompositionId, String noteProjectId, CompositionPositionRequestDto dto);
}
