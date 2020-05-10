package ru.ifmo.swfoc.xmltoobject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Unit {
    private String xmlName;
    private String textId;
    private String faction;
    private boolean hasSpaceEvaluator;
}
