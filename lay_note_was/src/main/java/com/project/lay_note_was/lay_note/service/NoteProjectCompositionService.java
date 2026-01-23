package com.project.lay_note_was.lay_note.service;

import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_project_composition.CompositionDto;
import com.project.lay_note_was.lay_note.dto.note_project_composition.request.CompositionPositionRequestDto;
import com.project.lay_note_was.lay_note.dto.note_project_composition.request.CompositionRequestDto;
import com.project.lay_note_was.lay_note.dto.note_project_composition.request.CompositionSizeRequestDto;
import com.project.lay_note_was.lay_note.dto.note_project_composition.response.CompositionResponseDto;

import java.util.List;

public interface NoteProjectCompositionService {
    ResponseDto<List<CompositionDto>> getComposition(String userEmail, String noteProjectId);

    ResponseDto<CompositionResponseDto> updateSizeComposition(String userEmail, String noteCompositionId, Long noteComponentId, String noteProjectId, CompositionSizeRequestDto dto);

    ResponseDto<CompositionResponseDto> updatePositionComposition(String userEmail, String noteCompositionId, Long noteComponentId, String noteProjectId, CompositionPositionRequestDto dto);
}
