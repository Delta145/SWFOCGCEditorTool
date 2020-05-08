package ru.ifmo.swfoc.io;

import lombok.Data;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class XMLGameObjectParser {
    private Config config;
    private File processingFile;
    private List<String> squadrons = new ArrayList<>();
    private List<String> spaceUnits = new ArrayList<>();
    private List<String> specialStructures = new ArrayList<>();
    private List<String> groundCompanies = new ArrayList<>();

    public XMLGameObjectParser(File file, Config config) {
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

                for (Element gameObject : listGameObject) {
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
                    }
                }

            }

        } catch (IOException | JDOMException io) {
            System.out.println(io.getMessage());
        }
    }
}
