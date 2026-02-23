package com.project.lay_note_was.lay_note.service;

import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.response.NoteImageBoxListOneResponseDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.response.NoteImageBoxListResponseDto;

public interface NoteImageBoxListService {
    ResponseDto<Void> deleteImageBoxList(String userEmail, String noteProjectId, Long noteImageBoxListId);

    ResponseDto<NoteImageBoxListOneResponseDto> createImageBox(String userEmail, String noteProjectId);
}
