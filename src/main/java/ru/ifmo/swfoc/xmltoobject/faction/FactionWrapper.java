package ru.ifmo.swfoc.xmltoobject.faction;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@Data
@XmlRootElement(name = "Factions")
@XmlAccessorType(XmlAccessType.FIELD)
public class FactionWrapper implements Serializable {
    @XmlElement(name = "Faction")
    private List<Faction> factions;
}
