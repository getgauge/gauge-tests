package common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static void writeToFile(String absolutePath, String data) throws IOException {
        FileOutputStream stream = new FileOutputStream(absolutePath, false);
        stream.write(data.getBytes());
        stream.close();
    }

    public static void appendToFile(String absolutePath, String data) throws IOException {
        FileOutputStream stream = new FileOutputStream(absolutePath, true);
        stream.write(data.getBytes());
        stream.close();
    }

    public static String getCurrentLanguage() {
        String language = System.getenv("language");
        if (language == null || language.equalsIgnoreCase("")) {
            language = "java";
        }

        return language;
    }

    public static String capitalize(String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }

    public static String getUniqueName() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return String.format("Name%d%s", System.nanoTime(), dateFormat.format(new Date()));
    }
}
