package ru.ifmo.swfoc;

import lombok.Data;
import ru.ifmo.swfoc.io.*;
import ru.ifmo.swfoc.xmltoobject.campaign.CampaignWrapper;
import ru.ifmo.swfoc.xmltoobject.faction.Faction;
import ru.ifmo.swfoc.xmltoobject.traderoute.TradeRoute;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Data
public class EditorCore {
    private List<CampaignWrapper> campaigns;
    private XMLCampaignLoader campaignParser;
    private XMLGameObjectLoader gameObjectParser;
    private XMLFactionLoader factionParser;
    private XMLTradeRouteLoader tradeRouteParser;
    private Config config;
    private List<Faction> factions;
    private List<TradeRoute> tradeRoutes;
    private DATLoader datLoader;

    public EditorCore(Config config) throws IOException {
        this.config = config;
        campaignParser = new XMLCampaignLoader(config.getCampaignFile(), config);
        gameObjectParser = new XMLGameObjectLoader(config.getGameObjectFile(), config);
        factionParser = new XMLFactionLoader(config.getFactionFile(), config);
        tradeRouteParser = new XMLTradeRouteLoader(config.getTradeRouteFile(), config);
        gameObjectParser.readAllGameObjects();
        campaigns = campaignParser.readAllCampaignFiles();
        factions = factionParser.readAllFactionFiles();
        tradeRoutes = tradeRouteParser.readAllTradeRouteFiles();
        datLoader = new DATLoader(config.getMasterTextFile());
    }

    public List<CampaignWrapper> getCampaigns() {
        return campaigns;
    }
}
