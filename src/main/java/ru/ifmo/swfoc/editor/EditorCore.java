package ru.ifmo.swfoc.editor;

import lombok.Data;
import ru.ifmo.swfoc.io.*;
import ru.ifmo.swfoc.xmltoobject.campaign.CampaignWrapper;
import ru.ifmo.swfoc.xmltoobject.faction.Faction;
import ru.ifmo.swfoc.xmltoobject.traderoute.TradeRoute;

import java.io.IOException;
import java.util.List;

@Data
public class EditorCore {
    private Config config;
    private DATLoader datLoader;
    private GameEntities gameEntitiesStore;
    private XMLCampaignLoader campaignParser;
    private XMLGameObjectLoader gameObjectParser;
    private XMLFactionLoader factionParser;
    private XMLTradeRouteLoader tradeRouteParser;

    public EditorCore(Config config) throws IOException {
        this.config = config;
        datLoader = new DATLoader(config.getMasterTextFile());
        campaignParser = new XMLCampaignLoader(config.getCampaignFile(), config);
        gameObjectParser = new XMLGameObjectLoader(config.getGameObjectFile(), config);
        factionParser = new XMLFactionLoader(config.getFactionFile(), config);
        tradeRouteParser = new XMLTradeRouteLoader(config.getTradeRouteFile(), config);
        gameObjectParser.readAllGameObjects();
        gameEntitiesStore = new GameEntitiesStore(datLoader, campaignParser, gameObjectParser, factionParser, tradeRouteParser);
    }
}
