package com.project.lay_note_was.lay_note.service;

import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_project_user.response.NoteProjectUserListResponseDto;
import com.project.lay_note_was.lay_note.dto.note_project_user.response.NoteProjectUserResponseDto;
import com.project.lay_note_was.lay_note.entity.note_project_user.UserRole;

public interface NoteProjectUserService {
    ResponseDto<NoteProjectUserListResponseDto> getProjectJoinUser(String userEmail, String noteProjectId);

    ResponseDto<NoteProjectUserResponseDto> changeUserRole(String userEmail, String memberEmail, UserRole userRole, String noteProjectId);

    ResponseDto<Boolean> isMember(String userEmail, String noteProjectId);
}
