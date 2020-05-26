package ru.ifmo.swfoc.io;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.ifmo.swfoc.xmltoobject.campaign.CampaignWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class XMLCampaignLoader {

    private static final Logger logger = LogManager.getLogger(XMLCampaignLoader.class);

    private Config config;
    private File processingFile;

    public XMLCampaignLoader(File file, Config config) {
        processingFile = file;
        this.config = config;
    }

    public List<CampaignWrapper> readAllCampaignFiles() {
        List<CampaignWrapper> campaignWrappers = new ArrayList<>();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(processingFile);
            NodeList listFiles = doc.getElementsByTagName("File");

            for (int i = 0; i < listFiles.getLength(); i++) {
                String campaignFileName = listFiles.item(i).getFirstChild().getNodeValue();
                File campaignFile = config.getFileForName(campaignFileName);
                JAXBContext jaxbContext;
                try {
                    jaxbContext = JAXBContext.newInstance(CampaignWrapper.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    CampaignWrapper campaigns = (CampaignWrapper) jaxbUnmarshaller.unmarshal(campaignFile);
                    campaigns.setFileName(campaignFileName);
                    campaignWrappers.add(campaigns);
                } catch (JAXBException e) {
                    logger.error("Error on parsing {}. You must make sure what root xml element in the file is <Campaigns>!!!", campaignFileName);
                }

            }

        } catch (IOException | SAXException | ParserConfigurationException io) {
            System.out.println(io.getMessage());
        }

        return campaignWrappers;
    }

}
