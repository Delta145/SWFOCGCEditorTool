package ru.ifmo.swfoc.xmltoobject.planet;

import lombok.Data;
import ru.ifmo.swfoc.xmltoobject.faction.Faction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@Data
@XmlRootElement(name = "Planets")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlanetWrapper implements Serializable {
    @XmlElement(name = "Planet")
    private List<Planet> planets;
}
