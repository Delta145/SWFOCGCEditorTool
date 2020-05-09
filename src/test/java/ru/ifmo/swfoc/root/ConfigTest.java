package ru.ifmo.swfoc.root;

import org.junit.Test;
import ru.ifmo.swfoc.editor.EditorCore;
import ru.ifmo.swfoc.editor.GameEntities;
import ru.ifmo.swfoc.io.Config;

import java.io.IOException;

public class ConfigTest {
    @Test
    public void loadPropertiesTest() throws IOException {
        Config config = new Config("settings.properties");
        EditorCore editorCore = new EditorCore(config);

        GameEntities gameEntitiesStore = editorCore.getGameEntitiesStore();
        System.out.println();
    }
}
