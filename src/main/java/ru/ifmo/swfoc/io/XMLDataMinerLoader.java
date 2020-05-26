package ru.ifmo.swfoc.io;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLDataMinerLoader {
    private final File processingFile;
    private final String typeAttribute = "type";
    private final String filenameAttribute = "filename";
    private final String campaignType = "Campaign";
    private final String factionType = "Faction";
    private final String tradeRouteType = "TradeRoute";
    private final String gameObjectType = "GameObjectType";

    public XMLDataMinerLoader(File file) {
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

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(processingFile);
            NodeList listFiles = doc.getElementsByTagName("File");

            for (int i = 0; i < listFiles.getLength(); i++) {
                String typeFile = listFiles.item(i).getAttributes().getNamedItem(typeAttribute).getNodeValue();
                if (typeFile.equals(type))
                    return listFiles.item(i).getAttributes().getNamedItem(filenameAttribute).getNodeValue();
            }

        } catch (IOException | ParserConfigurationException | SAXException io) {
            System.out.println(io.getMessage());
        }
        return null;
    }
}
