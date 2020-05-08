package ru.ifmo.swfoc.io;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XMLDataMinerParser {
    private final File processingFile;
    private final String typeAttribute = "type";
    private final String filenameAttribute = "filename";
    private final String campaignType = "Campaign";
    private final String factionType = "Faction";
    private final String tradeRouteType = "TradeRoute";
    private final String gameObjectType = "GameObjectType";

    public XMLDataMinerParser(File file) {
        processingFile = file;
    }

    String getCampaignFile() {
        return getFilenameForType(campaignType);
    }

    String getTradeRouteFile() {
        return getFilenameForType(tradeRouteType);
    }

    String getGameObjectFile() {
        return getFilenameForType(gameObjectType);
    }

    String getFactionFile() {
        return getFilenameForType(factionType);
    }

    private String getFilenameForType(String type) {
        SAXBuilder builder = new SAXBuilder();

        try {
            Document document = builder.build(processingFile);
            Element rootNode = document.getRootElement();
            List<Element> list = rootNode.getChildren();

            for (Element node : list)
                if (node.getAttribute(typeAttribute).getValue().equals(type))
                    return node.getAttribute(filenameAttribute).getValue();

        } catch (IOException | JDOMException io) {
            System.out.println(io.getMessage());
        }
        return null;
    }
}
