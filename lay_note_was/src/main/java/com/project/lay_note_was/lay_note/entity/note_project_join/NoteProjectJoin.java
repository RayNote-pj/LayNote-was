package com.project.lay_note_was.lay_note.entity.note_project_join;

import com.project.lay_note_was.lay_note.entity.note_project.NoteProject;
import com.project.lay_note_was.lay_note.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "note_project_joins")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteProjectJoin {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "project_join_id", columnDefinition = "CHAR(36)")
    private String noteProjectJoinId;

    @ManyToOne
    @JoinColumn(name = "note_project_id", columnDefinition = "CHAR(36)")
    private NoteProject noteProject;

    @ManyToOne
    @JoinColumn(name = "user_id", columnDefinition = "CHAR(36)")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "join_status", nullable = false, insertable = false)
    private JoinStatus joinStatus;

    @Column(name = "CREATED_AT", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "DECIDED_AT", insertable = false)
    private LocalDateTime decidedAt;
}
