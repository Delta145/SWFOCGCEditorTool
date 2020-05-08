package ru.ifmo.swfoc.xmltoobject.traderoute;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@Data
@XmlRootElement(name = "TradeRoute")
@XmlAccessorType(XmlAccessType.FIELD)
public class TradeRoute implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlAttribute(name = "Name")
    private String Name;
    @XmlElement(name = "Point_A")
    private String pointA;
    @XmlElement(name = "Point_B")
    private String pointB;
}
