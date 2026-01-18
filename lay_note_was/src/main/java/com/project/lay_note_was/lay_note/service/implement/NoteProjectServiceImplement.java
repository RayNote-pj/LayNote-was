package com.project.lay_note_was.lay_note.service.implement;

import com.project.lay_note_was.lay_note.common.constant.ResponseMessage;
import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_project.NoteProjectDto;
import com.project.lay_note_was.lay_note.dto.note_project.request.NoteProjectCreateRequestDto;
import com.project.lay_note_was.lay_note.dto.note_project.request.NoteProjectImageRequestDto;
import com.project.lay_note_was.lay_note.dto.note_project.request.NoteProjectUpdateRequestDto;
import com.project.lay_note_was.lay_note.dto.note_project.response.NoteProjectListResponseDto;
import com.project.lay_note_was.lay_note.dto.note_project.response.NoteProjectResponseDto;
import com.project.lay_note_was.lay_note.entity.note_project.NoteProject;
import com.project.lay_note_was.lay_note.entity.user.User;
import com.project.lay_note_was.lay_note.repository.NoteProjectRepository;
import com.project.lay_note_was.lay_note.repository.UserRepository;
import com.project.lay_note_was.lay_note.service.ImageService;
import com.project.lay_note_was.lay_note.service.NoteProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteProjectServiceImplement implements NoteProjectService {

    private final NoteProjectRepository noteProjectRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    @Override
    public ResponseDto<NoteProjectListResponseDto> getNoteProjectAll(String userEmail) {
        try {
            List<NoteProject> noteProject = noteProjectRepository.findByUser_UserEmail(userEmail);
            if (noteProject.isEmpty()) {
                throw new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteProject");
            }

            List<NoteProjectDto> response = noteProject.stream()
                    .map(NoteProjectDto::new)
                    .toList();

            NoteProjectListResponseDto data = new NoteProjectListResponseDto(response);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    public ResponseDto<NoteProjectResponseDto> getNoteProjectOne(String userEmail, String noteProjectId) {
        try {
            NoteProject noteProject = noteProjectRepository.findByUser_UserEmailAndNoteProjectId(userEmail, noteProjectId).orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteProject"));

            NoteProjectDto response = new NoteProjectDto(noteProject);
            NoteProjectResponseDto data = new NoteProjectResponseDto(response);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    public ResponseDto<NoteProjectListResponseDto> getWasteBasket(String userEmail) {
        try{
            List<NoteProject> noteProjects = noteProjectRepository.WasteFindByUser_UserEmail(userEmail);

            List<NoteProjectDto> response = noteProjects.stream()
                    .map(NoteProjectDto::new)
                    .toList();
            NoteProjectListResponseDto data = new NoteProjectListResponseDto(response);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    public ResponseDto<NoteProjectResponseDto> createNoteProject(String userEmail) {
        try{
            User user = userRepository.findByUserEmail(userEmail)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_USER));

            NoteProject noteProject = NoteProject.builder()
                    .noteProjectTitle("Untitle")
                    .noteProjectImageUrl("111")
                    .user(user)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            noteProjectRepository.save(noteProject);

            NoteProjectDto response = new NoteProjectDto(noteProject);
            NoteProjectResponseDto data = new NoteProjectResponseDto(response);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    public ResponseDto<NoteProjectResponseDto> updateNoteProjectTitle(String userEmail, String noteProjectId, NoteProjectUpdateRequestDto dto) {
        try{
            NoteProject noteProject = noteProjectRepository.findByUser_UserEmailAndNoteProjectId(userEmail, noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteProject"));
            NoteProject saveData = noteProject.toBuilder()
                    .noteProjectTitle(dto.getNoteProjectTitle())
                    .updatedAt(LocalDateTime.now())
                    .build();
            noteProjectRepository.save(saveData);

            NoteProjectDto response = new NoteProjectDto(saveData);
            NoteProjectResponseDto data = new NoteProjectResponseDto(response);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    public ResponseDto<NoteProjectResponseDto> updateNoteProjectImage(String userEmail, String noteProjectId, MultipartFile noteProjectImageUrl) {
        try{
            String noteProjectImgPath = null;
            if (noteProjectImageUrl != null && !noteProjectImageUrl.isEmpty()) {
                noteProjectImgPath = imageService.convertImgFile(noteProjectImageUrl, "note-project-image");
            }
            NoteProject noteProject = noteProjectRepository.findByUser_UserEmailAndNoteProjectId(userEmail, noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteProject"));
            NoteProject saveData = noteProject.toBuilder()
                    .noteProjectImageUrl(noteProjectImgPath)
                    .updatedAt(LocalDateTime.now())
                    .build();
            noteProjectRepository.save(saveData);

            NoteProjectDto response = new NoteProjectDto(saveData);
            NoteProjectResponseDto data = new NoteProjectResponseDto(response);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    public ResponseDto<NoteProjectResponseDto> updateDeleteAt(String userEmail, String noteProjectId) {
        try{
            NoteProject noteProject = noteProjectRepository.findByUser_UserEmailAndNoteProjectId(userEmail, noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA));
            NoteProject saveData = noteProject.toBuilder()
                    .deletedAt(LocalDateTime.now())
                    .build();
            noteProjectRepository.save(saveData);

            NoteProjectDto response = new NoteProjectDto(saveData);
            NoteProjectResponseDto data = new NoteProjectResponseDto(response);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    public ResponseDto<NoteProjectResponseDto> deleteDeleteAt(String userEmail, String noteProjectId) {
        try{
            NoteProject noteProject = noteProjectRepository.findByUser_UserEmailAndNoteProjectId(userEmail, noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA));
            NoteProject saveData = noteProject.toBuilder()
                    .deletedAt(null)
                    .build();
            noteProjectRepository.save(saveData);

            NoteProjectDto response = new NoteProjectDto(saveData);
            NoteProjectResponseDto data = new NoteProjectResponseDto(response);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    public ResponseDto<Void> deleteNoteProject(String userEmail, String noteProjectId) {
        try {
            NoteProject noteProject = noteProjectRepository.findByUser_UserEmailAndNoteProjectId(userEmail, noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA));

            noteProjectRepository.delete(noteProject);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
        } catch (IllegalArgumentException e) {
            return ResponseDto.setFailed(e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }
}
