package me.sophie.countingcodelines;

import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileStats {
    VirtualFile file;
    private int lines;
    private int codeLines;
    private int commentLines;
    private int blankLines;

    public FileStats (VirtualFile file) {
        this.file = file;
    }

    public void readStats() throws IOException {
        Path path = file.toNioPath();
        System.out.println("file: " + file.getName());
        System.out.println("path: " + path.toString());
        System.out.println("file: " + file.getPath());
        List<String> lines = Files.readAllLines(path);

        for(int i = 0; i<lines.size(); i++) {
            String line = lines.get(i);
            line = line.trim();

            if(line.isEmpty()) {
                blankLines++;
            }
            if(line.startsWith("//")) {
                commentLines++;
            }
            if(line.contains("/*")) {
                commentLines++;
                while(!line.contains("*/")) {
                    commentLines++;
                    line = lines.get(i+1);
                    i++;
                }
            }
        }

        this.lines = lines.size();
        codeLines = this.lines-commentLines-blankLines;
    }

    public int getLines() {
        return lines;
    }

    public int getBlankLines() {
        return blankLines;
    }

    public VirtualFile getFile() {
        return file;
    }

    public int getCodeLines() {
        return codeLines;
    }

    public int getCommentLines() {
        return commentLines;
    }
}
