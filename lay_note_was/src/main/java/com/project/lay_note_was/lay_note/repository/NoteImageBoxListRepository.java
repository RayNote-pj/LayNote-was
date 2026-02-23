package com.project.lay_note_was.lay_note.repository;

import com.project.lay_note_was.lay_note.entity.note_image_box.NoteImageBox;
import com.project.lay_note_was.lay_note.entity.note_image_box.NoteImageBoxList;
import com.project.lay_note_was.lay_note.entity.note_project_composition.NoteComponentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface NoteImageBoxListRepository extends JpaRepository<NoteImageBoxList, Long> {
    Optional<NoteImageBoxList> findByNoteImageBoxListId(Long noteImageBoxListId);
}
