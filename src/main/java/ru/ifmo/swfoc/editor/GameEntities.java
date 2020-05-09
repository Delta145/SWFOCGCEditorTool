package ru.ifmo.swfoc.editor;

import ru.ifmo.swfoc.editor.model.MCampaign;
import ru.ifmo.swfoc.editor.model.MFaction;
import ru.ifmo.swfoc.editor.model.MPlanet;
import ru.ifmo.swfoc.editor.model.MTradeRoute;

import java.util.List;

public interface GameEntities {
    List<MCampaign> getAllCampaigns();
    List<MFaction> getAllFactions();
    List<MTradeRoute> getAllTradeRoutes();
    List<MPlanet> getAllPlanets();
    List<String> getAllUnits();
}
