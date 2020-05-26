package ru.ifmo.swfoc.io;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.ifmo.swfoc.xmltoobject.traderoute.TradeRoute;
import ru.ifmo.swfoc.xmltoobject.traderoute.TradeRouteWrapper;

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

public class XMLTradeRouteLoader {
    private Config config;
    private File processingFile;

    public XMLTradeRouteLoader(File file, Config config) {
        processingFile = file;
        this.config = config;
    }

    public List<TradeRoute> readAllTradeRouteFiles() {
        List<TradeRoute> tradeRoutes = new ArrayList<>();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(processingFile);
            NodeList listFiles = doc.getElementsByTagName("File");

            for (int i = 0; i < listFiles.getLength(); i++) {
                String tradeRouteFileName = listFiles.item(i).getFirstChild().getNodeValue();
                File tradeRouteFile = config.getFileForName(tradeRouteFileName);
                JAXBContext jaxbContext;
                try {
                    jaxbContext = JAXBContext.newInstance(TradeRouteWrapper.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    TradeRouteWrapper tradeRouteWrapper = (TradeRouteWrapper) jaxbUnmarshaller.unmarshal(tradeRouteFile);
                    tradeRoutes.addAll(tradeRouteWrapper.getTradeRoutes());
                } catch (JAXBException e) {
                    e.printStackTrace();
                }

            }

        } catch (IOException | ParserConfigurationException | SAXException io) {
            System.out.println(io.getMessage());
        }

        return tradeRoutes;
    }
}
