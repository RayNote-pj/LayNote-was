package com.project.lay_note_was.lay_note.dto.note_project_composition;

import com.project.lay_note_was.lay_note.dto.note_box.NoteBoxDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.NoteImageBoxListDto;
import com.project.lay_note_was.lay_note.dto.note_list.NoteListDto;
import com.project.lay_note_was.lay_note.entity.note_project_composition.NoteComponentType;
import com.project.lay_note_was.lay_note.entity.note_project_composition.NoteProjectComposition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompositionDto {

    private String noteCompositionId;
    private NoteComponentType noteComponentType;
    private int compositionX;
    private int compositionY;
    private int compositionZ;
    private int compositionZ2;
    private NoteBoxDto noteBox;
    private NoteListDto noteList;
    private NoteImageBoxListDto noteImageBoxList;

    public CompositionDto(NoteProjectComposition c) {
        this.noteCompositionId = c.getNoteCompositionId();
        this.noteComponentType = c.getNoteComponentType();
        this.compositionX = c.getCompositionX();
        this.compositionY = c.getCompositionY();
        this.compositionZ = c.getCompositionZ();
        this.compositionZ2 = c.getCompositionZ2();
    }

    public void setNoteBox(NoteBoxDto noteBox) {
        this.noteBox = noteBox;
    }

    public void setNoteList(NoteListDto noteList) {
        this.noteList = noteList;
    }

    public void setNoteImageBoxList(NoteImageBoxListDto noteImageBoxList) {
        this.noteImageBoxList = noteImageBoxList;
    }
}

