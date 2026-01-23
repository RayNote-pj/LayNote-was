package com.project.lay_note_was.lay_note.dto.note_project_pin.response;

import com.project.lay_note_was.lay_note.dto.note_project_pin.PinDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PinListResponseDto {
    private List<PinDto> pinDto;
}
