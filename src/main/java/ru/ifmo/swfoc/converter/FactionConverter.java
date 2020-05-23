package ru.ifmo.swfoc.converter;

import ru.ifmo.swfoc.editor.model.MFaction;
import ru.ifmo.swfoc.io.DATLoader;
import ru.ifmo.swfoc.xmltoobject.faction.Faction;

public class FactionConverter {
    private final DATLoader datLoader;

    public FactionConverter(DATLoader datLoader) {
        this.datLoader = datLoader;
    }

    public MFaction toMFaction(Faction faction) {
        String xmlName = faction.getName().toUpperCase();
        String name = datLoader.getInGameName(faction.getText_ID().trim());
        return new MFaction(name, xmlName);
    }
}
