package com.project.lay_note_was.lay_note.repository;

import com.project.lay_note_was.lay_note.dto.note_project_composition.CompositionDto;
import com.project.lay_note_was.lay_note.entity.note_project_composition.NoteComponentType;
import com.project.lay_note_was.lay_note.entity.note_project_composition.NoteProjectComposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteProjectCompositionRepository extends JpaRepository<NoteProjectComposition, String> {

    @Query("""
    SELECT c FROM NoteProjectComposition c
    JOIN c.noteProject np
    JOIN np.noteProjectUsers npu
    WHERE c.noteCompositionId = :noteCompositionId
    AND npu.user.userEmail = :userEmail
""")
    NoteProjectComposition findByNoteCompositionId(String noteCompositionId, String userEmail);

    @Query("""
    SELECT npc
    FROM NoteProjectComposition  npc
    JOIN FETCH npc.noteProject np
    JOIN FETCH np.user
    LEFT JOIN FETCH npc.noteBox nb
    LEFT JOIN FETCH npc.noteList nl
    LEFT JOIN FETCH nl.noteListItems
    LEFT JOIN FETCH npc.noteImageBoxList nibl
    WHERE np.noteProjectId = :noteProjectId
""")
    List<NoteProjectComposition> findAllByNoteProject_NoteProjectIdWithFetch(@Param("noteProjectId") String noteProjectId);

    Optional<NoteProjectComposition> findByNoteProject_NoteProjectIdAndNoteBox_NoteBoxIdAndNoteComponentType(String noteProject_noteProjectId, Long noteBoxId, NoteComponentType noteComponentType);

    Optional<NoteProjectComposition> findByNoteProject_NoteProjectIdAndNoteImageBoxList_NoteImageBoxListIdAndNoteComponentType(String noteProject_noteProjectId, Long noteImageBoxListId, NoteComponentType noteComponentType);

    Optional<NoteProjectComposition> findByNoteProject_NoteProjectIdAndNoteList_NoteListIdAndNoteComponentType(String noteProject_noteProjectId, Long noteListId, NoteComponentType noteComponentType);

}
