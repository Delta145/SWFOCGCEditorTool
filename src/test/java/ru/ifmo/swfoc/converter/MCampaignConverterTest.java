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
        List<MCampaign> campaigns = gameEntitiesStore.getCampaigns();
        MCampaignConverter mCampaignConverter = new MCampaignConverter();
        Campaign campaign = mCampaignConverter.toCampaign(campaigns.get(0));
        List<String> markup_filename = campaign.getSpecial_Case_Production();
        for (String s : markup_filename) {
            System.out.println(s);
        }
//        System.out.println(campaign.getTrade_Routes());
    }
}
