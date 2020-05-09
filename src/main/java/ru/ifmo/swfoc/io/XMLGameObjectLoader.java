package ru.ifmo.swfoc.io;

import lombok.Data;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import ru.ifmo.swfoc.xmltoobject.campaign.CampaignWrapper;
import ru.ifmo.swfoc.xmltoobject.faction.FactionWrapper;
import ru.ifmo.swfoc.xmltoobject.planet.Planet;
import ru.ifmo.swfoc.xmltoobject.planet.PlanetWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class XMLGameObjectLoader {
    private Config config;
    private File processingFile;
    private List<String> squadrons = new ArrayList<>();
    private List<String> spaceUnits = new ArrayList<>();
    private List<String> specialStructures = new ArrayList<>();
    private List<String> groundCompanies = new ArrayList<>();
    private List<String> starBases = new ArrayList<>();
    private List<Planet> planets = new ArrayList<>();
    private List<String> heroCompanies = new ArrayList<>();

    public XMLGameObjectLoader(File file, Config config) {
        processingFile = file;
        this.config = config;
    }

    public void readAllGameObjects() {
        SAXBuilder builder = new SAXBuilder();

        try {
            Document document = builder.build(processingFile);
            Element rootNode = document.getRootElement();
            List<Element> list = rootNode.getChildren();

            for (Element node : list) {
                String gameObjectFileName = node.getValue();
                File gameObjectFile = config.findFileIgnoreCase(gameObjectFileName);

                Document gameObjectDoc = builder.build(gameObjectFile);
                Element rootNodeGameObject = gameObjectDoc.getRootElement();
                List<Element> listGameObject = rootNodeGameObject.getChildren();

                nextFile:
                for (Element gameObject : listGameObject) {
                    if (gameObject.getAttribute("Name").getValue().contains("Death_Clone"))
                        continue;
                    switch (gameObject.getName()) {
                        case "Squadron":
                            squadrons.add(gameObject.getAttribute("Name").getValue());
                            break;
                        case "SpaceUnit":
                            spaceUnits.add(gameObject.getAttribute("Name").getValue());
                            break;
                        case "SpecialStructure":
                            specialStructures.add(gameObject.getAttribute("Name").getValue());
                            break;
                        case "GroundCompany":
                            groundCompanies.add(gameObject.getAttribute("Name").getValue());
                            break;
                        case "StarBase":
                            starBases.add(gameObject.getAttribute("Name").getValue());
                            break;
                        case "HeroCompany":
                            heroCompanies.add(gameObject.getAttribute("Name").getValue());
                            break;
                        case "Planet":
                            JAXBContext jaxbContext;
                            try {
                                jaxbContext = JAXBContext.newInstance(PlanetWrapper.class);
                                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                                PlanetWrapper planetWrapper = (PlanetWrapper) jaxbUnmarshaller.unmarshal(gameObjectFile);
                                planets.addAll(planetWrapper.getPlanets());
                            } catch (JAXBException e) {
                                e.printStackTrace();
                            }
                            break nextFile;
                    }
                }

            }

        } catch (IOException | JDOMException io) {
            System.out.println(io.getMessage());
        }
    }
}
