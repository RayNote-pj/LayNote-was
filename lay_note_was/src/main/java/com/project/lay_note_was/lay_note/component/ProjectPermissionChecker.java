package com.project.lay_note_was.lay_note.component;

import com.project.lay_note_was.lay_note.common.constant.ResponseMessage;
import com.project.lay_note_was.lay_note.entity.note_project_user.NoteProjectUser;
import com.project.lay_note_was.lay_note.entity.note_project_user.UserRole;
import com.project.lay_note_was.lay_note.repository.NoteProjectUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectPermissionChecker  {

    private final NoteProjectUserRepository noteProjectUserRepository;

    public NoteProjectUser requireMemberOrOwner (String userEmail, String noteProjectId) {
        NoteProjectUser projectUser = noteProjectUserRepository.findByUser_UserEmailAndNoteProject_NoteProjectId(userEmail, noteProjectId)
                .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_NOTE_PROJECT_MEMBER));
        if (projectUser.getUserRole() != UserRole.OWNER &&
                projectUser.getUserRole() != UserRole.MEMBER) {
            throw new IllegalArgumentException(ResponseMessage.NO_PERMISSION);
        }

        return projectUser;
    }
}
