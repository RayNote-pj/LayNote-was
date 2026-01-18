package com.project.lay_note_was.lay_note.service.implement;

import com.project.lay_note_was.lay_note.common.constant.ResponseMessage;
import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_project_join.NoteProjectJoinDto;
import com.project.lay_note_was.lay_note.dto.note_project_join.request.NoteProjectJoinRequestDto;
import com.project.lay_note_was.lay_note.dto.note_project_join.response.NoteProjectJoinListResponseDto;
import com.project.lay_note_was.lay_note.dto.note_project_join.response.NoteProjectJoinResponseDto;
import com.project.lay_note_was.lay_note.entity.note_project.NoteProject;
import com.project.lay_note_was.lay_note.entity.note_project_join.JoinStatus;
import com.project.lay_note_was.lay_note.entity.note_project_join.NoteProjectJoin;
import com.project.lay_note_was.lay_note.entity.note_project_user.NoteProjectUser;
import com.project.lay_note_was.lay_note.entity.note_project_user.NoteProjectUserId;
import com.project.lay_note_was.lay_note.entity.note_project_user.UserRole;
import com.project.lay_note_was.lay_note.entity.user.User;
import com.project.lay_note_was.lay_note.repository.NoteProjectJoinRepository;
import com.project.lay_note_was.lay_note.repository.NoteProjectRepository;
import com.project.lay_note_was.lay_note.repository.NoteProjectUserRepository;
import com.project.lay_note_was.lay_note.repository.UserRepository;
import com.project.lay_note_was.lay_note.service.NoteProjectJoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteProjectJoinServiceImplement implements NoteProjectJoinService {
    private final NoteProjectJoinRepository noteProjectJoinRepository;
    private final UserRepository userRepository;
    private final NoteProjectRepository noteProjectRepository;
    private final NoteProjectUserRepository noteProjectUserRepository;

    @Override
    public ResponseDto<NoteProjectJoinResponseDto> createJoin(String userEmail, String noteProjectId,NoteProjectJoinRequestDto dto) {
        try {
            User user = userRepository.findByUserEmail(userEmail)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_USER));
            NoteProject noteProject = noteProjectRepository.findByNoteProjectId(noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteProject"));

            boolean alreadyMember = noteProjectUserRepository.findByUser_UserEmailAndNoteProject_NoteProjectId(userEmail, noteProjectId)
                    .isPresent();

            if (alreadyMember) {
                return ResponseDto.setFailed(ResponseMessage.ALREADY_NOTE_PROJECT_MEMBER);
            }

            boolean alreadyPending = noteProjectJoinRepository.findByNoteProject_NoteProjectIdAndUser_UserEmailAndJoinStatus(
                    noteProjectId, userEmail, JoinStatus.PENDING).isPresent();

            if (alreadyPending) {
                return ResponseDto.setFailed(ResponseMessage.ALREADY_REQUEST);
            }

            NoteProjectJoin noteProjectJoin = NoteProjectJoin.builder()
                    .noteProject(noteProject)
                    .user(user)
                    .createdAt(LocalDateTime.now())
                    .build();

            noteProjectJoinRepository.save(noteProjectJoin);
            NoteProjectJoinDto response = new NoteProjectJoinDto(noteProjectJoin);

            NoteProjectJoinResponseDto data = new NoteProjectJoinResponseDto(response);
            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (IllegalArgumentException e) {
            return ResponseDto.setFailed(e.getMessage());
        } catch(Exception e) {
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Transactional
    @Override
    public ResponseDto<NoteProjectJoinResponseDto> changeJoinStatus(String userEmail, String noteProjectJoinId, JoinStatus joinStatus, UserRole role) {
        try {
            NoteProjectJoin noteProjectJoin = noteProjectJoinRepository.findByNoteProjectJoinId(noteProjectJoinId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA));

            if (noteProjectJoin == null) return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_DATA +"noteProjectJoin");

            User projectOwner = userRepository.findByUserEmail(userEmail)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA));

            if (projectOwner == null) return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_DATA +"projectOwner");

            String projectId = noteProjectJoin.getNoteProject().getNoteProjectId();
            NoteProjectUserId projectOwnerId = new NoteProjectUserId(projectOwner.getUserId(), projectId);
            NoteProjectUser ownerLink = noteProjectUserRepository.findById(projectOwnerId).orElse(null);

        if (ownerLink == null || ownerLink.getUserRole() != UserRole.OWNER) {
            return ResponseDto.setFailed(ResponseMessage.NO_PERMISSION);
        }

        if (noteProjectJoin.getJoinStatus() != JoinStatus.PENDING) {
            if (noteProjectJoin.getJoinStatus() == joinStatus) {
                return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
            }
            return ResponseDto.setFailed(ResponseMessage.VALIDATION_FAIL);
        }

        if (joinStatus != JoinStatus.APPROVED || joinStatus != JoinStatus.REJECTED) {
            return ResponseDto.setFailed(ResponseMessage.VALIDATION_FAIL);
        }

        if (joinStatus == JoinStatus.APPROVED) {
            UserRole grantRole = role == null ? UserRole.MEMBER : role;

            if (grantRole == UserRole.OWNER) {
                return ResponseDto.setFailed(ResponseMessage.VALIDATION_FAIL);
            }
            NoteProjectUserId memberId = new NoteProjectUserId(noteProjectJoin.getUser().getUserId(), projectId);
            NoteProjectUser existing = noteProjectUserRepository.findById(memberId).orElse(null);
            if (existing == null) {
                NoteProjectUser link = NoteProjectUser.builder()
                        .noteProject(noteProjectJoin.getNoteProject())
                        .user(noteProjectJoin.getUser())
                        .userRole(grantRole)
                        .build();
                noteProjectUserRepository.save(link);
            }
        }
            noteProjectJoin.setJoinStatus(joinStatus);
            noteProjectJoin.setDecidedAt(java.time.LocalDateTime.now());

            NoteProjectJoinDto response = new NoteProjectJoinDto(noteProjectJoin);
            NoteProjectJoinResponseDto data = new NoteProjectJoinResponseDto(response);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (IllegalArgumentException e) {
            return ResponseDto.setFailed(e.getMessage());
        } catch(Exception e) {
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    public ResponseDto<NoteProjectJoinListResponseDto> getJoin(String userEmail, String noteProjectId) {
        try {
            List<NoteProjectJoin> noteProjectJoin = noteProjectJoinRepository.findByNoteProject_NoteProjectId(noteProjectId);
            if (noteProjectJoin.isEmpty()) {
                return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_DATA + "noteProjectJoin");
            }

            List<NoteProjectJoinDto> response = noteProjectJoin.stream()
                    .map(NoteProjectJoinDto::new)
                    .toList();
            NoteProjectJoinListResponseDto data = new NoteProjectJoinListResponseDto(response);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch(Exception e) {
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }
}
