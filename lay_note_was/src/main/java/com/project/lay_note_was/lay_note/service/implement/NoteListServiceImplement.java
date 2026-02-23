package com.project.lay_note_was.lay_note.service.implement;

import com.project.lay_note_was.lay_note.common.constant.ResponseMessage;
import com.project.lay_note_was.lay_note.component.ProjectPermissionChecker;
import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_list.NoteListDto;
import com.project.lay_note_was.lay_note.dto.note_list.NoteListItemDto;
import com.project.lay_note_was.lay_note.dto.note_list.request.NoteListRequestDto;
import com.project.lay_note_was.lay_note.dto.note_list.response.NoteListOneResponseDto;
import com.project.lay_note_was.lay_note.dto.note_list.response.NoteListResponseDto;
import com.project.lay_note_was.lay_note.entity.note_list.NoteList;
import com.project.lay_note_was.lay_note.entity.note_list_item.NoteListItem;
import com.project.lay_note_was.lay_note.entity.note_project.NoteProject;
import com.project.lay_note_was.lay_note.entity.note_project_composition.NoteComponentType;
import com.project.lay_note_was.lay_note.entity.note_project_composition.NoteProjectComposition;
import com.project.lay_note_was.lay_note.entity.note_project_user.NoteProjectUser;
import com.project.lay_note_was.lay_note.entity.note_project_user.UserRole;
import com.project.lay_note_was.lay_note.repository.*;
import com.project.lay_note_was.lay_note.service.NoteListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteListServiceImplement implements NoteListService {
    private final NoteListRepository noteListRepository;
    private final NoteProjectCompositionRepository noteProjectCompositionRepository;
    private final NoteListItemRepository noteListItemRepository;
    private final NoteProjectRepository noteProjectRepository;
    private final ProjectPermissionChecker projectPermissionChecker;

    @Override
    @Transactional
    public ResponseDto<NoteListOneResponseDto> createNoteList(String userEmail, String noteProjectId) {
        try {
            projectPermissionChecker.requireMemberOrOwner(userEmail, noteProjectId);

            NoteList noteList = NoteList.builder()
                    .noteListTitle("Note_List")
                    .build();
            noteListRepository.save(noteList);

            NoteListItem item = NoteListItem.builder()
                    .noteList(noteList)
                    .noteListContent("")
                    .noteListCheck(false)
                    .build();
            noteListItemRepository.save(item);

            NoteProject noteProject = noteProjectRepository.findById(noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteProject"));

            NoteProjectComposition composition = NoteProjectComposition.builder()
                    .compositionX(200)
                    .compositionY(200)
                    .compositionZ(0)
                    .noteList(noteList)
                    .noteComponentType(NoteComponentType.NOTELIST)
                    .noteProject(noteProject)
                    .build();
            noteProjectCompositionRepository.save(composition);


            NoteListOneResponseDto data = new NoteListOneResponseDto(noteList.getNoteListId());

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (IllegalArgumentException e) {
            return ResponseDto.setFailed(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseDto<NoteListOneResponseDto> updateNoteList(String userEmail, NoteListRequestDto dto, Long noteListId, String noteProjectId) {
        try {
            projectPermissionChecker.requireMemberOrOwner(userEmail, noteProjectId);
            NoteList noteList = noteListRepository.findByNoteListId(noteListId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA));

            noteList.setNoteListTitle(dto.getNoteListTitle());

            NoteListOneResponseDto data = new NoteListOneResponseDto(noteList);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (IllegalArgumentException e) {
            return ResponseDto.setFailed(e.getMessage());
        } catch (Exception e) {
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Transactional
    @Override
    public ResponseDto<Void> deleteNoteList(String userEmail, Long noteListId, String noteProjectId) {
        try {
            projectPermissionChecker.requireMemberOrOwner(userEmail, noteProjectId);

            NoteList noteList = noteListRepository.findById(noteListId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA));

            NoteProjectComposition composition = noteProjectCompositionRepository.findByNoteProject_NoteProjectIdAndNoteList_NoteListIdAndNoteComponentType(noteProjectId, noteListId, NoteComponentType.NOTELIST).orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteProjectComposition"));

            noteProjectCompositionRepository.delete(composition);
            noteListRepository.delete(noteList);
            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
        } catch (IllegalArgumentException e) {
            return ResponseDto.setFailed(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

}
