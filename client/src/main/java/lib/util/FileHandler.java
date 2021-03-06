package lib.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHandler {

    public static byte[] readFile(String isrc, String fileName) throws IOException {
        byte[] content = Files.readAllBytes(Paths.get(isrc + "/" + fileName));
        return content;
    }
}
