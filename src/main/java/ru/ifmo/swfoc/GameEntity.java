package ru.ifmo.swfoc;

import ru.ifmo.swfoc.campaign.Campaign;
import ru.ifmo.swfoc.campaign.CampaignWrapper;
import ru.ifmo.swfoc.io.Config;
import ru.ifmo.swfoc.io.XMLCampaignParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GameEntity {
    private ArrayList<CampaignWrapper> campaigns;
    private XMLCampaignParser parser;
    private Config config;

    public GameEntity(Config config) throws FileNotFoundException {
        this.config = config;
        parser = new XMLCampaignParser(config.getCampaignFile(), config);
        campaigns = parser.readAllCampaignFiles();
    }

    public ArrayList<CampaignWrapper> getCampaigns() {
        return campaigns;
    }
}
