package ru.ifmo.swfoc.xmltoobject.campaign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "Campaigns")
@XmlAccessorType(XmlAccessType.FIELD)
public class CampaignWrapper implements Serializable {
    @XmlTransient
    private String fileName;
    @XmlElement(name = "Campaign")
    private List<Campaign> campaigns;
}
