package ru.ifmo.swfoc.io;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import ru.ifmo.swfoc.campaign.Campaign;
import ru.ifmo.swfoc.campaign.CampaignWrapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLCampaignParser {
    private Config config;
    private File processingFile;

    public XMLCampaignParser(File file, Config config) {
        processingFile = file;
        this.config = config;
    }

    public ArrayList<CampaignWrapper> readAllCampaignFiles() {
        ArrayList<CampaignWrapper> campaignWrappers = new ArrayList<>();

        SAXBuilder builder = new SAXBuilder();

        try {
            Document document = builder.build(processingFile);
            Element rootNode = document.getRootElement();
            List<Element> list = rootNode.getChildren();

            for (Element node : list) {
                String campaignFileName = node.getValue();
                File campaignFile = config.findFileIgnoreCase(campaignFileName);
                ArrayList<Campaign> campaigns = readAllCampaigns(campaignFile);
                CampaignWrapper campaignWrapper = new CampaignWrapper(campaigns, campaignFileName);
                campaignWrappers.add(campaignWrapper);
            }

        } catch (IOException | JDOMException io) {
            System.out.println(io.getMessage());
        }

        return campaignWrappers;
    }

    private ArrayList<Campaign> readAllCampaigns(File file) {
        ArrayList<Campaign> campaigns = new ArrayList<>();

        SAXBuilder builder = new SAXBuilder();

        try {
            Document document = builder.build(file);
            Element rootNode = document.getRootElement();
            List<Element> list = rootNode.getChildren();

            for (Element node : list) {
                String campaignName = node.getAttribute("Name").getValue();
                Campaign campaign = new Campaign(campaignName);
                campaigns.add(campaign);
            }

        } catch (IOException | JDOMException io) {
            System.out.println(io.getMessage());
        }

        return campaigns;
    }

}
