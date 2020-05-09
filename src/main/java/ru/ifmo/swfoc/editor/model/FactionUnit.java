package ru.ifmo.swfoc.editor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FactionUnit {
    private String unitName;
    private MFaction owner;
}
