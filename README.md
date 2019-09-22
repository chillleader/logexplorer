# LogExplorer
LogExplorer is a simple log searching tool created with Java & Swing

## Key features
* Searching for a keyword in selected directories;
* Finding multiple keyword occurrences in a single file;
* Searching only in files with a specific extension ('.log' by default);
* Multithreading that allows to complete almost any search rapidly;
* Opening log files and navigation between found occurences (next/previous/select all);
* Opening muiltiple files in separate tabs.

The search result is displayed in the left side of the window as a file tree.
In the right side the contents of the file are displayed.

## Screenshots
Search dialog window | Search result
-------------------- | -------------
<img src="/img/dialog.png" width="400"> | <img src="/img/result.png" width="400">

## Known issues
* Problems with navigation occur from time to time;
* Page-by-page reading mode for huge files exists, but still requires work to do;
* Minor UI problems (e.g. long file names in tabs)
I still work on this project when I have time.
