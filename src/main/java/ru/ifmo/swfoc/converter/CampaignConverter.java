package ru.ifmo.swfoc.converter;

import ru.ifmo.swfoc.editor.model.MCampaign;
import ru.ifmo.swfoc.editor.model.MFaction;
import ru.ifmo.swfoc.editor.model.MPlanet;
import ru.ifmo.swfoc.editor.model.MTradeRoute;
import ru.ifmo.swfoc.io.DATLoader;
import ru.ifmo.swfoc.xmltoobject.campaign.Campaign;
import ru.ifmo.swfoc.xmltoobject.faction.Faction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CampaignConverter {
    private DATLoader datLoader;
    private Map<String, MPlanet> planets;
    private Map<String, MFaction> factions;
    private Map<String, MTradeRoute> routes;

    public CampaignConverter(DATLoader datLoader) {
        this.datLoader = datLoader;
    }

    public MCampaign toMCampaign(Campaign campaign, String filename) {
        MCampaign.MCampaignBuilder b = MCampaign.builder();

        String campaignName = datLoader.getValue(campaign.getText_ID().trim());
        String description = datLoader.getValue(campaign.getDescription_Text().trim());
        MFaction activePlayer = factions.get(campaign.getStarting_Active_Player().trim());

        Boolean isMultiplayer = null;
        if (campaign.getIs_Multiplayer() != null)
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
        String[] trade_routes = campaign.getTrade_Routes().trim().split(",");
        for (String tradeRoute : trade_routes) {
            tradeRoutes.add(routes.get(tradeRoute.trim()));
        }


        Map<MFaction, Integer> maxTechs = new HashMap<>();
        List<String> max_tech_level = campaign.getMax_Tech_Level();
        for (String s : max_tech_level) {
            String[] factionTech = s.trim().split(",");
            maxTechs.put(factions.get(factionTech[0]), Integer.parseInt(factionTech[1]));
        }

        Map<MFaction, Integer> startingTechs = new HashMap<>();
        List<String> starting_tech_level = campaign.getStarting_Tech_Level();
        for (String s : starting_tech_level) {
            String[] factionTech = s.trim().split(",");
            startingTechs.put(factions.get(factionTech[0]), Integer.parseInt(factionTech[1]));
        }

        Map<MFaction, Integer> startingCredits = new HashMap<>();
        List<String> starting_credits = campaign.getStarting_Credits();
        for (String s : starting_credits) {
            String[] factionCredits = s.trim().split(",");
            startingCredits.put(factions.get(factionCredits[0]), Integer.parseInt(factionCredits[1]));
        }

        Map<MFaction, MPlanet> homeLocations = new HashMap<>();
        List<String> home_location = campaign.getHome_Location();
        for (String s : home_location) {
            String[] factionPlanet = s.trim().split(",");
            homeLocations.put(factions.get(factionPlanet[0]), planets.get(factionPlanet[1]));
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

        ;


        return b.build();
    }
}
