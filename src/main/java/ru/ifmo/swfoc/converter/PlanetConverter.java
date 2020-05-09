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

        b.xmlName(planet.getName());
        b.name(datLoader.getValue(planet.getText_ID()));
        b.x(x); b.y(y); b.z(z);
        b.maxSpaceBaseLevel(planet.getMax_Space_Base());
        b.maxStructuresLand(planet.getSpecial_Structures_Land());
        b.maxStructuresSpace(planet.getSpecial_Structures_Space());
        if (planet.getTerrain() != null)
            b.terrain(TerrainType.valueOf(planet.getTerrain().trim().toUpperCase()));
        return b.build();
    } 
}
