package com.project.lay_note_was.lay_note.dto.note_list;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.lay_note_was.lay_note.entity.note_list.NoteList;
import com.project.lay_note_was.lay_note.entity.note_list_item.NoteListItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoteListItemDto {
    private Long noteListItemId;
    private String noteListContent;
    private boolean noteListCheck;

    public NoteListItemDto(NoteListItem noteListItem) {
        this.noteListItemId = noteListItem.getNoteListItemId();
        this.noteListContent = noteListItem.getNoteListContent();
        this.noteListCheck = noteListItem.isNoteListCheck();
    }
}
