package ru.ifmo.swfoc.converter;

import ru.ifmo.swfoc.editor.model.*;
import ru.ifmo.swfoc.io.DATLoader;
import ru.ifmo.swfoc.xmltoobject.campaign.Campaign;

import java.util.*;

public class CampaignConverter {
    private DATLoader datLoader;
    private Map<String, MPlanet> planets;
    private Map<String, MFaction> factions;
    private Map<String, MTradeRoute> routes;
    private Map<String, MUnit> units;

    public CampaignConverter(DATLoader datLoader, Map<String, MPlanet> planets, Map<String, MFaction> factions, Map<String, MTradeRoute> tradeRoutes, Map<String, MUnit> units) {
        this.datLoader = datLoader;
        this.planets = planets;
        this.factions = factions;
        this.routes = tradeRoutes;
        this.units = units;
    }

    private boolean validStr(String str) {
        return str != null && str.length() > 0;
    }

    public MCampaign toMCampaign(Campaign campaign, String filename) {
        MCampaign.MCampaignBuilder b = MCampaign.builder();

        String campaignName = datLoader.getInGameName(campaign.getText_ID().trim());
        String description = datLoader.getInGameName(campaign.getDescription_Text().trim());

        MFaction activePlayer = null;
        if (validStr(campaign.getStarting_Active_Player()))
            activePlayer = factions.get(campaign.getStarting_Active_Player().trim());

        Boolean isMultiplayer = null;
        if (validStr(campaign.getIs_Multiplayer()))
            isMultiplayer = campaign.getIs_Multiplayer().trim().equalsIgnoreCase("yes");

        Boolean showCompletedTab = null;
        if (campaign.getShow_Completed_Tab() != null)
            showCompletedTab = campaign.getShow_Completed_Tab().trim().equalsIgnoreCase("true");

        Boolean supportCustomSettings = null;
        if (campaign.getSupports_Custom_Settings() != null)
            supportCustomSettings = campaign.getSupports_Custom_Settings().trim().equalsIgnoreCase("true");

        List<MPlanet> locations = new ArrayList<>();
        String[] locationStrings = campaign.getLocations().trim().split(",");
        for (String locationString : locationStrings) {
            locations.add(planets.get(locationString.trim()));
        }

        List<MTradeRoute> tradeRoutes = new ArrayList<>();
        if (validStr(campaign.getTrade_Routes())) {
            String[] trade_routes = campaign.getTrade_Routes().trim().split(",");
            for (String tradeRoute : trade_routes) {
                tradeRoutes.add(routes.get(tradeRoute.trim()));
            }
        }

        List<String> aiVictoryConditions = new ArrayList<>();
        if (validStr(campaign.getAI_Victory_Conditions())) {
            String[] victory_conditions = campaign.getAI_Victory_Conditions().trim().split(",");
            for (String victory_condition : victory_conditions) {
                aiVictoryConditions.add(victory_condition.trim());
            }
        }

        List<String> humanVictoryConditions = new ArrayList<>();
        if (validStr(campaign.getHuman_Victory_Conditions())) {
            String[] victory_conditions = campaign.getHuman_Victory_Conditions().trim().split(",");
            for (String victory_condition : victory_conditions) {
                humanVictoryConditions.add(victory_condition.trim());
            }
        }

        Map<MFaction, Integer> maxTechs = new HashMap<>();
        if (campaign.getMax_Tech_Level() != null) {
            List<String> max_tech_level = campaign.getMax_Tech_Level();
            for (String s : max_tech_level) {
                String[] factionTech = s.trim().split(",");
                maxTechs.put(factions.get(factionTech[0].trim()), Integer.parseInt(factionTech[1].trim()));
            }
        }

        Map<MFaction, Integer> startingTechs = new HashMap<>();
        List<String> starting_tech_level = campaign.getStarting_Tech_Level();
        for (String s : starting_tech_level) {
            String[] factionTech = s.trim().split(",");
            startingTechs.put(factions.get(factionTech[0].trim()), Integer.parseInt(factionTech[1].trim()));
        }

        Map<MFaction, Integer> startingCredits = new HashMap<>();
        List<String> starting_credits = campaign.getStarting_Credits();
        for (String s : starting_credits) {
            String[] factionCredits = s.trim().split(",");
            startingCredits.put(factions.get(factionCredits[0].trim()), Integer.parseInt(factionCredits[1].trim()));
        }

        Map<MFaction, MPlanet> homeLocations = new HashMap<>();
        if (campaign.getHome_Location() != null) {
            List<String> home_location = campaign.getHome_Location();
            for (String s : home_location) {
                String[] factionPlanet = s.trim().split(",");
                homeLocations.put(factions.get(factionPlanet[0].trim()), planets.get(factionPlanet[1].trim()));
            }
        }

        Map<MFaction, String> aiPlayerControl = new HashMap<>();
        if (campaign.getAI_Player_Control() != null) {
            List<String> ai_player_control = campaign.getAI_Player_Control();
            for (String s : ai_player_control) {
                String[] factionAI = s.trim().split(",");
                aiPlayerControl.put(factions.get(factionAI[0].trim()), factionAI[1].trim());
            }
        }

        Map<MFaction, String> markupFiles = new HashMap<>();
        if (campaign.getMarkup_Filename() != null) {
            List<String> markup_filenames = campaign.getMarkup_Filename();
            for (String s : markup_filenames) {
                String[] factionFile = s.trim().split(",");
                markupFiles.put(factions.get(factionFile[0].trim()), factionFile[1].trim());
            }
        }

        Map<MPlanet, List<FactionUnit>> factionUnitMap = new HashMap<>();
        if (campaign.getStarting_Forces() != null) {
            List<String> starting_forces = campaign.getStarting_Forces();
            for (String s : starting_forces) {
                String[] factionPlanetUnit = s.split(",");
                FactionUnit factionUnit = new FactionUnit(units.get(factionPlanetUnit[2].trim()), factions.get(factionPlanetUnit[0].trim()));
                MPlanet planet = planets.get(factionPlanetUnit[1].trim());

                List<FactionUnit> list = factionUnitMap.computeIfAbsent(planet, k -> new ArrayList<>());
                list.add(factionUnit);
            }
        }

        Map<MPlanet, List<FactionUnit>> specialCaseProduction = new HashMap<>();
        if (campaign.getSpecial_Case_Production() != null) {
            List<String> special_case_production = campaign.getSpecial_Case_Production();
            for (String s : special_case_production) {
                String[] factionPlanetUnit = s.split(",");
                FactionUnit factionUnit = new FactionUnit(units.get(factionPlanetUnit[2].trim()), factions.get(factionPlanetUnit[0].trim()));
                MPlanet planet = planets.get(factionPlanetUnit[1].trim());

                List<FactionUnit> list = specialCaseProduction.computeIfAbsent(planet, k -> new ArrayList<>());
                list.add(factionUnit);
            }
        }


        b.fileName(filename)
                .name(campaignName)
                .xmlName(campaign.getName())
                .isMultiplayer(isMultiplayer)
                .locations(locations)
                .cameraDistance(campaign.getCamera_Distance())
                .cameraShiftX(campaign.getCamera_Shift_X())
                .cameraShiftY(campaign.getCamera_Shift_Y())
                .maxTech(maxTechs)
                .startingTech(startingTechs)
                .showCompletedTab(showCompletedTab)
                .supportsCustomSettings(supportCustomSettings)
                .description(description)
                .singlePlayerActivePlayer(activePlayer)
                .homeLocations(homeLocations)
                .startingCredits(startingCredits)
                .campaignSet(campaign.getCampaign_Set())
                .sortOrder(campaign.getSort_Order())
                .tradeRoutes(tradeRoutes)
                .singlePlayerEmpireStory(campaign.getEmpire_Story_Name())
                .singlePlayerRebelStory(campaign.getRebel_Story_Name())
                .singlePlayerUnderworldStory(campaign.getUnderworld_Story_Name())
                .aiPlayerControl(aiPlayerControl)
                .aiVictoryConditions(aiVictoryConditions)
                .humanVictoryConditions(humanVictoryConditions)
                .markupFiles(markupFiles)
                .startingForces(factionUnitMap)
                .specialCaseProduction(specialCaseProduction);

        return b.build();
    }
}
