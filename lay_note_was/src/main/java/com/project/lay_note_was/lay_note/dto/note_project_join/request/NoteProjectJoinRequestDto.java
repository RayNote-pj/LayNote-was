package com.project.lay_note_was.lay_note.dto.note_project_join.request;

import com.project.lay_note_was.lay_note.entity.note_project_join.JoinStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteProjectJoinRequestDto {
    private String userEmail;
}
