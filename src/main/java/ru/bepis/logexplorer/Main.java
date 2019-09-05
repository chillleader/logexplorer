package ru.bepis.logexplorer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ru.bepis.logexplorer.file.FileNameExtractor;
import ru.bepis.logexplorer.file.FileUtil;
import ru.bepis.logexplorer.file.Searcher;
import ru.bepis.logexplorer.ui.MainUI;
import ru.bepis.logexplorer.ui.SearchDialog;

public class Main {

    public static void main(String[] args) {
        /*MainUI ui = MainUI.getInstance();*/
        List<String> dirs = new ArrayList<>();
        dirs.add("C:/Users/easyd/Desktop");
        Searcher searcher = new Searcher();
        searcher.setDirectoryList(dirs);
        searcher.setExtension("txt");
        searcher.setSearchTemplate("helios");
        searcher.startSearch();
        while (true) {
            if (searcher.isReady()) break;
        }
        for (Map.Entry<String, List<Integer>> entry : searcher.getResult().entrySet()) {
            System.out.println(entry.getKey());
            for (Integer i : entry.getValue()) {
                System.out.println(i);
            }
        }
    }

}
