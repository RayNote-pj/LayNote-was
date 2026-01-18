package com.project.lay_note_was.lay_note.dto.note_project.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteProjectImageRequestDto {
    @NotNull
    private MultipartFile noteProjectImageUrl;
}
