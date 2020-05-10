package ru.ifmo.swfoc.converter;

import ru.ifmo.swfoc.editor.model.MFaction;
import ru.ifmo.swfoc.editor.model.MUnit;
import ru.ifmo.swfoc.io.DATLoader;
import ru.ifmo.swfoc.xmltoobject.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UnitConverter {
    private DATLoader datLoader;
    private Map<String, MFaction> xmlNameFaction;

    public UnitConverter(DATLoader datLoader, Map<String, MFaction> factions) {
        this.datLoader = datLoader;
        this.xmlNameFaction = factions;
    }

    public MUnit toMUnit(Unit unit) {
        String name = null;
        if (unit.getTextId() != null)
            name = datLoader.getInGameName(unit.getTextId());

        List<MFaction> factionList = new ArrayList<>();
        if (unit.getFaction() != null) {
            String[] factions = unit.getFaction().trim().split(",");
            for (String faction : factions) {
                factionList.add(xmlNameFaction.get(faction.trim()));
            }
        }

        return new MUnit(name, unit.getXmlName(), factionList);
    }
}
