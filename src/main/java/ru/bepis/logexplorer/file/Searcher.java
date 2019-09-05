package ru.bepis.logexplorer.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Searcher {

    private final int MAX_THREADS = 10;

    // a flag to indicate if the result is ready
    private boolean isReady = true;

    public boolean isReady() {
        return isReady;
    }

    private List<String> directoryList;
    private String searchTemplate;
    private String extension;
    private Map<String, List<Integer>> result = new HashMap<>();

    public void setDirectoryList(List<String> directoryList) {
        this.directoryList = directoryList;
    }

    public void setSearchTemplate(String searchTemplate) {
        this.searchTemplate = searchTemplate;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Map<String, List<Integer>> getResult() {
        return result;
    }

    public void startSearch() throws IllegalStateException {
        if (directoryList == null || searchTemplate == null) {
            throw new IllegalStateException(
                "directoryList and searchTemplate must be set before start");
        }
        isReady = false;
        if (extension == null || extension.isEmpty()) {
            extension = "*";
        }
        final List<String> fileNames;
        try {
            fileNames = FileNameExtractor.convertToFileNames(directoryList, extension);
            ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
            List<Future<List<Integer>>> futures = new ArrayList<>();

            for (String name : fileNames) {
                Callable<List<Integer>> task = () -> {
                    FileUtil util = new FileUtil();
                    util.openFile(name);
                    return util.searchOccurrences(searchTemplate);
                };
                Future<List<Integer>> future = executor.submit(task);
                futures.add(future);
            }

            for (int i = 0; i < fileNames.size(); i++) {
                List<Integer> searchResult = futures.get(i).get();
                if (searchResult.isEmpty()) {
                    continue;
                }
                String fileName = fileNames.get(i);
                result.put(fileName, searchResult);
            }
            executor.shutdown();
            isReady = true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
