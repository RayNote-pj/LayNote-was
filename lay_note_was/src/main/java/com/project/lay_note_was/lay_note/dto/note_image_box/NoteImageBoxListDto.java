package com.project.lay_note_was.lay_note.dto.note_image_box;

import com.project.lay_note_was.lay_note.entity.note_image_box.NoteImageBoxList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoteImageBoxListDto {
    private Long noteImageBoxListId;
    private List<NoteImageBoxDto> noteImageBoxDto;

    public NoteImageBoxListDto(NoteImageBoxList noteImageBoxList) {
        this.noteImageBoxListId = noteImageBoxList.getNoteImageBoxListId();
        this.noteImageBoxDto = noteImageBoxList.getNoteImageBoxes().stream()
                .map(NoteImageBoxDto::new)
                .toList();
    }
}
