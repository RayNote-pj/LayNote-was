package com.project.lay_note_was.lay_note.repository;

import com.project.lay_note_was.lay_note.entity.note_list_item.NoteListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoteListItemRepository extends JpaRepository<NoteListItem, Long> {
    @Query("""
        SELECT nlt FROM NoteListItem nlt
        JOIN nlt.noteList nl
        JOIN nl.noteProjectCompositions npc
        WHERE npc.noteComponentType = "NOTELIST"
        AND nlt.noteListItemId = :noteListItemId
""")
    Optional<NoteListItem> findByNoteListItemId(Long noteListItemId);
}
