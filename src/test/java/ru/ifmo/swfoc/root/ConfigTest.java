package ru.ifmo.swfoc.root;

import org.junit.Test;
import ru.ifmo.swfoc.editor.EditorCore;
import ru.ifmo.swfoc.editor.GameEntities;
import ru.ifmo.swfoc.editor.model.MPlanet;
import ru.ifmo.swfoc.editor.model.MUnit;
import ru.ifmo.swfoc.io.Config;

import java.io.IOException;
import java.util.List;

public class ConfigTest {
    @Test
    public void loadPropertiesTest() throws IOException {
        Config config = new Config("settings.properties");
        EditorCore editorCore = new EditorCore(config);

        GameEntities gameEntitiesStore = editorCore.getGameEntitiesStore();
        List<MUnit> spaceUnits = gameEntitiesStore.getSpecialSpecialStructures();
        for (MUnit spaceUnit : spaceUnits) {
            System.out.println(spaceUnit);
        }
    }
}
