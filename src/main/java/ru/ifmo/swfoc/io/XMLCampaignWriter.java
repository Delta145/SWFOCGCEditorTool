package ru.ifmo.swfoc.io;

import ru.ifmo.swfoc.xmltoobject.campaign.CampaignWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class XMLCampaignWriter {
    private File directory;
    public XMLCampaignWriter(File file) {
        directory = file;
    }

    public void writeCampaignFile(CampaignWrapper wrapper) {
        try {
            File file = new File(directory, wrapper.getFileName());
            JAXBContext jaxbContext = JAXBContext.newInstance(CampaignWrapper.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(wrapper, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
