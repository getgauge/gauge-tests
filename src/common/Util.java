package common;

import java.io.File;
import java.io.IOException;

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

    public static File getTempDir() throws IOException {
        String projectDirName = "gauge_project_" + System.nanoTime();
        File projectDir = new File(getSystemTempDir(), projectDirName);
        projectDir.mkdir();
        return projectDir;
    }

    private static File getSystemTempDir() throws IOException {
        File tempFile = File.createTempFile("gauge", "");
        File tempDir = tempFile.getParentFile();
        tempFile.delete();
        return tempDir;
    }
}
