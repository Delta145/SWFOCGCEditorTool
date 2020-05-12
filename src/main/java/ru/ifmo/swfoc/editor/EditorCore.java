package ru.ifmo.swfoc.editor;

import lombok.Data;
import ru.ifmo.swfoc.converter.MCampaignConverter;
import ru.ifmo.swfoc.editor.model.MCampaign;
import ru.ifmo.swfoc.io.*;
import ru.ifmo.swfoc.xmltoobject.campaign.Campaign;
import ru.ifmo.swfoc.xmltoobject.campaign.CampaignWrapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Data
public class EditorCore {
    private Config config;
    private DATLoader datLoader;
    private GameEntities gameEntitiesStore;
    private XMLCampaignLoader campaignParser;
    private XMLGameObjectLoader gameObjectParser;
    private XMLFactionLoader factionParser;
    private XMLTradeRouteLoader tradeRouteParser;
    private MCampaignConverter mCampaignConverter = new MCampaignConverter();

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

    public void saveCampaignsToDirectory(File file) throws IOException {
        if (!file.isDirectory())
            throw new IOException("Not a directory");

        Map<String, CampaignWrapper> campaignFiles = new HashMap<>();
        List<MCampaign> campaigns = gameEntitiesStore.getCampaigns();
        for (MCampaign campaign : campaigns) {
            String fileName = campaign.getFileName();
            campaignFiles.computeIfAbsent(fileName, (str) -> new CampaignWrapper(fileName, new ArrayList<>()));
            CampaignWrapper campaignWrapper = campaignFiles.get(fileName);
            List<Campaign> campaignsList = campaignWrapper.getCampaigns();
            campaignsList.add(mCampaignConverter.toCampaign(campaign));
        }

        Collection<CampaignWrapper> values = campaignFiles.values();
        for (CampaignWrapper value : values) {
            XMLCampaignWriter xmlCampaignWriter = new XMLCampaignWriter(file);
            xmlCampaignWriter.writeCampaignFile(value);
        }
    }

}
