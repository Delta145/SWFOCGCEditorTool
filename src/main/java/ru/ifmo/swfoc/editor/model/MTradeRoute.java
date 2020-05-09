package ru.ifmo.swfoc.editor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.ifmo.swfoc.xmltoobject.planet.Planet;

@AllArgsConstructor
@Data
public class MTradeRoute {
    private String name;
    private MPlanet pointA;
    private MPlanet pointB;
}
