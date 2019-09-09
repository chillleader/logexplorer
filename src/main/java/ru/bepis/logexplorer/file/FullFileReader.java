package ru.bepis.logexplorer.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class FullFileReader {

    private final File file;

    public FullFileReader(String path) throws FileNotFoundException {
        file = new File(path);
        if (!file.exists() || file.isDirectory()) throw new FileNotFoundException();
    }

    public String readFile() throws IOException {
        StringBuilder sb = new StringBuilder();
        try (LineIterator it = FileUtils.lineIterator(file, "UTF-8")) {
            while (it.hasNext()) {
                sb.append(it.nextLine());
                sb.append('\n');
            }
        }
        return sb.toString();
    }

}
