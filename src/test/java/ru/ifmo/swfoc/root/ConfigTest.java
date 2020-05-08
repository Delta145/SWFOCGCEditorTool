package ru.ifmo.swfoc.root;

import org.junit.Test;
import ru.ifmo.swfoc.GameEntity;
import ru.ifmo.swfoc.campaign.Campaign;
import ru.ifmo.swfoc.campaign.CampaignWrapper;
import ru.ifmo.swfoc.io.Config;

import java.io.File;
import java.io.FileNotFoundException;

public class ConfigTest {
    @Test
    public void loadPropertiesTest() throws FileNotFoundException {
        Config config = new Config("/home/gosha/IdeaProjects/SWFOCModddingUltimateTool/settings.properties");
        GameEntity gameEntity = new GameEntity(config);

        for (CampaignWrapper wrapper : gameEntity.getCampaigns()) {
            System.out.println(wrapper.getCampaignFile());
            for (Campaign campaign : wrapper.getCampaigns()) {
                System.out.printf("\t%s\n", campaign.getName());
            }
        }

    }
}
