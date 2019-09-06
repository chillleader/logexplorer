package ru.bepis.logexplorer.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * This class reads the file by pages of given size. close() must be called to release the
 * resources.
 */
public class ChunkFileReader {

    public final int PAGE_SIZE_LINES = 1000;

    // 10 is the value of ASCII line separating char, used to adjust the page start
    private final byte LINE_SEPARATOR_VALUE = 10;
    /* while reading from a random position in the file, we want to display some context
        before the actual text we search for. This is a number of chars to step backwards. */
    private final int PAGE_START_OFFSET = 1000;

    private RandomAccessFile file;

    private long nextPageStart = 0;
    // in pageHistory the start pointer of every opened page is stored
    private List<Long> pageHistory = new ArrayList<>();
    private int curPageIndex = -1; // index of pageHistory that indicates current page start
    // -1 is set before any page is read

    public ChunkFileReader(String path) throws FileNotFoundException {
        file = new RandomAccessFile(path, "r");
    }

    public long getNextPageStart() {
        return nextPageStart;
    }

    public long getPrevPageStart() {
        if (curPageIndex == 0) {
            return 0;
        } else {
            return pageHistory.get(curPageIndex - 1);
        }
    }

    public long getFileSize() throws IOException {
        return file.length() - 1;
    }

    private String readPage(long charNumber) throws IOException {
        if (charNumber >= file.length() - 1) {
            throw new IOException("Index out of file bounds");
        }
        file.seek(charNumber);
        if (charNumber != 0) {
            // make sure the page we display starts from a new line
            while (file.readByte() != LINE_SEPARATOR_VALUE) {
                charNumber--;
                file.seek(charNumber);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < PAGE_SIZE_LINES; i++) {
            String line = file.readLine();
            if (line == null) {
                break;
            }
            String utf8 = new String(line.getBytes(StandardCharsets.ISO_8859_1),
                StandardCharsets.UTF_8);
            stringBuilder.append(utf8);
            stringBuilder.append('\n');
        }
        nextPageStart = file.getFilePointer();
        return stringBuilder.toString();
    }

    public String readPrevPage() throws IOException {
        if (curPageIndex > 0) {
            curPageIndex--;
        }
        return readPage(pageHistory.get(curPageIndex));
    }

    public String readNextPage() throws IOException {
        curPageIndex++;
        pageHistory.add(nextPageStart);
        return readPage(nextPageStart);
    }

    public String readPageContaining(long charNumber) throws IOException {
        charNumber = (charNumber - PAGE_START_OFFSET >= 0) ? charNumber - PAGE_START_OFFSET : 0;
        pageHistory.add(charNumber);
        return readPage(charNumber);
    }

    public void close() throws IOException {
        file.close();
    }

}
