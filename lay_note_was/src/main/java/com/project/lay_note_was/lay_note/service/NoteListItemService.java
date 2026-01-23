package com.project.lay_note_was.lay_note.service;

import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_list.request.NoteListItemRequestDto;
import com.project.lay_note_was.lay_note.dto.note_list.response.NoteListItemResponseDto;

public interface NoteListItemService {
    ResponseDto<NoteListItemResponseDto> createNoteListItem(String userEmail, String noteProjectId, Long noteListId);

    ResponseDto<NoteListItemResponseDto> updateNoteListItem(String userEmail, String noteProjectId, Long noteListId, Long noteListItemId, NoteListItemRequestDto dto);

    ResponseDto<Void> deleteNoteListItem(String userEmail, Long noteListItemId, String noteProjectId);
}
