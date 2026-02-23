package com.project.lay_note_was.lay_note.repository;

import com.project.lay_note_was.lay_note.entity.note_list.NoteList;
import com.project.lay_note_was.lay_note.entity.note_project_composition.NoteComponentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteListRepository extends JpaRepository<NoteList, Long> {
    @Query("""
    SELECT nl FROM NoteList nl
    JOIN NoteProjectComposition nc
    JOIN nc.noteProject p
    JOIN p.user u
    LEFT JOIN FETCH nl.noteListItems
    WHERE p.noteProjectId = :noteProjectId
    AND nc.noteComponentType = :noteComponentType
    AND u.userEmail = :userEmail
""")
    List<NoteList> findByUserEmailAndCompositionId(
            @Param("userEmail") String userEmail,
            @Param("noteProjectId") String noteProjectId,
            @Param("noteComponentType")NoteComponentType noteComponentType
    );

    @Query("""
        SELECT nl FROM NoteList nl
        JOIN nl.noteProjectCompositions npc
        WHERE npc.noteComponentType = "NOTELIST"
        AND nl.noteListId = :noteListId
""")
    Optional<NoteList> findByNoteListId(Long noteListId);
}
