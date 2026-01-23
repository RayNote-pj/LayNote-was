package com.project.lay_note_was.lay_note.service.implement;

import com.project.lay_note_was.lay_note.common.constant.ResponseMessage;
import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.NoteImageBoxDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.NoteImageBoxListDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.response.NoteImageBoxListOneResponseDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.response.NoteImageBoxListResponseDto;
import com.project.lay_note_was.lay_note.entity.note_image_box.NoteImageBox;
import com.project.lay_note_was.lay_note.entity.note_image_box.NoteImageBoxList;
import com.project.lay_note_was.lay_note.entity.note_project.NoteProject;
import com.project.lay_note_was.lay_note.entity.note_project_composition.NoteComponentType;
import com.project.lay_note_was.lay_note.entity.note_project_composition.NoteProjectComposition;
import com.project.lay_note_was.lay_note.entity.note_project_user.NoteProjectUser;
import com.project.lay_note_was.lay_note.entity.note_project_user.UserRole;
import com.project.lay_note_was.lay_note.repository.*;
import com.project.lay_note_was.lay_note.service.NoteImageBoxListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteImageBoxListServiceImplement implements NoteImageBoxListService {

    private final NoteProjectUserRepository noteProjectUserRepository;
    private final NoteImageBoxListRepository noteImageBoxListRepository;
    private final NoteProjectCompositionRepository noteProjectCompositionRepository;
    private final NoteProjectRepository noteProjectRepository;
    private final NoteImageBoxRepository noteImageBoxRepository;

    @Transactional
    @Override
    public ResponseDto<NoteImageBoxListOneResponseDto> createImageBox(String userEmail, String noteProjectId) {
        try {
            NoteProjectUser projectUser = noteProjectUserRepository.findByUser_UserEmailAndNoteProject_NoteProjectId(userEmail, noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_USER));
            if(!(projectUser.getUserRole() == UserRole.OWNER || projectUser.getUserRole() == UserRole.MEMBER)) {
                return ResponseDto.setFailed(ResponseMessage.NO_PERMISSION);
            }

            NoteImageBoxList noteImageBoxList = NoteImageBoxList.builder().build();
            noteImageBoxListRepository.save(noteImageBoxList);

            NoteProject noteProject = noteProjectRepository.findById(noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteProject"));

            NoteProjectComposition composition = NoteProjectComposition.builder()
                    .compositionX(200)
                    .compositionY(200)
                    .compositionZ(1)
                    .compositionWidth(200)
                    .compositionHeight(300)
                    .noteComponentType(NoteComponentType.NOTEIMAGEBOX)
                    .noteComponentId(noteImageBoxList.getNoteImageBoxListId())
                    .noteProject(noteProject)
                    .build();
            noteProjectCompositionRepository.save(composition);

            NoteImageBox box = NoteImageBox.builder()
                    .noteImageBoxList(noteImageBoxList)
                    .build();
            noteImageBoxRepository.save(box);

            NoteImageBoxListOneResponseDto data = new NoteImageBoxListOneResponseDto(noteImageBoxList.getNoteImageBoxListId());

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (IllegalArgumentException e) {
            return ResponseDto.setFailed(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    public ResponseDto<NoteImageBoxListResponseDto> getImageBox(String userEmail, String noteProjectId) {
        try {
            NoteProjectUser projectUser = noteProjectUserRepository.findByUser_UserEmailAndNoteProject_NoteProjectId(userEmail, noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_USER));
            if(!(projectUser.getUserRole() == UserRole.OWNER || projectUser.getUserRole() == UserRole.MEMBER)) {
                return ResponseDto.setFailed(ResponseMessage.NO_PERMISSION);
            }

            List<NoteImageBoxList> noteImageBoxList = noteImageBoxListRepository.findAllByUserEmailAndCompositionId(userEmail, noteProjectId, NoteComponentType.NOTEIMAGEBOX);
            List<NoteImageBoxListDto> imageBoxListDtoList = noteImageBoxList.stream()
                    .map(list -> {
                        List<NoteImageBoxDto> boxes = noteImageBoxRepository
                                .findAllByNoteImageBoxList_NoteImageBoxListId(list.getNoteImageBoxListId())
                                .stream()
                                .map(NoteImageBoxDto::new)
                                .toList();

                        return new NoteImageBoxListDto(list.getNoteImageBoxListId(), boxes);
                    })
                    .toList();

            NoteImageBoxListResponseDto data = new NoteImageBoxListResponseDto(imageBoxListDtoList);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (IllegalArgumentException e) {
            return ResponseDto.setFailed(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Transactional
    @Override
    public ResponseDto<Void> deleteImageBoxList(String userEmail, String noteProjectId, Long noteImageBoxListId) {
        try {
            NoteProjectUser projectUser = noteProjectUserRepository.findByUser_UserEmailAndNoteProject_NoteProjectId(userEmail, noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_USER));
            if(!(projectUser.getUserRole() == UserRole.OWNER || projectUser.getUserRole() == UserRole.MEMBER)) {
                return ResponseDto.setFailed(ResponseMessage.NO_PERMISSION);
            }

            NoteProjectComposition composition = noteProjectCompositionRepository.findByComponentTypeAndTargetIdAndNoteProject_noteProjectId(NoteComponentType.NOTELIST, noteImageBoxListId, noteProjectId).orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteProjectComposition"));

            List<NoteImageBox> imageBoxes = noteImageBoxRepository.findAllByNoteImageBoxList_NoteImageBoxListId(noteImageBoxListId);
            NoteImageBoxList noteImageBoxList = noteImageBoxListRepository.findByNoteImageBoxListId(noteImageBoxListId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteImageBoxList"));

            noteProjectCompositionRepository.delete(composition);
            noteImageBoxRepository.deleteAll(imageBoxes);
            noteImageBoxListRepository.delete(noteImageBoxList);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
        } catch (IllegalArgumentException e) {
            return ResponseDto.setFailed(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }

    }
}
