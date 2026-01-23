package com.project.lay_note_was.lay_note.entity.note_project;

import com.project.lay_note_was.lay_note.entity.note_project_pin.NoteProjectPin;
import com.project.lay_note_was.lay_note.entity.note_project_user.NoteProjectUser;
import com.project.lay_note_was.lay_note.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "note_projects")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class NoteProject {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "note_project_id", columnDefinition = "CHAR(36)")
    private String noteProjectId;

    @Column(name = "note_project_image_url", nullable = false)
    private String noteProjectImageUrl;

    @ManyToOne
    @JoinColumn(name = "note_project_owner_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "noteProject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoteProjectPin> noteProjectPin;

    @Column(name = "note_project_title", nullable = false)
    private String noteProjectTitle;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "noteProject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoteProjectUser> noteProjectUsers;
}
