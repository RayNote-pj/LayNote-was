package com.project.lay_note_was.lay_note.service.implement;

import com.project.lay_note_was.lay_note.common.constant.ResponseMessage;
import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.NoteImageBoxDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.NoteImageBoxListOneDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.request.NoteImageBoxRequestDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.response.NoteImageBoxResponseDto;
import com.project.lay_note_was.lay_note.entity.note_image_box.NoteImageBox;
import com.project.lay_note_was.lay_note.entity.note_image_box.NoteImageBoxList;
import com.project.lay_note_was.lay_note.entity.note_project.NoteProject;
import com.project.lay_note_was.lay_note.entity.note_project_composition.NoteComponentType;
import com.project.lay_note_was.lay_note.entity.note_project_composition.NoteProjectComposition;
import com.project.lay_note_was.lay_note.entity.note_project_user.NoteProjectUser;
import com.project.lay_note_was.lay_note.entity.note_project_user.UserRole;
import com.project.lay_note_was.lay_note.repository.*;
import com.project.lay_note_was.lay_note.service.NoteImageBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteImageBoxServiceImplement implements NoteImageBoxService {
    private final NoteProjectUserRepository noteProjectUserRepository;
    private final NoteImageBoxListRepository noteImageBoxListRepository;
    private final NoteImageBoxRepository noteImageBoxRepository;
    private final NoteProjectRepository noteProjectRepository;
    private final NoteProjectCompositionRepository noteProjectCompositionRepository;

    @Override
    public ResponseDto<NoteImageBoxResponseDto> createImageBox(String userEmail, String noteProjectId, Long noteImageBoxListId, NoteImageBoxRequestDto dto) {
        try {
            NoteProjectUser projectUser = noteProjectUserRepository.findByUser_UserEmailAndNoteProject_NoteProjectId(userEmail, noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_NOTE_PROJECT_MEMBER));

            if(!(projectUser.getUserRole() == UserRole.OWNER || projectUser.getUserRole() == UserRole.MEMBER)) {
                return ResponseDto.setFailed(ResponseMessage.NO_PERMISSION);
            }

            NoteImageBoxList noteImageBoxList = noteImageBoxListRepository.findByNoteImageBoxListId(noteImageBoxListId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteImageBoxList"));

            NoteImageBox noteImageBox = NoteImageBox.builder()
                    .noteImageBoxList(noteImageBoxList)
                    .imageUrl(dto.getImageUrl())
                    .imageCaption(dto.getImageCaption())
                    .build();

            NoteImageBox saveData = noteImageBoxRepository.save(noteImageBox);

            NoteImageBoxDto response = new NoteImageBoxDto(saveData);
            NoteImageBoxListOneDto responseList = new NoteImageBoxListOneDto(response);
            NoteImageBoxResponseDto data = new NoteImageBoxResponseDto(responseList);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (IllegalArgumentException e) {
            return ResponseDto.setFailed(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    public ResponseDto<NoteImageBoxResponseDto> updateImageBox(String userEmail, String noteProjectId, Long noteImageBoxListId, Long noteImageBoxId, NoteImageBoxRequestDto dto) {
        try {
            NoteProjectUser projectUser = noteProjectUserRepository.findByUser_UserEmailAndNoteProject_NoteProjectId(userEmail, noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_NOTE_PROJECT_MEMBER));

            if(!(projectUser.getUserRole() == UserRole.OWNER || projectUser.getUserRole() == UserRole.MEMBER)) {
                return ResponseDto.setFailed(ResponseMessage.NO_PERMISSION);
            }

            NoteImageBox noteImageBox = noteImageBoxRepository.findById(noteImageBoxId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteImageBox"));
            NoteImageBox updateData = noteImageBox.toBuilder()
                    .imageUrl(dto.getImageUrl())
                    .imageCaption(dto.getImageCaption())
                    .build();

            NoteImageBoxDto response = new NoteImageBoxDto(updateData);
            NoteImageBoxListOneDto responseList = new NoteImageBoxListOneDto(response);
            NoteImageBoxResponseDto data = new NoteImageBoxResponseDto(responseList);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (IllegalArgumentException e) {
            return ResponseDto.setFailed(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    public ResponseDto<Void> deleteImageBox(String userEmail, String noteProjectId, Long noteImageBoxId) {
        try {
            NoteProjectUser projectUser = noteProjectUserRepository.findByUser_UserEmailAndNoteProject_NoteProjectId(userEmail, noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_NOTE_PROJECT_MEMBER));

            if(!(projectUser.getUserRole() == UserRole.OWNER || projectUser.getUserRole() == UserRole.MEMBER)) {
                return ResponseDto.setFailed(ResponseMessage.NO_PERMISSION);
            }

            NoteImageBox noteImageBox = noteImageBoxRepository.findById(noteImageBoxId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteImageBox"));

            noteImageBoxRepository.delete(noteImageBox);
            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
        } catch (IllegalArgumentException e) {
            return ResponseDto.setFailed(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }
}
