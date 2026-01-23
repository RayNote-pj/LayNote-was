package com.project.lay_note_was.lay_note.service.implement;

import com.project.lay_note_was.lay_note.common.constant.ResponseMessage;
import com.project.lay_note_was.lay_note.component.ProjectPermissionChecker;
import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_box.NoteBoxDto;
import com.project.lay_note_was.lay_note.dto.note_box.request.NoteBoxCreateRequestDto;
import com.project.lay_note_was.lay_note.dto.note_box.request.NoteBoxUpdateRequestDto;
import com.project.lay_note_was.lay_note.dto.note_box.response.NoteBoxListResponseDto;
import com.project.lay_note_was.lay_note.dto.note_box.response.NoteBoxResponseDto;
import com.project.lay_note_was.lay_note.entity.note_box.NoteBox;
import com.project.lay_note_was.lay_note.entity.note_project.NoteProject;
import com.project.lay_note_was.lay_note.entity.note_project_composition.NoteComponentType;
import com.project.lay_note_was.lay_note.entity.note_project_composition.NoteProjectComposition;
import com.project.lay_note_was.lay_note.entity.note_project_user.NoteProjectUser;
import com.project.lay_note_was.lay_note.entity.note_project_user.UserRole;
import com.project.lay_note_was.lay_note.repository.NoteBoxRepository;
import com.project.lay_note_was.lay_note.repository.NoteProjectCompositionRepository;
import com.project.lay_note_was.lay_note.repository.NoteProjectRepository;
import com.project.lay_note_was.lay_note.repository.NoteProjectUserRepository;
import com.project.lay_note_was.lay_note.service.ImageService;
import com.project.lay_note_was.lay_note.service.NoteBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteBoxServiceImplement implements NoteBoxService {

    private final NoteBoxRepository noteBoxRepository;
    private final NoteProjectRepository noteProjectRepository;
    private final NoteProjectCompositionRepository noteProjectCompositionRepository;
    private final ImageService imageService;
    private final ProjectPermissionChecker projectPermissionChecker;

    @Override
    public ResponseDto<NoteBoxResponseDto> createNoteBox(String userEmail, String noteProjectId) {
        try {
            projectPermissionChecker.requireMemberOrOwner(userEmail, noteProjectId);

            NoteProject noteProject = noteProjectRepository.findById(noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteProject"));

            NoteBox noteBox = NoteBox.builder()
                    .noteBoxTitle("Untitled")
                    .noteBoxContent("")
                    .imageUrl(null)
                    .build();
            noteBoxRepository.save(noteBox);
            NoteProjectComposition composition = NoteProjectComposition.builder()
                    .compositionX(200)
                    .compositionY(200)
                    .compositionZ(1)
                    .compositionWidth(350)
                    .compositionHeight(400)
                    .noteComponentType(NoteComponentType.NOTEBOX)
                    .noteComponentId(noteBox.getNoteBoxId())
                    .noteProject(noteProject)
                    .build();
            noteProjectCompositionRepository.save(composition);
            NoteBoxDto response = new NoteBoxDto(noteBox);
            NoteBoxResponseDto data = new NoteBoxResponseDto(response);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (IllegalArgumentException e) {
            return ResponseDto.setFailed(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    public ResponseDto<NoteBoxResponseDto> updateNoteBox(String userEmail, String noteProjectId, NoteBoxUpdateRequestDto dto, Long noteBoxId) {
        try {
            projectPermissionChecker.requireMemberOrOwner(userEmail, noteProjectId);

            NoteBox noteBox = noteBoxRepository.findById(noteBoxId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteBox"));

            noteBox.setNoteBoxTitle(dto.getNoteBoxTitle());
            noteBox.setNoteBoxContent(dto.getNoteBoxContent());
            noteBox.setImageUrl(dto.getImageUrl());

            NoteBoxDto response = new NoteBoxDto(noteBox);
            NoteBoxResponseDto data = new NoteBoxResponseDto(response);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (IllegalArgumentException e) {
            return ResponseDto.setFailed(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    public ResponseDto<NoteBoxListResponseDto> getNoteBox(String userEmail, String noteProjectId) {
        try {
            projectPermissionChecker.requireMemberOrOwner(userEmail, noteProjectId);

            List<NoteBox> noteBoxes = noteBoxRepository.findAll();

            List<NoteBoxDto> response = noteBoxes.stream()
                    .map(NoteBoxDto::new)
                    .toList();

            NoteBoxListResponseDto data = new NoteBoxListResponseDto(response);
            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (IllegalArgumentException e) {
            return ResponseDto.setFailed(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    public ResponseDto<Void> deleteNoteBox(String userEmail, String noteProjectId, Long noteBoxId) {
        try {
            projectPermissionChecker.requireMemberOrOwner(userEmail, noteProjectId);
            NoteProjectComposition composition = noteProjectCompositionRepository.findByComponentTypeAndTargetIdAndNoteProject_noteProjectId(NoteComponentType.NOTELIST, noteBoxId, noteProjectId).orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteProjectComposition"));

            NoteBox noteBox = noteBoxRepository.findById(noteBoxId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteBox"));

            noteProjectCompositionRepository.delete(composition);
            noteBoxRepository.delete(noteBox);
            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
        } catch (IllegalArgumentException e) {
            return ResponseDto.setFailed(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    public ResponseDto<NoteBoxResponseDto> updateNoteBoxImg(String userEmail, String noteProjectId, MultipartFile imageUrl, Long noteBoxId) {
        try {
            projectPermissionChecker.requireMemberOrOwner(userEmail, noteProjectId);
            String noteBoxImgPath = null;
            if (imageUrl != null && !imageUrl.isEmpty()) {
                noteBoxImgPath = imageService.convertImgFile(imageUrl, "note-box-image");
            }
            NoteBox noteBox = noteBoxRepository.findNoteBox(userEmail, noteBoxId);

            NoteBox note = noteBox.toBuilder()
                    .imageUrl(noteBoxImgPath)
                    .build();
            NoteBoxDto dto = new NoteBoxDto(note);
            NoteBoxResponseDto data = new NoteBoxResponseDto(dto);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

}
