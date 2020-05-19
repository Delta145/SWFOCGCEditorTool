package ru.ifmo.swfoc.io;


import java.io.*;
import java.util.Properties;
import java.util.logging.Logger;

public class Config {
    private String propertiesPath;
    private File dataMinerFile;
    private XMLDataMinerLoader xmlDataMinerLoader;
    private String swfocSourcesDir;
    private String modSourcesDir;
    private File masterTextFile;


    public Config(String path) {
        propertiesPath = path;
        loadProperties();
        xmlDataMinerLoader = new XMLDataMinerLoader(dataMinerFile);
    }

    private void loadProperties() {
        System.out.println("Loading properties...");
        try (InputStream input = new FileInputStream(propertiesPath)) {
            Properties prop = new Properties();

            prop.load(input);

            swfocSourcesDir = prop.getProperty("swfoc.xml");
            modSourcesDir = prop.getProperty("mod.xml");
            String dataMinerFileName = prop.getProperty("swfoc.dataminerfiles");

            masterTextFile = new File(prop.getProperty("mod.mastertext"));
            if (!masterTextFile.exists()) {
                System.err.println("Master file for mod was not found, trying load it from swfoc sources");
                masterTextFile = new File(prop.getProperty("swfoc.mastertext"));
            }

            dataMinerFile = getFileForName(dataMinerFileName);
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public File getFileForName(String filename) throws FileNotFoundException {
        try {
            return findFileIgnoreCase(filename, modSourcesDir);
        } catch (FileNotFoundException e) {
            System.err.println(String.format("File %s was not found in %s, trying load it from swfoc sources", filename, modSourcesDir));
            return findFileIgnoreCase(filename, swfocSourcesDir);
        }
    }

    File findFileIgnoreCase(String filename, String dir) throws FileNotFoundException {
        if (filename.contains("/")) {
            StringBuilder builder = new StringBuilder();
            String[] split = filename.split("/");
            builder.append(dir);
            for (int i = 0; i < split.length-1; i++)
                builder.append(split[i]);
            filename = split[split.length-1];
            dir = builder.toString();
        }

        if (filename.contains("\\")) {
            StringBuilder builder = new StringBuilder();
            String[] split = filename.split("\\\\");
            builder.append(dir);
            for (int i = 0; i < split.length-1; i++)
                builder.append(split[i]).append("/");
            filename = split[split.length-1];
            dir = builder.toString();
        }


        File directory = new File(dir);
        File[] contents = directory.listFiles();
        if (contents != null)
            for (File f : contents) {
                if (f.getName().equalsIgnoreCase(filename))
                    return f;
            }
        throw new FileNotFoundException(filename + " was not found.");
    }

    public File getMasterTextFile() {
        return masterTextFile;
    }

    public File getCampaignFile() throws FileNotFoundException {
        return getFileForName(xmlDataMinerLoader.getCampaignFile());
    }

    public File getTradeRouteFile() throws FileNotFoundException {
        return getFileForName(xmlDataMinerLoader.getTradeRouteFile());
    }

    public File getGameObjectFile() throws FileNotFoundException {
        return getFileForName(xmlDataMinerLoader.getGameObjectFile());
    }

    public File getFactionFile() throws FileNotFoundException {
        return getFileForName(xmlDataMinerLoader.getFactionFile());
    }
}
