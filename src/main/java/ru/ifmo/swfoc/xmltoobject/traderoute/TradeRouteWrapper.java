package ru.ifmo.swfoc.xmltoobject.traderoute;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@Data
@XmlRootElement(name = "TradeRoutes")
@XmlAccessorType(XmlAccessType.FIELD)
public class TradeRouteWrapper implements Serializable {
    @XmlElement(name = "TradeRoute")
    private List<TradeRoute> tradeRoutes;
}
