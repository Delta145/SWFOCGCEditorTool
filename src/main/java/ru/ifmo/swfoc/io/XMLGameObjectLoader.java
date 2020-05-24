package ru.ifmo.swfoc.io;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import ru.ifmo.swfoc.util.Pair;
import ru.ifmo.swfoc.xmltoobject.Unit;
import ru.ifmo.swfoc.xmltoobject.planet.Planet;
import ru.ifmo.swfoc.xmltoobject.planet.PlanetWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
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
    private List<Pair<Element, Unit>> updateAfterAll = new ArrayList<>();

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
                File gameObjectFile = config.getFileForName(gameObjectFileName);

                Document gameObjectDoc = builder.build(gameObjectFile);
                Element rootNodeGameObject = gameObjectDoc.getRootElement();
                List<Element> listGameObject = rootNodeGameObject.getChildren();

                nextFile:
                for (Element gameObject : listGameObject) {
                    if (gameObject.getAttribute("Name").getValue().contains("Death_Clone"))
                        continue;
                    String xmlName = gameObject.getAttributeValue("Name").trim().toUpperCase();
                    String factions = gameObject.getChildText("Affiliation");
                    String textId = gameObject.getChildText("Text_ID");
                    boolean isSpecial = (gameObject.getChildText("Ship_Class") != null && (gameObject.getChildText("Ship_Class").equalsIgnoreCase("fighter") || gameObject.getChildText("Ship_Class").equalsIgnoreCase("bomber")))
                            || gameObject.getChild("Build_Requires_Initial_Placement") != null;
                    Element variantOfExistingType = gameObject.getChild("Variant_Of_Existing_Type");

                    Unit unit = new Unit(xmlName, textId, factions, isSpecial);
                    if (variantOfExistingType != null)
                        updateUnitOfExistingType(variantOfExistingType, unit, true);
                    xmlNameUnit.put(xmlName, unit);
                    switch (gameObject.getName()) {
                        case "Squadron":
                            squadrons.add(unit);
                            break;
                        case "UniqueUnit":
//                            if (!isSpecial)
                                uniqueUnits.add(new Unit(xmlName, textId, factions, true));
                            break;
                        case "SpaceUnit":
//                            if (!isSpecial)
                                spaceUnits.add(new Unit(xmlName, textId, factions, true));
                            break;
                        case "SpecialStructure":
                            specialStructures.add(new Unit(xmlName, textId, factions, isSpecial));
                            break;
                        case "GroundCompany":
                            groundCompanies.add(new Unit(xmlName, textId, factions, isSpecial));
                            break;
                        case "StarBase":
                            starBases.add(new Unit(xmlName, textId, factions, isSpecial));
                            break;
                        case "HeroCompany":
                            heroCompanies.add(new Unit(xmlName, textId, factions, isSpecial));
                            break;
                        case "Planet":
                            addPlanet(gameObjectFile);
                            break nextFile;
                    }
                }
            }

            for (Pair<Element, Unit> u : updateAfterAll)
                updateUnitOfExistingType(u.getFirst(), u.getSecond(), false);

        } catch (IOException | JDOMException io) {
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

    private void updateUnitOfExistingType(Element gameObject, Unit unit, boolean allowAdd) {
        try {
            Unit parentUnit = xmlNameUnit.get(gameObject.getText().trim().toUpperCase());
            if (unit.getFaction() == null)
                unit.setFaction(parentUnit.getFaction());
            if (unit.getTextId() == null)
                unit.setTextId(parentUnit.getTextId());
        } catch (NullPointerException e) {
            if (allowAdd)
                updateAfterAll.add(new Pair<>(gameObject, unit));
            else
                logger.warn("Unable to update unit {} from parent unit {}!", unit.getXmlName(), gameObject.getText());
        }
    }
}
