package ru.ifmo.swfoc.io;

import ru.ifmo.swfoc.xmltoobject.campaign.CampaignWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class XMLCampaignWriter {
    private final File originalDirectory;
    private File directory;
    public XMLCampaignWriter(File file) {
        originalDirectory = file;
    }

    public void writeCampaignFile(CampaignWrapper wrapper) {
        String fileName = makeDirectories(wrapper.getFileName());
        try {
            File file = new File(directory, fileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(CampaignWrapper.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(wrapper, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    
    private String makeDirectories(String path) {
        String[] pathParts = path.split("\\\\");
        directory = originalDirectory;
        for (int i = 0; i < pathParts.length-1; i++) {
            String pathPart = pathParts[i];
            directory = new File(directory, pathPart);
            if (!directory.exists())
                directory.mkdir();
        }
        return pathParts[pathParts.length-1];
    }
}
