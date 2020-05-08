package ru.ifmo.swfoc.root;

import org.junit.Test;
import ru.ifmo.swfoc.io.Config;

import java.io.File;
import java.io.FileNotFoundException;

public class ConfigTest {
    @Test
    public void loadPropertiesTest() throws FileNotFoundException {
        Config config = new Config("/home/gosha/IdeaProjects/SWFOCModddingUltimateTool/settings.properties");
        File campaignFile = config.getCampaignFile();
        System.out.println();

    }
}
