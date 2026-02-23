package com.project.lay_note_was.lay_note.dto.note_project_join.response;

import com.project.lay_note_was.lay_note.dto.note_project_join.NoteProjectJoinDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteProjectJoinListResponseDto {
    private List<NoteProjectJoinDto> noteProjectJoins;
}
