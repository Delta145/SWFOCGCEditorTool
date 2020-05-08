package ru.ifmo.swfoc.xmltoobject.planet;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@Data
@XmlRootElement(name = "Planet")
@XmlAccessorType(XmlAccessType.FIELD)
public class Planet implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlAttribute(name = "Name")
    private String Name;
    @XmlElement(name = "Text_ID")
    private String Text_ID;
    @XmlElement(name = "Galactic_Position")
    private String Galactic_Position;
    @XmlElement(name = "Max_Space_Base")
    private int Max_Space_Base;
    @XmlElement(name = "Special_Structures_Land")
    private int Special_Structures_Land;
    @XmlElement(name = "Special_Structures_Space")
    private int Special_Structures_Space;
    @XmlElement(name = "Terrain")
    private String Terrain;
}
