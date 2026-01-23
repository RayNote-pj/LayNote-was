package com.project.lay_note_was.lay_note.repository;

import com.project.lay_note_was.lay_note.entity.note_project_pin.NoteProjectPin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteProjectPinRepository extends JpaRepository<NoteProjectPin, String> {
    List<NoteProjectPin> findByUser_UserEmailOrderByNoteProject_UpdatedAtDesc(String userEmail);

    @Query("""
    select p
    from NoteProjectPin p
    join fetch p.noteProject
    where p.user.userEmail = :userEmail
    order by p.noteProject.updatedAt desc
""")
    List<NoteProjectPin> findPinsWithProject(@Param("userEmail") String userEmail);


    NoteProjectPin findByPinId(String pinId);
}
