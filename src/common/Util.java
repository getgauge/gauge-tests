package common;

import java.io.File;

public class Util {
    public static String combinePath(String path1, String path2) {
        String combined_path = new File(path1, path2).getPath();
        return combined_path;
    }

    public static boolean isFileExists(String fileName) {
        File file = new File(fileName);
        return file.exists() && !file.isDirectory();
    }

    public static boolean isDirectoryExists(String directoryName) {
        File file = new File(directoryName);
        return file.exists() && file.isDirectory();
    }
}
