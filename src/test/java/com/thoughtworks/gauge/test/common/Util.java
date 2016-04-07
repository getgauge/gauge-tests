package com.thoughtworks.gauge.test.common;

import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;

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
        if(path1==null)
            return path2;
        if(path2==null)
            return path1;
        String combined_path = new File(path1, path2).getPath();
        return combined_path;
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


    public static String joinList(List<String> list) {
        if (list.isEmpty()) return "";
        return list.stream().
                reduce((t, u) -> t + "," + u).
                get();
    }

    public static int countOccurrences(String in, String of) {
        int ctr = 0;
        int indx = in.indexOf(of);
        while (indx > -1) {
            ctr++;
            indx = in.indexOf(of, indx + of.length());
        }
        return ctr;
    }
}
