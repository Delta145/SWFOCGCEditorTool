package ru.ifmo.swfoc.root;

import org.junit.Test;
import ru.ifmo.swfoc.GameEntity;
import ru.ifmo.swfoc.io.Config;
import ru.ifmo.swfoc.xmltoobject.campaign.Campaign;
import ru.ifmo.swfoc.xmltoobject.campaign.CampaignWrapper;

import java.io.FileNotFoundException;

public class ConfigTest {
    @Test
    public void loadPropertiesTest() throws FileNotFoundException {
        Config config = new Config("/home/gosha/IdeaProjects/SWFOCModddingUltimateTool/settings.properties");
        GameEntity gameEntity = new GameEntity(config);

        for (CampaignWrapper wrapper : gameEntity.getCampaigns()) {
            System.out.println(wrapper.getFileName());
            for (Campaign campaign : wrapper.getCampaigns()) {
                System.out.printf("\t%s\n", campaign.getName());
                System.out.printf("\t\t%s\n", campaign.getText_ID());
                System.out.printf("\t\t%s\n", campaign.getDescription_Text());
            }
        }

    }
}
