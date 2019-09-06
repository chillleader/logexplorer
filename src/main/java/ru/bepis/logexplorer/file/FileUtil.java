package ru.bepis.logexplorer.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class FileUtil {

    private File file;

    /**
     * This method should be used to specify the file before searching
     * @param path A path to file
     * @throws FileNotFoundException When file doesn't exist
     */
    public void openFile(String path) throws FileNotFoundException {
        file = new File(path);
        if (!file.exists()) throw new FileNotFoundException();
        // todo: check encoding (UTF-8)
    }

    /**
     * This method searches for occurrences of a given template in a currently opened file
     * @param template A text to search for
     * @return List of numbers of lines where the requested text has been found,
     * empty list if the file doesn't contain any occurrences
     * @throws IOException When couldn't read the file
     */
    public List<Integer> searchOccurrences(String template) throws IOException {
        if (file == null) throw new FileNotFoundException();
        List<Integer> resultList = new ArrayList<>();
        int charNumber = 0;
        try (LineIterator it = FileUtils.lineIterator(file, "UTF-8")) {
            while (it.hasNext()) {
                String line = it.nextLine();
                if (line.contains(template))
                    resultList.add(charNumber);
                charNumber += line.length();
            }
        }
        return resultList;
    }

}
