package com.project.lay_note_was.lay_note.dto.note_list.response;

import com.project.lay_note_was.lay_note.dto.note_list.NoteListItemDto;
import com.project.lay_note_was.lay_note.entity.note_list_item.NoteListItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoteListItemResponseDto {
    private Long noteListId;
    private String noteListTitle;
    private NoteListItemDto noteListItemDto;

    public NoteListItemResponseDto(NoteListItem note) {
        this.noteListId = note.getNoteList().getNoteListId();
        this.noteListTitle = note.getNoteList().getNoteListTitle();
        this.noteListItemDto = new NoteListItemDto(note);
    }
}
