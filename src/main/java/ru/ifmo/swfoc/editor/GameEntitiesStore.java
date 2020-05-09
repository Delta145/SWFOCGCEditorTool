package ru.ifmo.swfoc.editor;

import ru.ifmo.swfoc.converter.CampaignConverter;
import ru.ifmo.swfoc.converter.FactionConverter;
import ru.ifmo.swfoc.converter.PlanetConverter;
import ru.ifmo.swfoc.converter.TradeRouteConverter;
import ru.ifmo.swfoc.editor.model.MCampaign;
import ru.ifmo.swfoc.editor.model.MFaction;
import ru.ifmo.swfoc.editor.model.MPlanet;
import ru.ifmo.swfoc.editor.model.MTradeRoute;
import ru.ifmo.swfoc.io.*;
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
    private List<MFaction> factions = new ArrayList<>();
    private List<MTradeRoute> tradeRoutes = new ArrayList<>();
    private List<MPlanet> planets = new ArrayList<>();

    private Map<String, MPlanet> xmlNamePlanet = new HashMap<>();
    private Map<String, MFaction> xmlNameFaction = new HashMap<>();
    private Map<String, MTradeRoute> xmlNameRoute = new HashMap<>();

    private PlanetConverter planetConverter;
    private TradeRouteConverter tradeRouteConverter;
    private FactionConverter factionConverter;
    private CampaignConverter campaignConverter;

    public GameEntitiesStore(DATLoader datLoader, XMLCampaignLoader campaignParser, XMLGameObjectLoader gameObjectParser, XMLFactionLoader factionParser, XMLTradeRouteLoader tradeRouteParser) {
        planetConverter = new PlanetConverter(datLoader);
        factionConverter = new FactionConverter(datLoader);

        List<Faction> fs = factionParser.readAllFactionFiles();
        for (Faction f : fs) {
            MFaction mFaction = factionConverter.toMFaction(f);
            factions.add(mFaction);
            xmlNameFaction.put(mFaction.getName(), mFaction);
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

        campaignConverter = new CampaignConverter(datLoader, xmlNamePlanet, xmlNameFaction, xmlNameRoute);
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
    public List<MCampaign> getAllCampaigns() {
        return campaigns;
    }

    @Override
    public List<MFaction> getAllFactions() {
        return factions;
    }

    @Override
    public List<MTradeRoute> getAllTradeRoutes() {
        return tradeRoutes;
    }

    @Override
    public List<MPlanet> getAllPlanets() {
        return planets;
    }

    @Override
    public List<String> getAllUnits() {
        return null;
    }

    @Override
    public void setAllCampaigns(List<MCampaign> campaigns) {
        this.campaigns = campaigns;
    }
}
