package com.project.lay_note_was.lay_note.service.implement;

import com.project.lay_note_was.lay_note.common.constant.ResponseMessage;
import com.project.lay_note_was.lay_note.component.ProjectPermissionChecker;
import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.NoteImageBoxDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.NoteImageBoxListDto;
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
import com.project.lay_note_was.lay_note.service.ImageService;
import com.project.lay_note_was.lay_note.service.NoteImageBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class NoteImageBoxServiceImplement implements NoteImageBoxService {
    private final NoteImageBoxListRepository noteImageBoxListRepository;
    private final NoteImageBoxRepository noteImageBoxRepository;
    private final ProjectPermissionChecker projectPermissionChecker;
    private final ImageService imageService;
    private final NoteProjectCompositionRepository noteProjectCompositionRepository;

    @Override
    public ResponseDto<NoteImageBoxResponseDto> createImageBox(String userEmail, String noteProjectId, Long noteImageBoxListId, NoteImageBoxRequestDto dto) {
        try {
            projectPermissionChecker.requireMemberOrOwner(userEmail, noteProjectId);

            NoteImageBoxList noteImageBoxList = noteImageBoxListRepository.findByNoteImageBoxListId(noteImageBoxListId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteImageBoxList"));

            NoteImageBox noteImageBox = NoteImageBox.builder()
                    .noteImageBoxList(noteImageBoxList)
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
    @Transactional
    public ResponseDto<NoteImageBoxListOneDto> updateCaption(String userEmail, String noteProjectId, Long noteImageBoxId, NoteImageBoxRequestDto dto) {
        try {
            projectPermissionChecker.requireMemberOrOwner(userEmail, noteProjectId);

            NoteImageBox noteImageBox = noteImageBoxRepository.findById(noteImageBoxId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteImageBox"));

            noteImageBox.setImageCaption(dto.getImageCaption());

            NoteImageBoxDto response = new NoteImageBoxDto(noteImageBox);
            NoteImageBoxListOneDto data = new NoteImageBoxListOneDto(response);

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
    public ResponseDto<Void> deleteImageBox(String userEmail, String noteProjectId, Long noteImageBoxId) {
        try {
            projectPermissionChecker.requireMemberOrOwner(userEmail, noteProjectId);

            NoteImageBox noteImageBox = noteImageBoxRepository.findNoteImageBox(noteImageBoxId)
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

    @Override
    @Transactional
    public ResponseDto<NoteImageBoxListOneDto> updateImage(String userEmail, String noteProjectId, Long noteImageBoxId, MultipartFile imageUrl) {

        try {
            projectPermissionChecker.requireMemberOrOwner(userEmail, noteProjectId);

            String noteImgBoxPath = null;
            if (imageUrl != null && !imageUrl.isEmpty()) {
                noteImgBoxPath = imageService.convertImgFile(imageUrl, "note-img-box-image");
            }

            NoteImageBox noteImageBox = noteImageBoxRepository.findNoteImageBox(noteImageBoxId)
                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXIST_DATA + "noteImageBox"));

            noteImageBox.setImageUrl(noteImgBoxPath);
            NoteImageBoxDto dto = new NoteImageBoxDto(noteImageBox);
            NoteImageBoxListOneDto data = new NoteImageBoxListOneDto(dto);

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }
}
