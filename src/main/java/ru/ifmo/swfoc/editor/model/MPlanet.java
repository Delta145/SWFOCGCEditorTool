package ru.ifmo.swfoc.editor.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MPlanet {
    private String name;
    private String xmlName;
    private Double x, y, z;
    private Integer maxSpaceBaseLevel;
    private Integer maxStructuresLand;
    private Integer maxStructuresSpace;
    private TerrainType terrain;
}
