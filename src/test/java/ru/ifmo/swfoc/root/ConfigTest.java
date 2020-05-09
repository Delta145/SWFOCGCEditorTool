package ru.ifmo.swfoc.root;

import org.junit.Test;
import ru.ifmo.swfoc.editor.EditorCore;
import ru.ifmo.swfoc.io.Config;
import ru.ifmo.swfoc.xmltoobject.campaign.Campaign;
import ru.ifmo.swfoc.xmltoobject.campaign.CampaignWrapper;
import ru.ifmo.swfoc.xmltoobject.faction.Faction;
import ru.ifmo.swfoc.xmltoobject.planet.Planet;
import ru.ifmo.swfoc.xmltoobject.traderoute.TradeRoute;

import java.io.IOException;

public class ConfigTest {
    @Test
    public void loadPropertiesTest() throws IOException {
        Config config = new Config("settings.properties");
        EditorCore editorCore = new EditorCore(config);

        for (CampaignWrapper wrapper : editorCore.getCampaigns()) {
            System.out.println(wrapper.getFileName());
            for (Campaign campaign : wrapper.getCampaigns()) {
                System.out.printf("\t%s\n", campaign.getName());
                System.out.printf("\t\t%s\n", editorCore.getDatLoader().getValue(campaign.getText_ID()));
                System.out.printf("\t\t%s\n",  editorCore.getDatLoader().getValue(campaign.getDescription_Text()));
            }
        }

        System.out.println("All squadrons: ");
        for (String s : editorCore.getGameObjectParser().getSquadrons())
            System.out.printf("\t%s\n", s);
        System.out.println("All space units: ");
        for (String s : editorCore.getGameObjectParser().getSpaceUnits())
            System.out.printf("\t%s\n", s);
        System.out.println("All special structures: ");
        for (String s : editorCore.getGameObjectParser().getGroundCompanies())
            System.out.printf("\t%s\n", s);
        System.out.println("All star bases: ");
        for (String s : editorCore.getGameObjectParser().getStarBases())
            System.out.printf("\t%s\n", s);

        System.out.println("All factions: ");
        for (Faction f : editorCore.getFactions())
            System.out.printf("\t%s\n", f.getName());

        System.out.println("All planets: ");
        for (Planet p : editorCore.getGameObjectParser().getPlanets())
            System.out.printf("\t%s\n", p.getName());

        System.out.println("All trade routes: ");
        for (TradeRoute tr : editorCore.getTradeRoutes())
            System.out.printf("\t%s\n", tr.getName());

        System.out.println("All hero companies: ");
        for (String s : editorCore.getGameObjectParser().getHeroCompanies())
            System.out.printf("\t%s\n", s);

    }
}
