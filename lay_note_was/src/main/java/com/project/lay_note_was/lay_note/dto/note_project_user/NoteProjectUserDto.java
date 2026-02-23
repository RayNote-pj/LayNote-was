package com.project.lay_note_was.lay_note.dto.note_project_user;

import com.project.lay_note_was.lay_note.entity.note_project_user.NoteProjectUser;
import com.project.lay_note_was.lay_note.entity.note_project_user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteProjectUserDto {
    private String userEmail;
    private UserRole userRole;

    public NoteProjectUserDto(NoteProjectUser noteProjectUser) {
        this.userEmail = noteProjectUser.getUser().getUserEmail();
        this.userRole = noteProjectUser.getUserRole();
    }
}
