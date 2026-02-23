package com.project.lay_note_was.lay_note.dto.note_list;

import com.project.lay_note_was.lay_note.entity.note_list.NoteList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoteListDto {
    private Long noteListId;
    private String noteListTitle;
    private List<NoteListItemDto> noteListItemDto;


    public NoteListDto(NoteList noteList) {
        this.noteListId = noteList.getNoteListId();
        this.noteListTitle = noteList.getNoteListTitle();
        this.noteListItemDto = noteList.getNoteListItems().stream()
                .map(NoteListItemDto::new)
                .toList();
    }
}
