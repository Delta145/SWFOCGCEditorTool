package ru.ifmo.swfoc.editor.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MPlanet {
    private String name;
    private String xmlName;
    private double x, y, z;
    private int maxSpaceBaseLevel;
    private int maxStructuresLand;
    private int maxStructuresSpace;
    private TerrainType terrain;
}
