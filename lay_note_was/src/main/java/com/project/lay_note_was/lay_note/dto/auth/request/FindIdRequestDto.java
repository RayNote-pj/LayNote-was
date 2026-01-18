package com.project.lay_note_was.lay_note.dto.auth.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindIdRequestDto {
    private String emailAddress;
    private String name;
    private String userPhone;
}
