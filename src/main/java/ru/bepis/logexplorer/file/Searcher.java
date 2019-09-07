package ru.bepis.logexplorer.file;

import java.util.List;
import java.util.Map;

public interface Searcher {

    // specify directories where search is conducted
    void setDirectoryList(List<String> directoryList);

    // specify text to search for
    void setSearchTemplate(String searchTemplate);

    // specify file extension (.log by default)
    void setExtension(String extension);

    // if no request and/or directory list is provided, an exception is thrown
    void startSearch() throws IllegalStateException;

    // returns false if the search is in progress, true otherwise
    public boolean isReady();

    // list contains positions in file where text was found
    Map<String, List<Integer>> getResult();

}
