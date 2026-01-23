package com.project.lay_note_was.lay_note.service;

import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_list.request.NoteListRequestDto;
import com.project.lay_note_was.lay_note.dto.note_list.response.NoteListOneResponseDto;
import com.project.lay_note_was.lay_note.dto.note_list.response.NoteListResponseDto;

public interface NoteListService {

    ResponseDto<NoteListOneResponseDto> createNoteList(String userEmail, String noteProjectId);

    ResponseDto<NoteListResponseDto> getNoteList(String userEmail, String noteProjectId);

    ResponseDto<NoteListOneResponseDto> updateNoteList(String userEmail, NoteListRequestDto dto, Long noteListId, String noteProjectId);

    ResponseDto<Void> deleteNoteList(String userEmail, Long noteListId, String noteProjectId);

}
