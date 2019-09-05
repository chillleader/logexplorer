package ru.bepis.logexplorer.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This util is used to convert a list of directory names into a list of file names excluding the
 * unwanted files by their extension name.
 */
public class FileNameExtractor {

    public static List<String> convertToFileNames(List<String> directoryNames, String extension)
        throws IOException {
        // extract files from directories
        List<String> fileNames = new ArrayList<>();
        for (String dirName : directoryNames) {
            File dir = new File(dirName);
            if (!dir.exists()) {
                throw new FileNotFoundException();
            }
            if (!dir.isDirectory()) {
                fileNames.add(dir.getName());
                continue;
            }
            try (Stream<Path> pathStream = Files.walk(Paths.get(dirName))) {
                fileNames.addAll(pathStream.map(Path::toString).collect(Collectors.toList()));
            }

        }
        // filter the unwanted files by extension
        final String regex = "([^\\s]+(\\.(?i)(" + extension + "))$)";
        try (Stream<String> pathStream = fileNames.stream()) {
            return pathStream.filter(path -> path.matches(regex))
                .collect(Collectors.toList());
        }
    }

}
