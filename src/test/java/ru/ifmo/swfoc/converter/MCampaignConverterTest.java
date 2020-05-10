package ru.ifmo.swfoc.converter;

import org.junit.Test;
import ru.ifmo.swfoc.editor.EditorCore;
import ru.ifmo.swfoc.editor.GameEntities;
import ru.ifmo.swfoc.editor.model.MCampaign;
import ru.ifmo.swfoc.editor.model.MUnit;
import ru.ifmo.swfoc.io.Config;
import ru.ifmo.swfoc.xmltoobject.campaign.Campaign;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class MCampaignConverterTest {
    @Test
    public void MCampaignConverterTest() throws IOException {
        Config config = new Config("settings.properties");
        EditorCore editorCore = new EditorCore(config);

        GameEntities gameEntitiesStore = editorCore.getGameEntitiesStore();
        List<MUnit> spaceUnits = gameEntitiesStore.getUniqueUnits();
        for (MUnit spaceUnit : spaceUnits) {
            System.out.println(spaceUnit);
        }
    }
}
