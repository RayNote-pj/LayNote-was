package com.project.lay_note_was.lay_note.service.implement;

import com.project.lay_note_was.lay_note.common.constant.ResponseMessage;
import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_project_user.NoteProjectUserDto;
import com.project.lay_note_was.lay_note.dto.note_project_user.response.NoteProjectUserListResponseDto;
import com.project.lay_note_was.lay_note.dto.note_project_user.response.NoteProjectUserResponseDto;
import com.project.lay_note_was.lay_note.entity.note_project.NoteProject;
import com.project.lay_note_was.lay_note.entity.note_project_user.NoteProjectUser;
import com.project.lay_note_was.lay_note.entity.note_project_user.UserRole;
import com.project.lay_note_was.lay_note.entity.user.User;
import com.project.lay_note_was.lay_note.repository.NoteProjectRepository;
import com.project.lay_note_was.lay_note.repository.NoteProjectUserRepository;
import com.project.lay_note_was.lay_note.repository.UserRepository;
import com.project.lay_note_was.lay_note.service.NoteProjectUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteProjectUserServiceImplement implements NoteProjectUserService {
    private final NoteProjectUserRepository noteProjectUserRepository;
    private final UserRepository userRepository;
    private final NoteProjectRepository noteProjectRepository;

    @Transactional
    @Override
    public ResponseDto<NoteProjectUserListResponseDto> getProjectJoinUser(String userEmail, String noteProjectId) {
        try {
            List<NoteProjectUser> noteProjectUser = noteProjectUserRepository.findByNoteProject_NoteProjectId(noteProjectId);
            if (noteProjectUser.isEmpty()) {
                return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_DATA + "noteProjectUser");
            }
            List<NoteProjectUserDto> response = noteProjectUser.stream()
                    .map(NoteProjectUserDto::new)
                    .toList();
            NoteProjectUserListResponseDto data = new NoteProjectUserListResponseDto(response);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }

    }

    @Override
    @Transactional
    public ResponseDto<NoteProjectUserResponseDto> changeUserRole(String userEmail, String memberEmail,UserRole userRole, String noteProjectId) {
        try {
            User user = userRepository.findByUserEmail(memberEmail)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_USER));
            NoteProject noteProject = noteProjectRepository.findByNoteProjectId(noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteProject"));
            NoteProjectUser noteProjectUser = noteProjectUserRepository.findByUserAndNoteProject(user, noteProject)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteProjectUser"));

            if (noteProjectUser.getUserRole() != UserRole.OWNER) {
                return ResponseDto.setFailed(ResponseMessage.ONLY_OWNER);
            }

            noteProjectUser.setUserRole(userRole);
            noteProjectUserRepository.save(noteProjectUser);

            NoteProjectUserDto response = new NoteProjectUserDto(noteProjectUser);
            NoteProjectUserResponseDto data = new NoteProjectUserResponseDto(response);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }

    }

    @Override
    public ResponseDto<Boolean> isMember(String userEmail, String noteProjectId) {
        try {
            boolean projectUser = noteProjectUserRepository
                    .existsByUser_UserEmailAndNoteProject_NoteProjectId(
                            userEmail,
                            noteProjectId
                    );

            if (!projectUser) {
                return ResponseDto.setSuccess(ResponseMessage.SUCCESS, false);
            }

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }
}
