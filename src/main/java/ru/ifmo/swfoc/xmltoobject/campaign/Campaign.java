package ru.ifmo.swfoc.xmltoobject.campaign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "Campaign")
@XmlAccessorType(XmlAccessType.FIELD)
public class Campaign implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlAttribute(name = "Name")
    private String Name;
    @XmlElement(name = "Campaign_Set")
    private String Campaign_Set;
    @XmlElement(name = "Text_ID")
    private String Text_ID;
    @XmlElement(name = "Sort_Order")
    private Integer Sort_Order;
    @XmlElement(name = "Description_Text")
    private String Description_Text;
    @XmlElement(name = "Camera_Shift_X")
    private Double Camera_Shift_X;
    @XmlElement(name = "Camera_Shift_Y")
    private Double Camera_Shift_Y;
    @XmlElement(name = "Camera_Distance")
    private Double Camera_Distance;
    @XmlElement(name = "Locations")
    private String Locations;
    @XmlElement(name = "Trade_Routes")
    private String Trade_Routes;
    @XmlElement(name = "Home_Location")
    private List<String> Home_Location;
    @XmlElement(name = "Starting_Active_Player")
    private String Starting_Active_Player;
    @XmlElement(name = "Rebel_Story_Name")
    private String Rebel_Story_Name;
    @XmlElement(name = "Empire_Story_Name")
    private String Empire_Story_Name;
    @XmlElement(name = "Underworld_Story_Name")
    private String Underworld_Story_Name;
    @XmlElement(name = "AI_Player_Control")
    private List<String> AI_Player_Control;
    @XmlElement(name = "Is_Multiplayer")
    private String Is_Multiplayer;
    @XmlElement(name = "Markup_Filename")
    private List<String> Markup_Filename;
    @XmlElement(name = "Supports_Custom_Settings")
    private String Supports_Custom_Settings;
    @XmlElement(name = "Show_Completed_Tab")
    private String Show_Completed_Tab;
    @XmlElement(name = "Human_Victory_Conditions")
    private String Human_Victory_Conditions;
    @XmlElement(name = "AI_Victory_Conditions")
    private String AI_Victory_Conditions;
    @XmlElement(name = "Special_Case_Production")
    private List<String> Special_Case_Production;
    @XmlElement(name = "Starting_Credits")
    private List<String> Starting_Credits;
    @XmlElement(name = "Starting_Tech_Level")
    private List<String> Starting_Tech_Level;
    @XmlElement(name = "Max_Tech_Level")
    private List<String> Max_Tech_Level;
    @XmlElement(name = "Starting_Forces")
    private List<String> Starting_Forces;
}
