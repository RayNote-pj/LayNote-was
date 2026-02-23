package com.project.lay_note_was.lay_note.dto.note_project_composition.request;

import com.project.lay_note_was.lay_note.entity.note_project_composition.NoteComponentType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompositionRequestDto {
    @NotNull
    private NoteComponentType noteComponentType;
    @NotNull
    private int  compositionX;
    @NotNull
    private int  compositionY;
    @NotNull
    private int  compositionZ;
    @NotNull
    private int  compositionZ2;
}
