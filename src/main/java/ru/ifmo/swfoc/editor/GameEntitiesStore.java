package ru.ifmo.swfoc.editor;

import ru.ifmo.swfoc.converter.*;
import ru.ifmo.swfoc.editor.model.*;
import ru.ifmo.swfoc.io.*;
import ru.ifmo.swfoc.xmltoobject.Unit;
import ru.ifmo.swfoc.xmltoobject.campaign.Campaign;
import ru.ifmo.swfoc.xmltoobject.campaign.CampaignWrapper;
import ru.ifmo.swfoc.xmltoobject.faction.Faction;
import ru.ifmo.swfoc.xmltoobject.planet.Planet;
import ru.ifmo.swfoc.xmltoobject.traderoute.TradeRoute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameEntitiesStore implements GameEntities {
    private List<MCampaign> campaigns = new ArrayList<>();
    private final List<MFaction> factions = new ArrayList<>();
    private final List<MTradeRoute> tradeRoutes = new ArrayList<>();
    private final List<MPlanet> planets = new ArrayList<>();

    private final Map<String, MPlanet> xmlNamePlanet = new HashMap<>();
    private final Map<String, MUnit> xmlNameUnit = new HashMap<>();
    private final Map<String, MFaction> xmlNameFaction = new HashMap<>();
    private final Map<String, MTradeRoute> xmlNameRoute = new HashMap<>();

    private final List<MUnit> squadrons = new ArrayList<>();
    private final List<MUnit> heroCompanies = new ArrayList<>();
    private final List<MUnit> groundCompanies = new ArrayList<>();
    private final List<MUnit> starBases = new ArrayList<>();
    private final List<MUnit> spaceUnits = new ArrayList<>();
    private final List<MUnit> specialStructures = new ArrayList<>();

    private PlanetConverter planetConverter;
    private TradeRouteConverter tradeRouteConverter;
    private FactionConverter factionConverter;
    private CampaignConverter campaignConverter;
    private UnitConverter unitConverter;

    public GameEntitiesStore(DATLoader datLoader, XMLCampaignLoader campaignParser, XMLGameObjectLoader gameObjectParser, XMLFactionLoader factionParser, XMLTradeRouteLoader tradeRouteParser) {
        planetConverter = new PlanetConverter(datLoader);
        factionConverter = new FactionConverter(datLoader);

        List<Faction> fs = factionParser.readAllFactionFiles();
        for (Faction f : fs) {
            MFaction mFaction = factionConverter.toMFaction(f);
            factions.add(mFaction);
            xmlNameFaction.put(mFaction.getXmlName(), mFaction);
        }

        List<Planet> ps = gameObjectParser.getPlanets();
        for (Planet p : ps) {
            MPlanet mPlanet = planetConverter.toMPlanet(p);
            planets.add(mPlanet);
            xmlNamePlanet.put(mPlanet.getXmlName(), mPlanet);
        }

        tradeRouteConverter = new TradeRouteConverter(datLoader, xmlNamePlanet);
        List<TradeRoute> trs = tradeRouteParser.readAllTradeRouteFiles();
        for (TradeRoute t : trs) {
            MTradeRoute mTradeRoute = tradeRouteConverter.toMTradeRoute(t);
            tradeRoutes.add(mTradeRoute);
            xmlNameRoute.put(mTradeRoute.getName(), mTradeRoute);
        }

        unitConverter = new UnitConverter(datLoader, xmlNameFaction);

        for (Unit u : gameObjectParser.getGroundCompanies()) {
            MUnit unit = unitConverter.toMUnit(u);
            groundCompanies.add(unit);
            xmlNameUnit.put(unit.getXmlName(), unit);
        }
        for (Unit u : gameObjectParser.getHeroCompanies()) {
            MUnit unit = unitConverter.toMUnit(u);
            heroCompanies.add(unit);
            xmlNameUnit.put(unit.getXmlName(), unit);
        }
        for (Unit u : gameObjectParser.getStarBases()) {
            MUnit unit = unitConverter.toMUnit(u);
            starBases.add(unit);
            xmlNameUnit.put(unit.getXmlName(), unit);
        }
        for (Unit u : gameObjectParser.getSpecialStructures()) {
            MUnit unit = unitConverter.toMUnit(u);
            specialStructures.add(unit);
            xmlNameUnit.put(unit.getXmlName(), unit);
        }
        for (Unit u : gameObjectParser.getSquadrons()) {
            MUnit unit = unitConverter.toMUnit(u);
            squadrons.add(unit);
            xmlNameUnit.put(unit.getXmlName(), unit);
        }
        for (Unit u : gameObjectParser.getSpaceUnits()) {
            MUnit unit = unitConverter.toMUnit(u);
            spaceUnits.add(unit);
            xmlNameUnit.put(unit.getXmlName(), unit);
        }



        campaignConverter = new CampaignConverter(datLoader, xmlNamePlanet, xmlNameFaction, xmlNameRoute, xmlNameUnit);
        List<CampaignWrapper> campaignWrappers = campaignParser.readAllCampaignFiles();
        for (CampaignWrapper campaignWrapper : campaignWrappers) {
            String fileName = campaignWrapper.getFileName();
            List<Campaign> cmps = campaignWrapper.getCampaigns();
            for (Campaign c : cmps) {
                campaigns.add(campaignConverter.toMCampaign(c, fileName));
            }
        }

    }

    @Override
    public List<MCampaign> getCampaigns() {
        return campaigns;
    }

    @Override
    public List<MFaction> getFactions() {
        return factions;
    }

    @Override
    public List<MTradeRoute> getTradeRoutes() {
        return tradeRoutes;
    }

    @Override
    public List<MPlanet> getPlanets() {
        return planets;
    }

    @Override
    public List<MUnit> getSpaceUnits() {
        return spaceUnits;
    }

    @Override
    public List<MUnit> getGroundCompanies() {
        return groundCompanies;
    }

    @Override
    public List<MUnit> getSquadrons() {
        return squadrons;
    }

    @Override
    public List<MUnit> getSpecialStructures() {
        return specialStructures;
    }

    @Override
    public List<MUnit> getHeroCompanies() {
        return heroCompanies;
    }

    @Override
    public void setAllCampaigns(List<MCampaign> campaigns) {
        this.campaigns = campaigns;
    }
}
