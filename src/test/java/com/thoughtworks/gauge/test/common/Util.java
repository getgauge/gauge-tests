package com.thoughtworks.gauge.test.common;

import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Util {
    public static String combinePath(String path1, String path2) {
        String combined_path = new File(path1, path2).getPath();
        return combined_path;
    }

    public static File getTempDir() throws IOException {
        String projectDirName = "gauge_project_" + System.nanoTime();
        File projectDir = new File(getSystemTempDir(), projectDirName);
        projectDir.mkdir();
        return projectDir;
    }

    private static File getSystemTempDir() throws IOException {
        return new File(System.getProperty("java.io.tmpdir"));
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

    public static List<String> toList(Table table, String columnName) {
        ArrayList<String> values = new ArrayList<String>();
        for (TableRow row : table.getTableRows()) {
            values.add(row.getCell(columnName));
        }
        return values;
    }

    public static ArrayList<String> quotifyValues(List<String> values) {
        ArrayList<String> quotedValues = new ArrayList<String>();
        for (String val : values) {
            quotedValues.add(String.format("\"%s\"", val));
        }
        return quotedValues;
    }


    public static String commaSeparatedValues(ArrayList<String> strings) {
        return StringUtils.join(strings, ",");
    }
}
