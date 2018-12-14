package gt.practice.linesInDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CountLines {

    private Function<List<File>, List<File>> fileList = (List<File> basePath) -> basePath.stream()
            .flatMap(x -> {
                if (x.isDirectory())
                    return this.fileList.apply(Objects.requireNonNull(Arrays.asList(x.listFiles()))).stream();
                else
                    return Stream.of(x);
            }).collect(Collectors.toList());

    private Function<String, List<File>> getFileList = path -> fileList.apply(Collections.singletonList(new File(path)));

    private BiFunction<String, Function<File, Integer>, Integer> countFunction =
            (path, func) -> getFileList.apply(path).stream().mapToInt(func::apply).sum();

    private Function<String, Integer> countJavaFilesIn = path -> countFunction.apply(
            path, file -> {
                if (file.getName().endsWith(".java"))
                    return 1;
                else
                    return 0;
            });

    private Function<String, Integer> countLinesIn = path -> countFunction.apply(
            path, file -> {
                try {
                    return Files.readAllLines(file.toPath()).size();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return 0;
            });

    public static void main(String[] args) {
        String basePath = "/Users/gauravt/workspace/Practice/javaCode/src/main/resources/linesInDirectory";
        CountLines countLines = new CountLines();
        System.out.println(countLines.getFileList.apply(basePath).size());
        System.out.println(countLines.countLinesIn.apply(basePath));
        System.out.println(countLines.countJavaFilesIn.apply(basePath));
        System.out.println(countLines.countAttributesIn(basePath));
    }

    private Attributes countAttributesIn(String basePath) {
        return applyFunction1(basePath, f -> {
            try {
                List<String> strings = Files.readAllLines(f.toPath());
                Map<String, List<String>> map = strings.stream()
                        .collect(Collectors.groupingBy(s -> {
                            if (s.startsWith("//")) {
                                return "commentLine";
                            } else if (s.isEmpty()) {
                                return "blankLine";
                            } else if (s.startsWith("import")) {
                                return "importLine";
                            } else {
                                return "codeLine";
                            }
                        }));

                return new Attributes(map.getOrDefault("commentLine", Collections.emptyList()).size(),
                        map.getOrDefault("blankLine", Collections.emptyList()).size(),
                        map.getOrDefault("importLine", Collections.emptyList()).size(),
                        map.getOrDefault("codeLine", Collections.emptyList()).size());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new Attributes(0, 0, 0, 0);
        });
    }

    private Attributes applyFunction1(String path, Function<File, Attributes> func) {
        return getFileList.apply(path).stream()
                .map(func::apply)
                .reduce(new Attributes(0, 0, 0, 0),
                        (attributes, attributes2) ->
                                new Attributes(attributes.getComments() + attributes2.getComments(),
                                        attributes.getBlankLines() + attributes2.getBlankLines(),
                                        attributes.getImportLine() + attributes2.getImportLine(),
                                        attributes.getCodeLine() + attributes2.getCodeLine()));
    }
}


