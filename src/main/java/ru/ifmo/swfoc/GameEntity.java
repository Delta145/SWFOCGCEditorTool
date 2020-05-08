package ru.ifmo.swfoc;

import lombok.Data;
import ru.ifmo.swfoc.io.Config;
import ru.ifmo.swfoc.io.XMLCampaignParser;
import ru.ifmo.swfoc.io.XMLFactionParser;
import ru.ifmo.swfoc.io.XMLGameObjectParser;
import ru.ifmo.swfoc.xmltoobject.campaign.CampaignWrapper;
import ru.ifmo.swfoc.xmltoobject.faction.Faction;

import java.io.FileNotFoundException;
import java.util.List;

@Data
public class GameEntity {
    private List<CampaignWrapper> campaigns;
    private XMLCampaignParser campaignParser;
    private XMLGameObjectParser gameObjectParser;
    private XMLFactionParser factionParser;
    private Config config;
    private List<Faction> factions;

    public GameEntity(Config config) throws FileNotFoundException {
        this.config = config;
        campaignParser = new XMLCampaignParser(config.getCampaignFile(), config);
        gameObjectParser = new XMLGameObjectParser(config.getGameObjectFile(), config);
        factionParser = new XMLFactionParser(config.getFactionFile(), config);
        campaigns = campaignParser.readAllCampaignFiles();
        gameObjectParser.readAllGameObjects();
        factions = factionParser.readAllFactionFiles();
    }

    public List<CampaignWrapper> getCampaigns() {
        return campaigns;
    }
}
