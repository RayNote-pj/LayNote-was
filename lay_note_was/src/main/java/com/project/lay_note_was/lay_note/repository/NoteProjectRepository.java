package com.project.lay_note_was.lay_note.repository;

import com.project.lay_note_was.lay_note.entity.note_project.NoteProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteProjectRepository extends JpaRepository<NoteProject, String> {
    @Query(value = """
    SELECT np FROM NoteProject np
    JOIN FETCH np.user
    WHERE np.user.userEmail = :userEmail
    AND np.deletedAt IS NULL
    ORDER BY np.updatedAt DESC
""")
    List<NoteProject> findByUser_UserEmail(String userEmail);

    Optional<NoteProject> findByUser_UserEmailAndNoteProjectId(String userEmail, String noteProjectId);

    @Query(value = """
    SELECT np FROM NoteProject np
    WHERE np.user.userEmail = :userEmail
    AND np.deletedAt IS NOT NULL
    ORDER BY np.deletedAt DESC
""")
    List<NoteProject> WasteFindByUser_UserEmail(@Param("userEmail") String userEmail);

    Optional<NoteProject> findByNoteProjectId(String noteProjectId);
}
