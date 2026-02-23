package com.project.lay_note_was.lay_note.repository;

import com.project.lay_note_was.lay_note.entity.note_project.NoteProject;
import com.project.lay_note_was.lay_note.entity.note_project_user.NoteProjectUser;
import com.project.lay_note_was.lay_note.entity.note_project_user.NoteProjectUserId;
import com.project.lay_note_was.lay_note.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteProjectUserRepository extends JpaRepository<NoteProjectUser, NoteProjectUserId> {

    List<NoteProjectUser> findByNoteProject_NoteProjectId(String noteProjectId);

    Optional<NoteProjectUser> findByUserAndNoteProject(User user, NoteProject noteProject);

    Optional<NoteProjectUser> findByUser_UserEmailAndNoteProject_NoteProjectId(String userEmail, String noteProjectId);

    boolean existsByUser_UserEmailAndNoteProject_NoteProjectId(String userEmail, String noteProjectId);
}
