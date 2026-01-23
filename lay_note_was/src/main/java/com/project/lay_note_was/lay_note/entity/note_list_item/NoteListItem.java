package com.project.lay_note_was.lay_note.entity.note_list_item;

import com.project.lay_note_was.lay_note.entity.note_list.NoteList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "note_list_items")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class NoteListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_list_item_id")
    private Long noteListItemId;

    @ManyToOne
    @JoinColumn(name= "note_list_id")
    private NoteList noteList;

    @Column(name = "note_list_content")
    private String noteListContent;

    @Column(name = "note_list_check")
    private boolean noteListCheck;
}
