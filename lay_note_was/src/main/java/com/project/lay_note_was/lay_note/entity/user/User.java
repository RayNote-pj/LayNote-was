package com.project.lay_note_was.lay_note.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.lay_note_was.lay_note.entity.note_project_user.NoteProjectUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", columnDefinition = "CHAR(36)")
    private String userId;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "user_password", nullable = false)
    @JsonIgnoreProperties
    private String password;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "nick_name", nullable = false)
    private String nickName;

    @Column(name = "user_phone", nullable = false)
    private String userPhone;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoteProjectUser> noteProjectUsers;
}
