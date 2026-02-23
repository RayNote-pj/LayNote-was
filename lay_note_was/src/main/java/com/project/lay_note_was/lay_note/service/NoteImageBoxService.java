package com.project.lay_note_was.lay_note.service;

import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.NoteImageBoxListOneDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.request.NoteImageBoxRequestDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.response.NoteImageBoxResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface NoteImageBoxService {
    ResponseDto<NoteImageBoxResponseDto> createImageBox(String userEmail, String noteProjectId, Long noteImageBoxListId, NoteImageBoxRequestDto dto);

    ResponseDto<NoteImageBoxListOneDto> updateCaption(String userEmail, String noteProjectId, Long noteImageBoxId, NoteImageBoxRequestDto dto);

    ResponseDto<Void> deleteImageBox(String userEmail, String noteProjectId, Long noteImageBoxId);

    ResponseDto<NoteImageBoxListOneDto> updateImage(String userEmail, String noteProjectId, Long noteImageBoxId, MultipartFile imageUrl);
}
