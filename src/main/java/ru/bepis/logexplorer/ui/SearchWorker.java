package ru.bepis.logexplorer.ui;

import java.util.List;
import java.util.Map;
import javax.swing.SwingWorker;
import ru.bepis.logexplorer.file.MultithreadedSearcher;
import ru.bepis.logexplorer.file.Searcher;

public class SearchWorker extends SwingWorker<Map<String, List<Long>>, String> {

    private Searcher searcher = new MultithreadedSearcher();
    private String searchTemplate;
    private List<String> dirs;
    private String extension = "log";

    public void setSearchTemplate(String searchTemplate) {
        this.searchTemplate = searchTemplate;
    }
    public void setDirs(List<String> dirs) {
        this.dirs = dirs;
    }
    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    protected Map<String, List<Long>> doInBackground() throws Exception {
        searcher.setSearchTemplate(searchTemplate);
        searcher.setExtension(extension);
        searcher.setDirectoryList(dirs);
        searcher.startSearch();
        while (true) {
            if (searcher.isReady()) break;
        }
        return searcher.getResult();
    }
}
