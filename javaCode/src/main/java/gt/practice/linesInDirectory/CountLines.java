package gt.practice.linesInDirectory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

public class CountLines {
    public static void main(String[] args) throws IOException {
        String basePath = "/Users/gauravt/workspace/Practice/javaCode/src/main/resources/linesInDirectory";
        CountLines countLines = new CountLines();
        System.out.println(countLines.countLinesIn(new File(basePath + "/File2")));
        System.out.println(countLines.countLinesIn(new File(basePath)));
    }

    private int countLinesIn(File file) {
        if (file.isDirectory()) {
            return Stream.of(file.listFiles())
                    .mapToInt(this::countLinesIn)
                    .sum();

        } else {
            return countForFile(file);
        }
    }

    private int countForFile(File file)  {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int count = 0;
            while (reader.readLine() != null) count++;
            return count;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


