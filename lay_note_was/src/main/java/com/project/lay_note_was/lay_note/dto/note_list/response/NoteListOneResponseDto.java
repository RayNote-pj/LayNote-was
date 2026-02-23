package com.project.lay_note_was.lay_note.dto.note_list.response;

import com.project.lay_note_was.lay_note.entity.note_list.NoteList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoteListOneResponseDto {
    private Long noteListId;

    public NoteListOneResponseDto(NoteList noteList) {
        this.noteListId = noteList.getNoteListId();
    }
}
