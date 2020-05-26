package ru.ifmo.swfoc.io;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.ifmo.swfoc.util.Pair;
import ru.ifmo.swfoc.xmltoobject.Unit;
import ru.ifmo.swfoc.xmltoobject.planet.Planet;
import ru.ifmo.swfoc.xmltoobject.planet.PlanetWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class XMLGameObjectLoader {

    private static final Logger logger = LogManager.getLogger(XMLGameObjectLoader.class);

    private Config config;
    private File processingFile;
    private Map<String, Unit> xmlNameUnit = new HashMap<>();
    private List<Unit> squadrons = new ArrayList<>();
    private List<Unit> spaceUnits = new ArrayList<>();
    private List<Unit> specialStructures = new ArrayList<>();
    private List<Unit> groundCompanies = new ArrayList<>();
    private List<Unit> starBases = new ArrayList<>();
    private List<Unit> heroCompanies = new ArrayList<>();
    private List<Unit> uniqueUnits = new ArrayList<>();
    private List<Planet> planets = new ArrayList<>();
    private List<Pair<String, Unit>> updateAfterAll = new ArrayList<>();

    public XMLGameObjectLoader(File file, Config config) {
        processingFile = file;
        this.config = config;
    }

    public void readAllGameObjects() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(processingFile);
            NodeList listFiles = doc.getElementsByTagName("File");

            for (int i = 0; i < listFiles.getLength(); i++) {
                String gameObjectFileName = listFiles.item(i).getFirstChild().getNodeValue();
                File gameObjectFile = config.getFileForName(gameObjectFileName);

                Document gameObjectDoc = dBuilder.parse(gameObjectFile);
                Node someChild = gameObjectDoc.getFirstChild();
                while (someChild.getNodeType() == Node.COMMENT_NODE)
                    someChild = someChild.getNextSibling();
                NodeList listGameObject = someChild.getChildNodes();

                nextFile:
                for (int j = 0; j < listGameObject.getLength(); j++) {
                    Node gameObject = listGameObject.item(j);
                    if (gameObject.getAttributes() == null)
                        continue;
                    String name = gameObject.getAttributes().getNamedItem("Name").getNodeValue();
                    if (name.contains("Death_Clone"))
                        continue;
                    String xmlName = gameObject.getAttributes().getNamedItem("Name").getNodeValue().trim().toUpperCase();
                    String factions = getChildText(gameObject, "Affiliation");
                    String textId = getChildText(gameObject, "Text_ID");

                    String shipClass = getChildText(gameObject, "Ship_Class");
                    boolean isSpecial = (shipClass != null && (shipClass.equalsIgnoreCase("fighter") || shipClass.equalsIgnoreCase("bomber")))
                            || getChildText(gameObject, "Build_Requires_Initial_Placement") != null;
                    String variantOfExistingType = getChildText(gameObject, "Variant_Of_Existing_Type");

                    Unit unit = new Unit(xmlName, textId, factions, isSpecial);
                    if (variantOfExistingType != null)
                        updateUnitOfExistingType(variantOfExistingType, unit, true);
                    xmlNameUnit.put(xmlName, unit);
                    switch (gameObject.getNodeName()) {
                        case "Squadron":
                            squadrons.add(unit);
                            break;
                        case "UniqueUnit":
//                            if (!isSpecial)
                            uniqueUnits.add(unit);
                            break;
                        case "SpaceUnit":
//                            if (!isSpecial)
                            spaceUnits.add(unit);
                            break;
                        case "SpecialStructure":
                            specialStructures.add(unit);
                            break;
                        case "GroundCompany":
                            groundCompanies.add(unit);
                            break;
                        case "StarBase":
                            starBases.add(unit);
                            break;
                        case "HeroCompany":
                            heroCompanies.add(unit);
                            break;
                        case "Planet":
                            addPlanet(gameObjectFile);
                            break nextFile;
                    }
                }
            }

            for (Pair<String, Unit> u : updateAfterAll)
                updateUnitOfExistingType(u.getFirst(), u.getSecond(), false);
        } catch (IOException | ParserConfigurationException | SAXException io) {
            System.out.println(io.getMessage());
        }
    }

    private void addPlanet(File gameObjectFile) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(PlanetWrapper.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            PlanetWrapper planetWrapper = (PlanetWrapper) jaxbUnmarshaller.unmarshal(gameObjectFile);
            planets.addAll(planetWrapper.getPlanets());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private void updateUnitOfExistingType(String gameObject, Unit unit, boolean allowAdd) {
        String parentUnitName = gameObject.trim().toUpperCase();
        try {
            Unit parentUnit = xmlNameUnit.get(parentUnitName);
            if (unit.getFaction() == null)
                unit.setFaction(parentUnit.getFaction());
            if (unit.getTextId() == null)
                unit.setTextId(parentUnit.getTextId());
        } catch (NullPointerException e) {
            if (allowAdd)
                updateAfterAll.add(new Pair<>(gameObject, unit));
            else
                logger.warn("Unable to update unit {} from parent unit {}", unit.getXmlName(), parentUnitName);
        }
    }

    private String getChildText(Node node, String childName) {
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i).getNodeName().equalsIgnoreCase(childName) && childNodes.item(i).getFirstChild() != null)
                return childNodes.item(i).getFirstChild().getNodeValue();
        }
        return null;
    }
}
