package ru.ifmo.swfoc.io;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.ifmo.swfoc.xmltoobject.faction.Faction;
import ru.ifmo.swfoc.xmltoobject.faction.FactionWrapper;

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

public class XMLFactionLoader {
    private Config config;
    private File processingFile;

    public XMLFactionLoader(File file, Config config) {
        processingFile = file;
        this.config = config;
    }
    public List<Faction> readAllFactionFiles() {
        List<Faction> factions = new ArrayList<>();


        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(processingFile);
            NodeList listFiles = doc.getElementsByTagName("File");

            for (int i = 0; i < listFiles.getLength(); i++) {
                String factionFileName = listFiles.item(i).getFirstChild().getNodeValue();
                File factionFile = config.getFileForName(factionFileName);
                JAXBContext jaxbContext;
                try {
                    jaxbContext = JAXBContext.newInstance(FactionWrapper.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    FactionWrapper factionWrapper = (FactionWrapper) jaxbUnmarshaller.unmarshal(factionFile);
                    factions.addAll(factionWrapper.getFactions());
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException | ParserConfigurationException | SAXException io) {
            System.err.println(io.getMessage());
        }

        return factions;
    }
}
