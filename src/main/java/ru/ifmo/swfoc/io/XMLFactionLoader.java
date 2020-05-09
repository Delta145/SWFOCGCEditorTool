package ru.ifmo.swfoc.io;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import ru.ifmo.swfoc.xmltoobject.campaign.CampaignWrapper;
import ru.ifmo.swfoc.xmltoobject.faction.Faction;
import ru.ifmo.swfoc.xmltoobject.faction.FactionWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLFactionLoader {
    private Config config;
    private File processingFile;

    public XMLFactionLoader(File file, Config config) {
        processingFile = file;
        this.config = config;
    }
    public List<Faction> readAllFactionFiles() {
        List<Faction> factions = new ArrayList<>();

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
                    jaxbContext = JAXBContext.newInstance(FactionWrapper.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    FactionWrapper factionWrapper = (FactionWrapper) jaxbUnmarshaller.unmarshal(campaignFile);
                    factions.addAll(factionWrapper.getFactions());
                } catch (JAXBException e) {
                    e.printStackTrace();
                }

            }

        } catch (IOException | JDOMException io) {
            System.out.println(io.getMessage());
        }

        return factions;
    }
}
