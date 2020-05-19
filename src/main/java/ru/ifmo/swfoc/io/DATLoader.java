package ru.ifmo.swfoc.io;

import ru.ifmo.swfoc.DATReader;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class DATLoader {
    private Map<String, String> records;
    private DATReader reader;

    public DATLoader(File file) throws IOException {
        reader = new DATReader(file);
        records = reader.readFile();
    }

    public String getInGameName(String key) {
        if (key == null)
            key = "";
        return records.get(key.trim());
    }
}
