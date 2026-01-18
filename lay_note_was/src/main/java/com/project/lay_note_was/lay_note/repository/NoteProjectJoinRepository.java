package com.project.lay_note_was.lay_note.repository;

import com.project.lay_note_was.lay_note.entity.note_project_join.JoinStatus;
import com.project.lay_note_was.lay_note.entity.note_project_join.NoteProjectJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteProjectJoinRepository extends JpaRepository<NoteProjectJoin, String> {
    Optional<NoteProjectJoin> findByNoteProjectJoinId(String noteProjectJoinId);

    List<NoteProjectJoin> findByNoteProject_NoteProjectId(String noteProjectId);

    Optional<NoteProjectJoin> findByNoteProject_NoteProjectIdAndUser_UserEmailAndJoinStatus(String noteProjectId, String userEmail, JoinStatus joinStatus);
}
