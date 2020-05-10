package ru.ifmo.swfoc.io;

import lombok.Data;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import ru.ifmo.swfoc.xmltoobject.Unit;
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
    private List<Unit> squadrons = new ArrayList<>();
    private List<Unit> spaceUnits = new ArrayList<>();
    private List<Unit> specialStructures = new ArrayList<>();
    private List<Unit> specialSpecialStructures = new ArrayList<>();
    private List<Unit> groundCompanies = new ArrayList<>();
    private List<Unit> starBases = new ArrayList<>();
    private List<Unit> heroCompanies = new ArrayList<>();
    private List<Unit> uniqueUnits = new ArrayList<>();
    private List<Planet> planets = new ArrayList<>();

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
                    String xmlName = gameObject.getAttributeValue("Name");
                    String factions = gameObject.getChildText("Affiliation");
                    String textId = gameObject.getChildText("Text_ID");
                    boolean isSpecial = (gameObject.getChildText("Ship_Class") != null && (gameObject.getChildText("Ship_Class").equalsIgnoreCase("fighter") || gameObject.getChildText("Ship_Class").equalsIgnoreCase("bomber"))) || gameObject.getChild("Build_Requires_Initial_Placement") != null;
                    Element variantOfExistingType = gameObject.getChild("Variant_Of_Existing_Type");

                    switch (gameObject.getName()) {
                        case "Squadron":
                            if (variantOfExistingType!= null) {
                                addUnitOfExistingType(gameObject, xmlName, factions, textId, isSpecial, squadrons);
                            } else squadrons.add(new Unit(xmlName, textId, factions, isSpecial));
                            break;
                        case "UniqueUnit":
                            if (variantOfExistingType != null) {
                                addUnitOfExistingType(gameObject, xmlName, factions, textId, isSpecial, uniqueUnits);
                            } else if (!isSpecial)
                                uniqueUnits.add(new Unit(xmlName, textId, factions, true));
                            break;
                        case "SpaceUnit":
                            if (variantOfExistingType != null) {
                                addUnitOfExistingType(gameObject, xmlName, factions, textId, isSpecial, spaceUnits);
                            } else if (!isSpecial)
                                spaceUnits.add(new Unit(xmlName, textId, factions, true));
                            break;
                        case "SpecialStructure":
                            if (variantOfExistingType!= null) {
                                addUnitOfExistingType(gameObject, xmlName, factions, textId, isSpecial, specialStructures);
                                addUnitOfExistingType(gameObject, xmlName, factions, textId, isSpecial, specialSpecialStructures);
                            } else {
                                specialStructures.add(new Unit(xmlName, textId, factions, isSpecial));
                                specialSpecialStructures.add(new Unit(xmlName, textId, factions, isSpecial));
                            }
                            break;
                        case "GroundCompany":
                            if (variantOfExistingType!= null) {
                                addUnitOfExistingType(gameObject, xmlName, factions, textId, isSpecial, groundCompanies);
                            } else groundCompanies.add(new Unit(xmlName, textId, factions, isSpecial));
                            break;
                        case "StarBase":
                            if (variantOfExistingType!= null) {
                                addUnitOfExistingType(gameObject, xmlName, factions, textId, isSpecial, starBases);
                            } else starBases.add(new Unit(xmlName, textId, factions, isSpecial));
                            break;
                        case "HeroCompany":
                            if (variantOfExistingType!= null) {
                                addUnitOfExistingType(gameObject, xmlName, factions, textId, isSpecial, heroCompanies);
                            } else heroCompanies.add(new Unit(xmlName, textId, factions, isSpecial));
                            break;
                        case "Planet":
                            addPlanet(gameObjectFile);
                            break nextFile;
                    }
                }
            }

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

    private void addUnitOfExistingType(Element gameObject, String xmlName, String factions, String textId, boolean hasSpaceEvaluator, List<Unit> spaceUnits) {
        for (Unit unit : spaceUnits) {
            if (unit.getXmlName().equalsIgnoreCase(gameObject.getChildText("Variant_Of_Existing_Type").trim())) {
                if (factions == null)
                    factions = unit.getFaction();
                if (textId == null)
                    textId = unit.getTextId();
                spaceUnits.add(new Unit(xmlName, textId, factions, hasSpaceEvaluator));
                break;
            }
        }
    }
}
