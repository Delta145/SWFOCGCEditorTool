package ru.ifmo.swfoc.converter;

import org.junit.Test;
import ru.ifmo.swfoc.editor.EditorCore;
import ru.ifmo.swfoc.editor.GameEntities;
import ru.ifmo.swfoc.editor.model.*;
import ru.ifmo.swfoc.io.Config;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MCampaignConverterTest {
    @Test
    public void MCampaignConverterTest() throws IOException {
        Config config = new Config("settings.properties");
        EditorCore editorCore = new EditorCore(config);

        editorCore.saveCampaignsToDirectory(new File("/home/gosha/Downloads"));
        GameEntities gameEntitiesStore = editorCore.getGameEntitiesStore();
        List<MCampaign> campaigns = gameEntitiesStore.getCampaigns();
        for (MCampaign campaign : campaigns) {
            if (campaign.getXmlName().equalsIgnoreCase("multiplayer_sandbox_equal_footing")) {
                Map<MPlanet, List<FactionUnit>> startingForces = campaign.getStartingForces();
                for (MPlanet planet : startingForces.keySet()) {
                    System.out.println(planet + " " + startingForces.get(planet));
                }
            }
        }
    }
}
