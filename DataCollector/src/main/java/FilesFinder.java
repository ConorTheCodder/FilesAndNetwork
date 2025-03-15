import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FilesFinder {
@SneakyThrows
    public static List<Path> findFileExtension(Path root, String extensionName) { //"C:\Users\rush7\IdeaProjects\java_basics\__MACOSX\data"
        List<Path> list = new ArrayList<>();
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(root)) {
            for (Path path : paths) {
                String fileName = path.getFileName().toString().toLowerCase();
                if (Files.isRegularFile(path) && fileName.endsWith(extensionName.toLowerCase())) {
                    list.add(path);
                } else if (Files.isDirectory(path)) {
                    list.addAll(findFileExtension(path, extensionName));
                }
            }
            return list;
        }
    }
}