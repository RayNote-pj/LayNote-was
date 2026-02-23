package com.project.lay_note_was.lay_note.repository;

import com.project.lay_note_was.lay_note.entity.note_box.NoteBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoteBoxRepository extends JpaRepository<NoteBox, Long> {
    @Query("""
    SELECT nb
    FROM NoteBox nb
    JOIN nb.noteProjectCompositions npj
    JOIN npj.noteProject np
    JOIN np.noteProjectUsers npu
    WHERE nb.noteBoxId = :noteBoxId
    AND npu.user.userEmail= :userEmail
""")
    Optional<NoteBox> findByNoteBoxIdAndNoteUserEmail(Long noteBoxId, String userEmail);
}

