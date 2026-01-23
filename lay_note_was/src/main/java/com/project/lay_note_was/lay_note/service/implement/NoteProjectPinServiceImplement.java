package com.project.lay_note_was.lay_note.service.implement;

import com.project.lay_note_was.lay_note.common.constant.ResponseMessage;
import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_project.NoteProjectDto;
import com.project.lay_note_was.lay_note.dto.note_project.response.NoteProjectListResponseDto;
import com.project.lay_note_was.lay_note.dto.note_project.response.NoteProjectResponseDto;
import com.project.lay_note_was.lay_note.dto.note_project_pin.PinDto;
import com.project.lay_note_was.lay_note.dto.note_project_pin.response.PinListResponseDto;
import com.project.lay_note_was.lay_note.dto.note_project_pin.response.PinResponseDto;
import com.project.lay_note_was.lay_note.entity.note_project.NoteProject;
import com.project.lay_note_was.lay_note.entity.note_project_pin.NoteProjectPin;
import com.project.lay_note_was.lay_note.entity.user.User;
import com.project.lay_note_was.lay_note.repository.NoteProjectPinRepository;
import com.project.lay_note_was.lay_note.repository.NoteProjectRepository;
import com.project.lay_note_was.lay_note.repository.UserRepository;
import com.project.lay_note_was.lay_note.service.NoteProjectPinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteProjectPinServiceImplement implements NoteProjectPinService {
    private final NoteProjectPinRepository noteProjectPinRepository;
    private final UserRepository userRepository;
    private final NoteProjectRepository noteProjectRepository;


    @Override
    public ResponseDto<PinListResponseDto> getPinAll(String userEmail) {
        try {
            List<NoteProjectPin> noteProjectPin = noteProjectPinRepository.findPinsWithProject(userEmail);
            List<PinDto> response = noteProjectPin.stream()
                    .map(PinDto::new)
                    .toList();
            PinListResponseDto data = new PinListResponseDto(response);
            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    public ResponseDto<PinResponseDto> createPin(String userEmail, String noteProjectId) {
        try {
            User user = userRepository.findByUserEmail(userEmail)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_USER));
            NoteProject noteProject = noteProjectRepository.findByNoteProjectId(noteProjectId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteProject"));
            NoteProjectPin noteProjectPin = NoteProjectPin.builder()
                    .noteProject(noteProject)
                    .user(user)
                    .build();
            noteProjectPinRepository.save(noteProjectPin);

            PinDto response = new PinDto(noteProjectPin);
            PinResponseDto data = new PinResponseDto(response);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    public ResponseDto<Void> deletePin(String userEmail, String pinId) {
        try {
            NoteProjectPin noteProjectPin = noteProjectPinRepository.findByPinId(pinId);
            noteProjectPinRepository.delete(noteProjectPin);
            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }
}
