package com.project.lay_note_was.lay_note.entity.note_list;

import com.project.lay_note_was.lay_note.entity.note_list_item.NoteListItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "note_list")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_list_id", nullable = false)
    private Long noteListId;

    @Column(name = "note_list_title")
    private String noteListTitle;

    @OneToMany(mappedBy = "noteList", fetch = FetchType.LAZY)
    private List<NoteListItem> noteListItems = new ArrayList<>();
}
