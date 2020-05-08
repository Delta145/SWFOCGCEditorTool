package ru.ifmo.swfoc.io;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import ru.ifmo.swfoc.xmltoobject.campaign.CampaignWrapper;
import ru.ifmo.swfoc.xmltoobject.traderoute.TradeRoute;
import ru.ifmo.swfoc.xmltoobject.traderoute.TradeRouteWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLTradeRouteParser {
    private Config config;
    private File processingFile;

    public XMLTradeRouteParser(File file, Config config) {
        processingFile = file;
        this.config = config;
    }

    public List<TradeRoute> readAllTradeRouteFiles() {
        List<TradeRoute> tradeRoutes = new ArrayList<>();

        SAXBuilder builder = new SAXBuilder();

        try {
            Document document = builder.build(processingFile);
            Element rootNode = document.getRootElement();
            List<Element> list = rootNode.getChildren();

            for (Element node : list) {
                String tradeRouteFileName = node.getValue();
                File tradeRouteFile = config.findFileIgnoreCase(tradeRouteFileName);
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

        } catch (IOException | JDOMException io) {
            System.out.println(io.getMessage());
        }

        return tradeRoutes;
    }
}
