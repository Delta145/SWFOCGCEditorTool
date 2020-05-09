package ru.ifmo.swfoc.editor.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class MCampaign {
    private String fileName;
    private String xmlName;
    private String name;
    private String campaignSet;
    private Integer sortOrder;
    private String description;
    private Double cameraShiftX;
    private Double cameraShiftY;
    private Double cameraDistance;
    private List<MPlanet> locations;
    private List<MTradeRoute> tradeRoutes;
    private Map<MFaction, MPlanet> homeLocations;
    private MFaction singlePlayerActivePlayer;
    private String singlePlayerRebelStory;
    private String singlePlayerEmpireStory;
    private String singlePlayerUnderworldStory;
    private Map<MFaction, String> AI_Player_Control;
    private Boolean isMultiplayer;
    private Map<MFaction, String> markupFiles;
    private Boolean supportsCustomSettings;
    private Boolean showCompletedTab;
    private List<String> humanVictoryConditions;
    private List<String> aiVictoryConditions;
    private Map<MPlanet, String> specialCaseProduction;
    private Map<MFaction, Integer> startingCredits;
    private Map<MFaction, Integer> startingTech;
    private Map<MFaction, Integer> maxTech;
    private Map<MPlanet, FactionUnit> Starting_Forces;
}
