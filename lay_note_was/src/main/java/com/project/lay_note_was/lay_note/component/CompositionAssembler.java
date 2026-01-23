package com.project.lay_note_was.lay_note.component;

import com.project.lay_note_was.lay_note.dto.note_box.NoteBoxDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.NoteImageBoxListDto;
import com.project.lay_note_was.lay_note.dto.note_list.NoteListDto;
import com.project.lay_note_was.lay_note.dto.note_project_composition.CompositionDto;
import com.project.lay_note_was.lay_note.repository.NoteBoxRepository;
import com.project.lay_note_was.lay_note.repository.NoteImageBoxListRepository;
import com.project.lay_note_was.lay_note.repository.NoteListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompositionAssembler {
    private final NoteBoxRepository noteBoxRepository;
    private final NoteListRepository noteListRepository;
    private final NoteImageBoxListRepository noteImageBoxListRepository;

    public CompositionDto assemble(CompositionDto composition) {
        CompositionDto dto =
                new CompositionDto(composition);
        System.out.println(
                "[ASSEMBLE] compositionId=" + composition.getNoteCompositionId()
                        + ", type=" + composition.getNoteComponentType()
                        + ", componentId=" + composition.getNoteComponentId()
        );

        switch (composition.getNoteComponentType()) {
            case NOTEBOX -> {
                System.out.println("[ASSEMBLE] NOTEBOX");
                noteBoxRepository.findById(composition.getNoteComponentId())
                        .ifPresent(noteBox ->
                                dto.setNoteBox(new NoteBoxDto(noteBox))
                        );
            }

            case NOTELIST -> {
                System.out.println("[ASSEMBLE] NOTELIST");
                noteListRepository.findById(composition.getNoteComponentId())
                        .ifPresent(noteList ->
                                dto.setNoteList(new NoteListDto(noteList))
                        );
            }

            case NOTEIMAGEBOX -> {
                System.out.println("[ASSEMBLE] NOTEIMAGEBOX");
                noteImageBoxListRepository.findById(composition.getNoteComponentId())
                        .ifPresent(list ->
                                dto.setNoteImageBoxList(
                                        new NoteImageBoxListDto(list)
                                )
                        );
            }
        }

        return dto;
    }
}

