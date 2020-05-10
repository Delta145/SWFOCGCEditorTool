package ru.ifmo.swfoc.editor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MUnit {
    private String name;
    private String xmlName;
    private List<MFaction> factions;
}
