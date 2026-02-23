package com.project.lay_note_was.lay_note.service.implement;

import com.project.lay_note_was.lay_note.component.CompositionAssembler;
import com.project.lay_note_was.lay_note.common.constant.ResponseMessage;
import com.project.lay_note_was.lay_note.component.ProjectPermissionChecker;
import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_project_composition.CompositionDto;
import com.project.lay_note_was.lay_note.dto.note_project_composition.request.CompositionPositionRequestDto;
import com.project.lay_note_was.lay_note.dto.note_project_composition.response.CompositionResponseDto;
import com.project.lay_note_was.lay_note.entity.note_box.NoteBox;
import com.project.lay_note_was.lay_note.entity.note_image_box.NoteImageBox;
import com.project.lay_note_was.lay_note.entity.note_image_box.NoteImageBoxList;
import com.project.lay_note_was.lay_note.entity.note_list.NoteList;
import com.project.lay_note_was.lay_note.entity.note_project_composition.NoteProjectComposition;
import com.project.lay_note_was.lay_note.repository.*;
import com.project.lay_note_was.lay_note.service.NoteProjectCompositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteProjectCompositionServiceImplement implements NoteProjectCompositionService {
    private final NoteProjectCompositionRepository noteProjectCompositionRepository;
    private final CompositionAssembler compositionAssembler;
    private final ProjectPermissionChecker projectPermissionChecker;

    @Override
    public ResponseDto<List<CompositionDto>> getComposition(String userEmail, String noteProjectId) {
        try {
            projectPermissionChecker.requireMemberOrOwner(userEmail, noteProjectId);

            List<NoteProjectComposition> compositions =
                    noteProjectCompositionRepository.findAllByNoteProject_NoteProjectIdWithFetch(noteProjectId);

            List<CompositionDto> data =
                    compositions.stream()
                            .map(compositionAssembler::assemble)
                            .toList();


            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (IllegalArgumentException e) {
            return ResponseDto.setFailed(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    public ResponseDto<CompositionResponseDto> updatePositionComposition(String userEmail, String noteCompositionId, String noteProjectId, CompositionPositionRequestDto dto) {
        try {
            projectPermissionChecker.requireMemberOrOwner(userEmail, noteProjectId);

            NoteProjectComposition projectComposition = noteProjectCompositionRepository.findByNoteCompositionId(noteCompositionId, userEmail);

            NoteProjectComposition composition = projectComposition.toBuilder()
                    .compositionX(dto.getCompositionX())
                    .compositionY(dto.getCompositionY())
                    .compositionZ(dto.getCompositionZ())
                    .compositionZ2(dto.getCompositionZ2())
                    .build();
            noteProjectCompositionRepository.save(composition);
            CompositionResponseDto data = new CompositionResponseDto(composition);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (IllegalArgumentException e) {
            return ResponseDto.setFailed(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_DATA);
        }
    }
}
