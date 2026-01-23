package com.project.lay_note_was.lay_note.service.implement;

import com.project.lay_note_was.lay_note.common.constant.ResponseMessage;
import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_list.NoteListItemDto;
import com.project.lay_note_was.lay_note.dto.note_list.request.NoteListItemRequestDto;
import com.project.lay_note_was.lay_note.dto.note_list.response.NoteListItemResponseDto;
import com.project.lay_note_was.lay_note.entity.note_list.NoteList;
import com.project.lay_note_was.lay_note.entity.note_list_item.NoteListItem;
import com.project.lay_note_was.lay_note.entity.note_project.NoteProject;
import com.project.lay_note_was.lay_note.entity.note_project_composition.NoteComponentType;
import com.project.lay_note_was.lay_note.entity.note_project_composition.NoteProjectComposition;
import com.project.lay_note_was.lay_note.entity.note_project_user.NoteProjectUser;
import com.project.lay_note_was.lay_note.entity.note_project_user.UserRole;
import com.project.lay_note_was.lay_note.repository.*;
import com.project.lay_note_was.lay_note.service.NoteListItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoteListItemServiceImplement implements NoteListItemService {
    private final NoteListItemRepository noteListItemRepository;
    private final NoteProjectUserRepository noteProjectUserRepository;
    private final NoteListRepository noteListRepository;
    private final NoteProjectRepository noteProjectRepository;
    private final NoteProjectCompositionRepository noteProjectCompositionRepository;

    @Transactional
    @Override
    public ResponseDto<NoteListItemResponseDto> createNoteListItem(String userEmail, String noteProjectId, Long noteListId) {
        try {
            NoteProjectUser projectUser = noteProjectUserRepository.findByUser_UserEmailAndNoteProject_NoteProjectId(userEmail, noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_NOTE_PROJECT_MEMBER));
            if(!(projectUser.getUserRole() == UserRole.OWNER || projectUser.getUserRole() == UserRole.MEMBER)) {
                return ResponseDto.setFailed(ResponseMessage.NO_PERMISSION);
            }
            NoteList noteList = noteListRepository.findByNoteListId(noteListId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteList"));
            NoteListItem noteListItem = NoteListItem.builder()
                    .noteListContent("")
                    .noteListCheck(false)
                    .noteList(noteList)
                    .build();
            NoteListItem saveNote = noteListItemRepository.save(noteListItem);

            NoteListItemDto response = new NoteListItemDto(saveNote);
            NoteListItemResponseDto data = new NoteListItemResponseDto(response);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (IllegalArgumentException e) {
            return ResponseDto.setFailed(e.getMessage());
        }  catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Transactional
    @Override
    public ResponseDto<NoteListItemResponseDto> updateNoteListItem(String userEmail, String noteProjectId, Long noteListId, Long noteListItemId, NoteListItemRequestDto dto) {
        try {
            NoteProjectUser projectUser = noteProjectUserRepository.findByUser_UserEmailAndNoteProject_NoteProjectId(userEmail, noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_NOTE_PROJECT_MEMBER));
            if(!(projectUser.getUserRole() == UserRole.OWNER || projectUser.getUserRole() == UserRole.MEMBER)) {
                return ResponseDto.setFailed(ResponseMessage.NO_PERMISSION);
            }

            NoteListItem noteListItem = noteListItemRepository.findByNoteList_NoteListIdAndNoteListItemId(noteListId, noteListItemId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteListItem"));

            noteListItem.setNoteListCheck(dto.isNoteListCheck());
            noteListItem.setNoteListContent(dto.getNoteListContent());

            NoteListItemDto response = new NoteListItemDto(noteListItem);
            NoteListItemResponseDto data = new NoteListItemResponseDto(response);

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
    public ResponseDto<Void> deleteNoteListItem(String userEmail, Long noteListItemId, String noteProjectId) {
        try {
            NoteProjectUser projectUser = noteProjectUserRepository.findByUser_UserEmailAndNoteProject_NoteProjectId(userEmail, noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_NOTE_PROJECT_MEMBER));
            if(!(projectUser.getUserRole() == UserRole.OWNER || projectUser.getUserRole() == UserRole.MEMBER)) {
                return ResponseDto.setFailed(ResponseMessage.NO_PERMISSION);
            }

            NoteListItem noteListItem = noteListItemRepository.findByNoteProjectIdAndNoteListItemId(noteProjectId, noteListItemId, NoteComponentType.NOTELIST)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA));

            noteListItemRepository.delete(noteListItem);
            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
        }  catch (IllegalArgumentException e) {
            return ResponseDto.setFailed(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }
}
