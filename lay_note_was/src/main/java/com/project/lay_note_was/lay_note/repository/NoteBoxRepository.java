package com.project.lay_note_was.lay_note.repository;

import com.project.lay_note_was.lay_note.entity.note_box.NoteBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteBoxRepository extends JpaRepository<NoteBox, Long> {

    @Query("""
    SELECT nb
    FROM NoteBox nb
    JOIN NoteProjectComposition npj
    JOIN User u
    Where nb.noteBoxId = npj.noteComponentId
    AND npj.noteComponentType = "NOTEBOX"
    AND u.userEmail = :userEmail
""")
    NoteBox findNoteBox(String userEmail, Long noteBoxId);
}

