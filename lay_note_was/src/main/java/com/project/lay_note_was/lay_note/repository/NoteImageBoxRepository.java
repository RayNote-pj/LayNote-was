package com.project.lay_note_was.lay_note.repository;

import com.project.lay_note_was.lay_note.entity.note_image_box.NoteImageBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface NoteImageBoxRepository extends JpaRepository<NoteImageBox, Long> {
    @Query("""
    SELECT nib
    FROM NoteImageBox nib
    JOIN nib.noteImageBoxList nbl
    JOIN nbl.noteProjectCompositions npj
    JOIN npj.noteProject np
    JOIN np.noteProjectUsers npu
    WHERE npj.noteComponentType = 'NOTEIMAGEBOX'
    AND nib.noteImageBoxId = :noteImageBoxId
""")
    Optional<NoteImageBox> findNoteImageBox(Long noteImageBoxId);
}
