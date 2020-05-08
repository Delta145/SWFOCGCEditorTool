package ru.ifmo.swfoc.io;

import java.io.*;
import java.util.Properties;

public class Config {
    private String propertiesPath;
    private File dataMinerFile;
    private XMLDataMinerParser xmlDataMinerParser;
    private String xmlDirectory;

    public Config(String path) {
        propertiesPath = path;
        loadProperties();
        xmlDataMinerParser = new XMLDataMinerParser(dataMinerFile);
    }

    private void loadProperties() {
        try (InputStream input = new FileInputStream(propertiesPath)) {
            Properties prop = new Properties();

            prop.load(input);

            xmlDirectory = prop.getProperty("swfoc.xml");
            String dataMinerFileName = prop.getProperty("swfoc.dataminerfiles");

            File directory = new File(xmlDirectory);
            File[] contents = directory.listFiles();
            for (File f : contents) {
                if (f.getName().equalsIgnoreCase(dataMinerFileName))
                    dataMinerFile = f;
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    File findFileIgnoreCase(String filename) throws FileNotFoundException {
        File directory = new File(xmlDirectory);
        File[] contents = directory.listFiles();
        for (File f : contents) {
            if (f.getName().equalsIgnoreCase(filename))
                return f;
        }
        throw new FileNotFoundException(filename + " was not found.");
    }

    public File getCampaignFile() throws FileNotFoundException {
        return findFileIgnoreCase(xmlDataMinerParser.getCampaignFile());
    }

    public File getGameObjectFile() throws FileNotFoundException {
        return findFileIgnoreCase(xmlDataMinerParser.getGameObjectFile());
    }

    public File getFactionFile() throws FileNotFoundException {
        return findFileIgnoreCase(xmlDataMinerParser.getFactionFile());
    }
}
