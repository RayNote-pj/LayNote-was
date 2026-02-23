package com.project.lay_note_was.lay_note.dto.note_project_composition.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.lay_note_was.lay_note.dto.note_box.response.NoteBoxResponseDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.NoteImageBoxListDto;
import com.project.lay_note_was.lay_note.dto.note_list.response.NoteListOneResponseDto;
import com.project.lay_note_was.lay_note.entity.note_project.NoteProject;
import com.project.lay_note_was.lay_note.entity.note_project_composition.NoteComponentType;
import com.project.lay_note_was.lay_note.entity.note_project_composition.NoteProjectComposition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompositionResponseDto {
    private String noteCompositionId;
    @JsonIgnore
    private NoteProject noteProject;
    private NoteComponentType noteComponentType;
    private int compositionX;
    private int compositionY;
    private int compositionZ;
    private int compositionZ2;
    private NoteBoxResponseDto noteBox;
    private NoteListOneResponseDto noteList;
    private NoteImageBoxListDto noteImageBoxList;

    public CompositionResponseDto(NoteProjectComposition noteProjectComposition) {
        this.noteCompositionId = noteProjectComposition.getNoteCompositionId();
        this.noteProject = noteProjectComposition.getNoteProject();
        this.noteComponentType = noteProjectComposition.getNoteComponentType();
        this.compositionX = noteProjectComposition.getCompositionX();
        this.compositionY = noteProjectComposition.getCompositionY();
        this.compositionZ = noteProjectComposition.getCompositionZ();
        this.compositionZ2 = noteProjectComposition.getCompositionZ2();
    }
}

