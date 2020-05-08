package ru.ifmo.swfoc.root;

import org.junit.Test;
import ru.ifmo.swfoc.GameEntity;
import ru.ifmo.swfoc.io.Config;
import ru.ifmo.swfoc.xmltoobject.campaign.Campaign;
import ru.ifmo.swfoc.xmltoobject.campaign.CampaignWrapper;
import ru.ifmo.swfoc.xmltoobject.faction.Faction;
import ru.ifmo.swfoc.xmltoobject.planet.Planet;

import java.io.FileNotFoundException;

public class ConfigTest {
    @Test
    public void loadPropertiesTest() throws FileNotFoundException {
        Config config = new Config("/home/gosha/IdeaProjects/SWFOCModddingUltimateTool/settings.properties");
        GameEntity gameEntity = new GameEntity(config);

        for (CampaignWrapper wrapper : gameEntity.getCampaigns()) {
            System.out.println(wrapper.getFileName());
            for (Campaign campaign : wrapper.getCampaigns()) {
                System.out.printf("\t%s\n", campaign.getName());
                System.out.printf("\t\t%s\n", campaign.getText_ID());
                System.out.printf("\t\t%s\n", campaign.getDescription_Text());
            }
        }

        System.out.println("All squadrons: ");
        for (String s : gameEntity.getGameObjectParser().getSquadrons())
            System.out.printf("\t%s\n", s);
        System.out.println("All space units: ");
        for (String s : gameEntity.getGameObjectParser().getSpaceUnits())
            System.out.printf("\t%s\n", s);
        System.out.println("All special structures: ");
        for (String s : gameEntity.getGameObjectParser().getGroundCompanies())
            System.out.printf("\t%s\n", s);
        System.out.println("All star bases: ");
        for (String s : gameEntity.getGameObjectParser().getStarBases())
            System.out.printf("\t%s\n", s);

        System.out.println("All factions: ");
        for (Faction f : gameEntity.getFactions())
            System.out.printf("\t%s\n", f.getName());

        System.out.println("All planets: ");
        for (Planet p : gameEntity.getGameObjectParser().getPlanets())
            System.out.printf("\t%s\n", p.getName());
    }
}
