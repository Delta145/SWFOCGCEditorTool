package ru.ifmo.swfoc.converter;

import ru.ifmo.swfoc.editor.model.MPlanet;
import ru.ifmo.swfoc.editor.model.TerrainType;
import ru.ifmo.swfoc.io.DATLoader;
import ru.ifmo.swfoc.xmltoobject.planet.Planet;

public class PlanetConverter {
    
    private DATLoader datLoader;
    
    public PlanetConverter(DATLoader datLoader) {
        this.datLoader = datLoader;
    }
    
    public MPlanet toMPlanet(Planet planet) {
        MPlanet.MPlanetBuilder b = MPlanet.builder();
        String[] galactic_position = planet.getGalactic_Position().split(",");
        double x = Double.parseDouble(galactic_position[0]), y = Double.parseDouble(galactic_position[1]), z = Double.parseDouble(galactic_position[2]);

        b.xmlName(planet.getName())
            .name(datLoader.getInGameName(planet.getText_ID()))
            .x(x).y(y).z(z)
            .maxSpaceBaseLevel(planet.getMax_Space_Base())
            .maxStructuresLand(planet.getSpecial_Structures_Land())
            .maxStructuresSpace(planet.getSpecial_Structures_Space());

        if (planet.getTerrain() != null && planet.getTerrain().trim().length() > 0) {
            b.terrain(TerrainType.valueOf(planet.getTerrain().trim().toUpperCase()));
        }

        return b.build();
    } 
}
