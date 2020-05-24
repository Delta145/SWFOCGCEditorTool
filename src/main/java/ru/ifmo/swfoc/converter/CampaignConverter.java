package ru.ifmo.swfoc.converter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ifmo.swfoc.editor.model.*;
import ru.ifmo.swfoc.io.DATLoader;
import ru.ifmo.swfoc.xmltoobject.campaign.Campaign;

import java.util.*;

public class CampaignConverter extends Converter {

    private static final Logger logger = LogManager.getLogger(CampaignConverter.class);

    private final DATLoader datLoader;
    private final Map<String, MPlanet> planets;
    private final Map<String, MFaction> factions;
    private final Map<String, MTradeRoute> routes;
    private final Map<String, MUnit> units;

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

        String campaignName = datLoader.getInGameName(campaign.getText_ID());
        String description = datLoader.getInGameName(campaign.getDescription_Text());

        MFaction activePlayer = null;
        if (validStr(campaign.getStarting_Active_Player()))
            activePlayer = factions.get(cnv(campaign.getStarting_Active_Player()));

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
        String[] locationStrings = campaign.getLocations().trim().split(",\n|,|\n");
        for (String locationString : locationStrings) {
            if (planets.get(cnv(locationString)) == null)
                continue;
            locations.add(planets.get(cnv(locationString)));
        }

        List<MTradeRoute> tradeRoutes = new ArrayList<>();
        if (validStr(campaign.getTrade_Routes())) {
            String[] trade_routes = campaign.getTrade_Routes().trim().split(",\n|,|\n");
            for (String tradeRoute : trade_routes)
                if (tradeRoute.trim().length() > 0)
                    tradeRoutes.add(routes.get(cnv(tradeRoute)));
        }

        List<String> aiVictoryConditions = new ArrayList<>();
        if (validStr(campaign.getAI_Victory_Conditions())) {
            String[] victory_conditions = campaign.getAI_Victory_Conditions().trim().split(",");
            for (String victory_condition : victory_conditions)
                aiVictoryConditions.add(victory_condition.trim());
        }

        List<String> humanVictoryConditions = new ArrayList<>();
        if (validStr(campaign.getHuman_Victory_Conditions())) {
            String[] victory_conditions = campaign.getHuman_Victory_Conditions().trim().split(",");
            for (String victory_condition : victory_conditions)
                humanVictoryConditions.add(victory_condition.trim());
        }

        Map<MFaction, Integer> maxTechs = new HashMap<>();
        if (campaign.getMax_Tech_Level() != null) {
            List<String> max_tech_level = campaign.getMax_Tech_Level();
            for (String s : max_tech_level) {
                String[] factionTech = s.trim().split(",");
                maxTechs.put(factions.get(cnv(factionTech[0])), Integer.parseInt(factionTech[1].trim()));
            }
        }

        Map<MFaction, Integer> startingTechs = new HashMap<>();
        List<String> starting_tech_level = campaign.getStarting_Tech_Level();
        if (starting_tech_level != null)
            for (String s : starting_tech_level) {
                String[] factionTech = s.trim().split(",");
                startingTechs.put(factions.get(cnv(factionTech[0])), Integer.parseInt(factionTech[1].trim()));
            }

        Map<MFaction, Integer> startingCredits = new HashMap<>();
        List<String> starting_credits = campaign.getStarting_Credits();
        if (starting_credits != null)
            for (String s : starting_credits) {
                String[] factionCredits = s.trim().split(",");
                if (factionCredits.length > 1)
                    startingCredits.put(factions.get(cnv(factionCredits[0])), Integer.parseInt(factionCredits[1].trim()));
            }

        Map<MFaction, MPlanet> homeLocations = new HashMap<>();
        if (campaign.getHome_Location() != null) {
            List<String> home_location = campaign.getHome_Location();
            for (String s : home_location) {
                String[] factionPlanet = s.trim().split(",");
                if (factions.get(cnv(factionPlanet[0])) == null)
                    continue;
                homeLocations.put(factions.get(cnv(factionPlanet[0])), planets.get(cnv(factionPlanet[1])));
            }
        }

        Map<MFaction, String> aiPlayerControl = new HashMap<>();
        if (campaign.getAI_Player_Control() != null) {
            List<String> ai_player_control = campaign.getAI_Player_Control();
            for (String s : ai_player_control) {
                String[] factionAI = s.trim().split(",");
                aiPlayerControl.put(factions.get(cnv(factionAI[0])), factionAI[1].trim());
            }
        }

        Map<MFaction, String> markupFiles = new HashMap<>();
        if (campaign.getMarkup_Filename() != null) {
            List<String> markup_filenames = campaign.getMarkup_Filename();
            for (String s : markup_filenames) {
                String[] factionFile = s.trim().split(",");
                markupFiles.put(factions.get(cnv(factionFile[0])), factionFile[1].trim());
            }
        }

        Map<MPlanet, List<FactionUnit>> factionUnitMap = new HashMap<>();
        if (campaign.getStarting_Forces() != null) {
            List<String> starting_forces = campaign.getStarting_Forces();
            fillUnitMap(factionUnitMap, starting_forces, campaign.getName());
        }

        Map<MPlanet, List<FactionUnit>> specialCaseProduction = new HashMap<>();
        if (campaign.getSpecial_Case_Production() != null) {
            List<String> special_case_production = campaign.getSpecial_Case_Production();
            fillUnitMap(specialCaseProduction, special_case_production, campaign.getName());
        }


        b.fileName(filename)
                .name(campaignName)
                .xmlName(campaign.getName().toUpperCase())
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
                .specialCaseProduction(specialCaseProduction)
                .descriptionId(campaign.getDescription_Text())
                .textId(campaign.getText_ID());

        return b.build();
    }

    private void fillUnitMap(Map<MPlanet, List<FactionUnit>> unitMap, List<String> unitList, String campaignName) {
        for (String s : unitList) {
            String[] factionPlanetUnit = s.split(",");
            FactionUnit factionUnit = new FactionUnit(units.get(cnv(factionPlanetUnit[2])), factions.get(cnv(factionPlanetUnit[0])));
            MPlanet planet = planets.get(cnv(factionPlanetUnit[1]));

            if (factionUnit.getUnit() == null) {
                logger.warn("Unable to find unit {} for planet - {} [campaign={}]", cnv(factionPlanetUnit[2]), cnv(factionPlanetUnit[1]), campaignName);
                continue;
            }

            List<FactionUnit> list = unitMap.computeIfAbsent(planet, k -> new ArrayList<>());
            list.add(factionUnit);
        }
    }
}
