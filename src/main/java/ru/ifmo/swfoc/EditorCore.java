package ru.ifmo.swfoc;

import lombok.Data;
import ru.ifmo.swfoc.io.*;
import ru.ifmo.swfoc.xmltoobject.campaign.CampaignWrapper;
import ru.ifmo.swfoc.xmltoobject.faction.Faction;
import ru.ifmo.swfoc.xmltoobject.traderoute.TradeRoute;

import java.io.FileNotFoundException;
import java.util.List;

@Data
public class EditorCore {
    private List<CampaignWrapper> campaigns;
    private XMLCampaignParser campaignParser;
    private XMLGameObjectParser gameObjectParser;
    private XMLFactionParser factionParser;
    private XMLTradeRouteParser tradeRouteParser;
    private Config config;
    private List<Faction> factions;
    private List<TradeRoute> tradeRoutes;

    public EditorCore(Config config) throws FileNotFoundException {
        this.config = config;
        campaignParser = new XMLCampaignParser(config.getCampaignFile(), config);
        gameObjectParser = new XMLGameObjectParser(config.getGameObjectFile(), config);
        factionParser = new XMLFactionParser(config.getFactionFile(), config);
        tradeRouteParser = new XMLTradeRouteParser(config.getTradeRouteFile(), config);
        gameObjectParser.readAllGameObjects();
        campaigns = campaignParser.readAllCampaignFiles();
        factions = factionParser.readAllFactionFiles();
        tradeRoutes = tradeRouteParser.readAllTradeRouteFiles();

    }

    public List<CampaignWrapper> getCampaigns() {
        return campaigns;
    }
}
