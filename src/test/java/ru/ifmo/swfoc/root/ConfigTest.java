package ru.ifmo.swfoc.root;

import org.junit.Test;
import ru.ifmo.swfoc.editor.EditorCore;
import ru.ifmo.swfoc.editor.GameEntities;
import ru.ifmo.swfoc.editor.model.MCampaign;
import ru.ifmo.swfoc.io.Config;

import java.io.IOException;
import java.util.List;

public class ConfigTest {
    @Test
    public void loadPropertiesTest() throws IOException {
        Config config = new Config("settings.properties");
        EditorCore editorCore = new EditorCore(config);

        GameEntities gameEntitiesStore = editorCore.getGameEntitiesStore();
        List<MCampaign> allCampaigns = gameEntitiesStore.getCampaigns();
        for (MCampaign allCampaign : allCampaigns) {
            System.out.println(allCampaign);
        }
    }
}
