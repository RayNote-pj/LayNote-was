package com.project.lay_note_was.lay_note.service.implement;

import com.project.lay_note_was.lay_note.common.constant.ResponseMessage;
import com.project.lay_note_was.lay_note.component.ProjectPermissionChecker;
import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.response.NoteImageBoxListOneResponseDto;
import com.project.lay_note_was.lay_note.entity.note_image_box.NoteImageBox;
import com.project.lay_note_was.lay_note.entity.note_image_box.NoteImageBoxList;
import com.project.lay_note_was.lay_note.entity.note_project.NoteProject;
import com.project.lay_note_was.lay_note.entity.note_project_composition.NoteComponentType;
import com.project.lay_note_was.lay_note.entity.note_project_composition.NoteProjectComposition;
import com.project.lay_note_was.lay_note.repository.*;
import com.project.lay_note_was.lay_note.service.NoteImageBoxListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteImageBoxListServiceImplement implements NoteImageBoxListService {

    private final NoteImageBoxListRepository noteImageBoxListRepository;
    private final NoteProjectCompositionRepository noteProjectCompositionRepository;
    private final NoteProjectRepository noteProjectRepository;
    private final NoteImageBoxRepository noteImageBoxRepository;
    private final ProjectPermissionChecker projectPermissionChecker;

    @Transactional
    @Override
    public ResponseDto<NoteImageBoxListOneResponseDto> createImageBox(String userEmail, String noteProjectId) {
        try {
            projectPermissionChecker.requireMemberOrOwner(userEmail, noteProjectId);

            NoteImageBoxList noteImageBoxList = NoteImageBoxList.builder()
                    .build();

            noteImageBoxListRepository.save(noteImageBoxList);

            NoteImageBox box = NoteImageBox.builder()
                    .noteImageBoxList(noteImageBoxList)
                    .build();
            noteImageBoxRepository.save(box);

            NoteProject noteProject = noteProjectRepository.findById(noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteProject"));

            NoteProjectComposition composition = NoteProjectComposition.builder()
                    .compositionX(200)
                    .compositionY(200)
                    .compositionZ(1)
                    .noteImageBoxList(noteImageBoxList)
                    .noteComponentType(NoteComponentType.NOTEIMAGEBOX)
                    .noteProject(noteProject)
                    .build();
            noteProjectCompositionRepository.save(composition);

            NoteImageBoxListOneResponseDto data = new NoteImageBoxListOneResponseDto(noteImageBoxList.getNoteImageBoxListId());

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
            projectPermissionChecker.requireMemberOrOwner(userEmail, noteProjectId);

            NoteProjectComposition composition = noteProjectCompositionRepository.findByNoteProject_NoteProjectIdAndNoteImageBoxList_NoteImageBoxListIdAndNoteComponentType(noteProjectId, noteImageBoxListId, NoteComponentType.NOTEIMAGEBOX).orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteProjectComposition"));

            NoteImageBoxList noteImageBoxList = noteImageBoxListRepository.findByNoteImageBoxListId(noteImageBoxListId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteImageBoxList"));

            noteProjectCompositionRepository.delete(composition);
            noteImageBoxRepository.deleteAll(noteImageBoxList.getNoteImageBoxes());
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
