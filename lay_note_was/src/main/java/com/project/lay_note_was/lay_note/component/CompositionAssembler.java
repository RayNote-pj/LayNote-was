package com.project.lay_note_was.lay_note.component;

import com.project.lay_note_was.lay_note.dto.note_box.NoteBoxDto;
import com.project.lay_note_was.lay_note.dto.note_image_box.NoteImageBoxListDto;
import com.project.lay_note_was.lay_note.dto.note_list.NoteListDto;
import com.project.lay_note_was.lay_note.dto.note_project_composition.CompositionDto;
import com.project.lay_note_was.lay_note.entity.note_box.NoteBox;
import com.project.lay_note_was.lay_note.entity.note_image_box.NoteImageBoxList;
import com.project.lay_note_was.lay_note.entity.note_list.NoteList;

import com.project.lay_note_was.lay_note.entity.note_project_composition.NoteProjectComposition;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompositionAssembler {

    public CompositionDto assemble(
            NoteProjectComposition composition
    ) {
        CompositionDto dto =
                new CompositionDto(composition);
        ;

        switch (composition.getNoteComponentType()) {

            case NOTEBOX -> {
                NoteBox noteBox = composition.getNoteBox();

                if (noteBox != null) {
                    dto.setNoteBox(new NoteBoxDto(noteBox));
                }
            }

            case NOTELIST -> {
                NoteList noteList = composition.getNoteList();

                if (noteList != null) {
                    dto.setNoteList(new NoteListDto(noteList));
                }
            }

            case NOTEIMAGEBOX -> {
                NoteImageBoxList noteImageBoxList = (composition.getNoteImageBoxList());

                if (noteImageBoxList != null) {
                    dto.setNoteImageBoxList(new NoteImageBoxListDto(noteImageBoxList));
                }
            }
        }

        return dto;
    }
}

