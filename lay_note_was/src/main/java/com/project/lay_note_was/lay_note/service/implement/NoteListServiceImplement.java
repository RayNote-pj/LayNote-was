package com.project.lay_note_was.lay_note.service.implement;

import com.project.lay_note_was.lay_note.common.constant.ResponseMessage;
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
    private final NoteProjectUserRepository noteProjectUserRepository;
    private final NoteProjectCompositionRepository noteProjectCompositionRepository;
    private final NoteListItemRepository noteListItemRepository;
    private final NoteProjectRepository noteProjectRepository;

    @Transactional
    @Override
    public ResponseDto<NoteListOneResponseDto> createNoteList(String userEmail, String noteProjectId) {
        try {
            NoteProjectUser projectUser = noteProjectUserRepository.findByUser_UserEmailAndNoteProject_NoteProjectId(userEmail, noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_NOTE_PROJECT_MEMBER));
            if(!(projectUser.getUserRole() == UserRole.OWNER || projectUser.getUserRole() == UserRole.MEMBER)) {
                return ResponseDto.setFailed(ResponseMessage.NO_PERMISSION);
            }
            NoteList noteList = NoteList.builder()
                    .noteListTitle("Untitled")
                    .build();
            noteListRepository.save(noteList);

            NoteProject noteProject = noteProjectRepository.findById(noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteProject"));

            NoteProjectComposition composition = NoteProjectComposition.builder()
                    .compositionX(200)
                    .compositionY(200)
                    .compositionZ(1)
                    .compositionWidth(200)
                    .compositionHeight(300)
                    .noteComponentType(NoteComponentType.NOTELIST)
                    .noteComponentId(noteList.getNoteListId())
                    .noteProject(noteProject)
                    .build();
            noteProjectCompositionRepository.save(composition);

            NoteListItem item = NoteListItem.builder()
                    .noteList(noteList)
                    .build();
            noteListItemRepository.save(item);

            NoteListOneResponseDto data = new NoteListOneResponseDto(noteList);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (IllegalArgumentException e) {
            return ResponseDto.setFailed(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    public ResponseDto<NoteListResponseDto> getNoteList(String userEmail, String noteProjectId) {
        try {
            NoteProjectUser projectUser = noteProjectUserRepository.findByUser_UserEmailAndNoteProject_NoteProjectId(userEmail, noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_NOTE_PROJECT_MEMBER));
            if(!(projectUser.getUserRole() == UserRole.OWNER || projectUser.getUserRole() == UserRole.MEMBER)) {
                return ResponseDto.setFailed(ResponseMessage.NO_PERMISSION);
            }

            List<NoteList> noteList = noteListRepository.findByUserEmailAndCompositionId(userEmail, noteProjectId, NoteComponentType.NOTELIST);
            List<NoteListDto> noteListDto = noteList.stream()
                    .map(list -> {
                        List<NoteListItemDto> notes = noteListItemRepository
                                .findAllByNoteList_NoteListId(list.getNoteListId())
                                .stream()
                                .map(NoteListItemDto::new)
                                .toList();

                        return new NoteListDto(list, notes);
                    })
                    .toList();
            NoteListResponseDto data = new NoteListResponseDto(noteListDto);

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
    public ResponseDto<NoteListOneResponseDto> updateNoteList(String userEmail, NoteListRequestDto dto, Long noteListId, String noteProjectId) {
        try {
            NoteProjectUser projectUser = noteProjectUserRepository.findByUser_UserEmailAndNoteProject_NoteProjectId(userEmail, noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_NOTE_PROJECT_MEMBER));
            if(!(projectUser.getUserRole() == UserRole.OWNER || projectUser.getUserRole() == UserRole.MEMBER)) {
                return ResponseDto.setFailed(ResponseMessage.NO_PERMISSION);
            }
            NoteList noteList = noteListRepository.findById(noteListId)
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
            NoteProjectUser projectUser = noteProjectUserRepository.findByUser_UserEmailAndNoteProject_NoteProjectId(userEmail, noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_NOTE_PROJECT_MEMBER));

            if(!(projectUser.getUserRole() == UserRole.OWNER || projectUser.getUserRole() == UserRole.MEMBER)) {
                return ResponseDto.setFailed(ResponseMessage.NO_PERMISSION);
            }

            NoteList noteList = noteListRepository.findById(noteListId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA));

            NoteProjectComposition composition = noteProjectCompositionRepository.findByComponentTypeAndTargetIdAndNoteProject_noteProjectId(NoteComponentType.NOTELIST, noteListId, noteProjectId).orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteProjectComposition"));

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
