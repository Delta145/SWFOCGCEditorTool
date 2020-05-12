package ru.ifmo.swfoc.io;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import ru.ifmo.swfoc.xmltoobject.campaign.CampaignWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLCampaignLoader {
    private Config config;
    private File processingFile;

    public XMLCampaignLoader(File file, Config config) {
        processingFile = file;
        this.config = config;
    }

    public List<CampaignWrapper> readAllCampaignFiles() {
        List<CampaignWrapper> campaignWrappers = new ArrayList<>();

        SAXBuilder builder = new SAXBuilder();

        try {
            Document document = builder.build(processingFile);
            Element rootNode = document.getRootElement();
            List<Element> list = rootNode.getChildren();

            for (Element node : list) {
                String campaignFileName = node.getValue();
                File campaignFile = config.findFileIgnoreCase(campaignFileName);
                JAXBContext jaxbContext;
                try {
                    jaxbContext = JAXBContext.newInstance(CampaignWrapper.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    CampaignWrapper campaigns = (CampaignWrapper) jaxbUnmarshaller.unmarshal(campaignFile);
                    campaigns.setFileName(campaignFileName);
                    campaignWrappers.add(campaigns);
                } catch (JAXBException e) {
//                    e.printStackTrace();
                    System.err.println("Error on parsing "+campaignFileName);
                }

            }

        } catch (IOException | JDOMException io) {
            System.out.println(io.getMessage());
        }

        return campaignWrappers;
    }

}
