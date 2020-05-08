package ru.ifmo.swfoc.xmltoobject.faction;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@Data
@XmlRootElement(name = "Faction")
@XmlAccessorType(XmlAccessType.FIELD)
public class Faction implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlAttribute(name = "Name")
    private String Name;
    @XmlElement(name = "Text_ID")
    private String Text_ID;
}
