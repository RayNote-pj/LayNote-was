package com.project.lay_note_was.lay_note.service.implement;

import com.project.lay_note_was.lay_note.common.constant.ResponseMessage;
import com.project.lay_note_was.lay_note.component.ProjectPermissionChecker;
import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_list.request.NoteListItemRequestDto;
import com.project.lay_note_was.lay_note.dto.note_list.response.NoteListItemResponseDto;
import com.project.lay_note_was.lay_note.entity.note_list.NoteList;
import com.project.lay_note_was.lay_note.entity.note_list_item.NoteListItem;
import com.project.lay_note_was.lay_note.repository.*;
import com.project.lay_note_was.lay_note.service.NoteListItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoteListItemServiceImplement implements NoteListItemService {
    private final NoteListItemRepository noteListItemRepository;
    private final NoteListRepository noteListRepository;
    private final ProjectPermissionChecker projectPermissionChecker;

    @Override
    @Transactional
    public ResponseDto<NoteListItemResponseDto> createNoteListItem(String userEmail, String noteProjectId, Long noteListId) {
        try {
            projectPermissionChecker.requireMemberOrOwner(userEmail, noteProjectId);

            NoteList noteList = noteListRepository.findByNoteListId(noteListId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteList"));
            NoteListItem noteListItem = NoteListItem.builder()
                    .noteListContent("")
                    .noteListCheck(false)
                    .noteList(noteList)
                    .build();
            NoteListItem note = noteListItemRepository.save(noteListItem);
            NoteListItemResponseDto data = new NoteListItemResponseDto(note);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (IllegalArgumentException e) {
            return ResponseDto.setFailed(e.getMessage());
        }  catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseDto<NoteListItemResponseDto> updateNoteListItem(String userEmail, String noteProjectId, Long noteListItemId, NoteListItemRequestDto dto) {
        try {
            projectPermissionChecker.requireMemberOrOwner(userEmail, noteProjectId);

            NoteListItem noteListItem = noteListItemRepository.findByNoteListItemId(noteListItemId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteListItem"));

            noteListItem.setNoteListCheck(dto.isNoteListCheck());
            noteListItem.setNoteListContent(dto.getNoteListContent());

            NoteListItemResponseDto data = new NoteListItemResponseDto(noteListItem);

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
    public ResponseDto<Void> deleteNoteListItem(String userEmail, Long noteListItemId, String noteProjectId) {
        try {
            projectPermissionChecker.requireMemberOrOwner(userEmail, noteProjectId);

            NoteListItem noteListItem = noteListItemRepository.findByNoteListItemId(noteListItemId)
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

    @Override
    @Transactional
    public ResponseDto<NoteListItemResponseDto> updateCheck(String userEmail, String noteProjectId, Long noteListItemId, Boolean noteListCheck) {
        try {
            projectPermissionChecker.requireMemberOrOwner(userEmail, noteProjectId);

            NoteListItem noteListItem = noteListItemRepository.findByNoteListItemId(noteListItemId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteListItem"));
            noteListItem.setNoteListCheck(noteListCheck);

            NoteListItemResponseDto data = new NoteListItemResponseDto(noteListItem);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }
}
