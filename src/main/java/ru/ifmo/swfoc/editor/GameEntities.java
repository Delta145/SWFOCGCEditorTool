package ru.ifmo.swfoc.editor;

import ru.ifmo.swfoc.editor.model.*;

import java.util.List;

public interface GameEntities {
    List<MCampaign> getCampaigns();
    List<MFaction> getFactions();
    List<MTradeRoute> getTradeRoutes();
    List<MPlanet> getPlanets();
    List<MUnit> getSpaceUnits();
    List<MUnit> getGroundCompanies();
    List<MUnit> getSquadrons();
    List<MUnit> getSpecialStructures();
    List<MUnit> getHeroCompanies();
    List<MUnit> getStarBases();
    List<MUnit> getSpecialSpecialStructures();
    void setAllCampaigns(List<MCampaign> campaigns);
}
