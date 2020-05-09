package ru.ifmo.swfoc.root;

import org.junit.Test;
import ru.ifmo.swfoc.converter.PlanetConverter;
import ru.ifmo.swfoc.editor.EditorCore;
import ru.ifmo.swfoc.editor.model.MPlanet;
import ru.ifmo.swfoc.io.Config;
import ru.ifmo.swfoc.io.DATLoader;

import java.io.IOException;

public class ConverterTest {
    @Test
    public void convertPlanet() throws IOException {
        Config config = new Config("settings.properties");
        EditorCore editorCore = new EditorCore(config);
        DATLoader datLoader = editorCore.getDatLoader();
        PlanetConverter converter = new PlanetConverter(datLoader);
        MPlanet mPlanet = converter.toMPlanet(editorCore.getGameObjectParser().getPlanets().get(27));
        System.out.println(mPlanet);
    }
}
