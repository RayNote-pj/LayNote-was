package com.project.lay_note_was.lay_note.dto.note_image_box;

import com.project.lay_note_was.lay_note.entity.note_image_box.NoteImageBox;
import com.project.lay_note_was.lay_note.entity.note_image_box.NoteImageBoxList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoteImageBoxDto {
    private Long noteImageBoxId;
    private String imageCaption;
    private String imageUrl;

    public NoteImageBoxDto(NoteImageBox noteImageBox) {
        this.noteImageBoxId = noteImageBox.getNoteImageBoxId();
        this.imageCaption = noteImageBox.getImageCaption();
        this.imageUrl = noteImageBox.getImageUrl();
    }
}
