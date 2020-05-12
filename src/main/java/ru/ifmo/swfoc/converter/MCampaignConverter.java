package ru.ifmo.swfoc.converter;

import ru.ifmo.swfoc.editor.model.*;
import ru.ifmo.swfoc.xmltoobject.campaign.Campaign;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MCampaignConverter {
    public Campaign toCampaign(MCampaign mCampaign) {
        Campaign.CampaignBuilder b = Campaign.builder();

        String is_Multiplayer = null;
        if (mCampaign.getIsMultiplayer() != null && mCampaign.getIsMultiplayer())
            is_Multiplayer = "Yes";

        List<String> planetNames = mCampaign.getLocations().stream().map(MPlanet::getXmlName).collect(Collectors.toList());
        String locations = convertFromListToStr(planetNames);

        List<String> startingForces = null;
        if (mCampaign.getStartingForces() != null) {
            startingForces = new ArrayList<>();
            Set<MPlanet> mPlanets = mCampaign.getStartingForces().keySet();
            for (MPlanet mPlanet : mPlanets) {
                List<FactionUnit> factionUnits = mCampaign.getStartingForces().get(mPlanet);
                for (FactionUnit factionUnit : factionUnits) {
                    startingForces.add(factionUnit.getOwner().getXmlName() + ", " + mPlanet.getXmlName() + ", " + factionUnit.getUnit().getXmlName());
                }
            }
            Collections.sort(startingForces);
        }

        List<String> homeLocations = null;
        if (mCampaign.getHomeLocations() != null) {
            homeLocations = new ArrayList<>();
            Set<MFaction> mFactions = mCampaign.getHomeLocations().keySet();
            for (MFaction mFaction : mFactions) {
                homeLocations.add(mFaction.getXmlName() + ", " + mCampaign.getHomeLocations().get(mFaction).getXmlName());
            }
        }

        List<String> aiPlayerControl = null;
        Map<MFaction, String> aiPlayerControlMap = mCampaign.getAiPlayerControl();
        if (aiPlayerControlMap != null) {
            aiPlayerControl = new ArrayList<>();
            Set<MFaction> mFactions = aiPlayerControlMap.keySet();
            for (MFaction mFaction : mFactions) {
                aiPlayerControl.add(mFaction.getXmlName() + ", " + aiPlayerControlMap.get(mFaction));
            }
        }

        List<String> aiVictoryConditionsList = mCampaign.getAiVictoryConditions();
        String aiVictoryConditions = convertFromListToStr(aiVictoryConditionsList);

        List<String> humanVictoryConditionsList = mCampaign.getHumanVictoryConditions();
        String humanVictoryConditions = convertFromListToStr(humanVictoryConditionsList);

        String startingActivePlayer = null;
        if (mCampaign.getSinglePlayerActivePlayer() != null)
            startingActivePlayer = mCampaign.getSinglePlayerActivePlayer().getXmlName();

        String showCompleteTab = null;
        Boolean showCompletedTabBoolean = mCampaign.getShowCompletedTab();
        if (showCompletedTabBoolean != null) {
            if (showCompletedTabBoolean)
                showCompleteTab = "True";
            else {
                showCompleteTab = "False";
            }
        }

        String supportCustomSettings = null;
        Boolean supportCustomSettingsBoolean = mCampaign.getSupportsCustomSettings();
        if (supportCustomSettingsBoolean != null) {
            if (supportCustomSettingsBoolean)
                supportCustomSettings = "True";
            else {
                supportCustomSettings = "False";
            }
        }

        List<String> markupFiles = null;
        Map<MFaction, String> markupFilesMap = mCampaign.getMarkupFiles();
        if (markupFilesMap != null) {
            markupFiles = new ArrayList<>();
            Set<MFaction> mFactions = markupFilesMap.keySet();
            for (MFaction mFaction : mFactions) {
                markupFiles.add(mFaction.getXmlName() + ", " + markupFilesMap.get(mFaction));
            }
        }

        List<String> startingTech = null;
        Map<MFaction, Integer> startingTechMap = mCampaign.getStartingTech();
        if (startingTechMap != null) {
            startingTech = new ArrayList<>();
            Set<MFaction> mFactions = startingTechMap.keySet();
            for (MFaction mFaction : mFactions) {
                startingTech.add(mFaction.getXmlName() + ", " + startingTechMap.get(mFaction));
            }
        }

        List<String> maxTech = null;
        Map<MFaction, Integer> maxTechMap = mCampaign.getMaxTech();
        if (maxTechMap != null) {
            maxTech = new ArrayList<>();
            Set<MFaction> mFactions = maxTechMap.keySet();
            for (MFaction mFaction : mFactions) {
                maxTech.add(mFaction.getXmlName() + ", " + maxTechMap.get(mFaction));
            }
        }

        List<String> startCredits = null;
        Map<MFaction, Integer> maxCreditsMap = mCampaign.getStartingCredits();
        if (maxCreditsMap != null) {
            startCredits = new ArrayList<>();
            Set<MFaction> mFactions = maxCreditsMap.keySet();
            for (MFaction mFaction : mFactions) {
                startCredits.add(mFaction.getXmlName() + ", " + maxCreditsMap.get(mFaction));
            }
        }


        List<String> tradeRouteNames = mCampaign.getTradeRoutes().stream()
                .map(MTradeRoute::getName)
                .collect(Collectors.toList());
        String tradeRoutes = convertFromListToStr(tradeRouteNames);

        List<String> specialCaseProduction = null;
        Map<MPlanet, List<FactionUnit>> specialCaseProductionMap = mCampaign.getSpecialCaseProduction();
        if (specialCaseProductionMap != null) {
            specialCaseProduction = new ArrayList<>();
            Set<MPlanet> mPlanets = specialCaseProductionMap.keySet();
            for (MPlanet mPlanet : mPlanets) {
                List<FactionUnit> factionUnits = specialCaseProductionMap.get(mPlanet);
                for (FactionUnit factionUnit : factionUnits) {
                    specialCaseProduction.add(factionUnit.getOwner().getXmlName() + ", "
                            + mPlanet.getXmlName() + ", " + factionUnit.getUnit().getXmlName());
                }
            }
        }


        b.Name(mCampaign.getXmlName())
                .Text_ID(mCampaign.getTextId())
                .Description_Text(mCampaign.getDescriptionId())
                .Camera_Distance(mCampaign.getCameraDistance())
                .Camera_Shift_X(mCampaign.getCameraShiftX())
                .Camera_Shift_Y(mCampaign.getCameraShiftY())
                .Is_Multiplayer(is_Multiplayer)
                .Campaign_Set(mCampaign.getCampaignSet())
                .Empire_Story_Name(mCampaign.getSinglePlayerEmpireStory())
                .Rebel_Story_Name(mCampaign.getSinglePlayerEmpireStory())
                .Underworld_Story_Name(mCampaign.getSinglePlayerUnderworldStory())
                .Locations(locations)
                .Sort_Order(mCampaign.getSortOrder())
                .Starting_Forces(startingForces)
                .Home_Location(homeLocations)
                .AI_Player_Control(aiPlayerControl)
                .AI_Victory_Conditions(aiVictoryConditions)
                .Human_Victory_Conditions(humanVictoryConditions)
                .Starting_Active_Player(startingActivePlayer)
                .Show_Completed_Tab(showCompleteTab)
                .Supports_Custom_Settings(supportCustomSettings)
                .Markup_Filename(markupFiles)
                .Starting_Tech_Level(startingTech)
                .Max_Tech_Level(maxTech)
                .Starting_Credits(startCredits)
                .Trade_Routes(tradeRoutes)
                .Special_Case_Production(specialCaseProduction);

        return b.build();
    }

    private String convertFromListToStr(List<String> aiVictoryConditionsList) {
        String str = null;
        if (aiVictoryConditionsList != null) {
            StringBuilder builder = new StringBuilder();
            String prefix = "";
            for (String s : aiVictoryConditionsList) {
                builder.append(prefix);
                prefix = ", ";
                builder.append(s);
            }
            str = builder.toString();
        }
        return str;
    }
}
