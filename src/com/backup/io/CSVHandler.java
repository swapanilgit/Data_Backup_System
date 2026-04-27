package com.backup.io;

import java.io.*;
import java.util.*;

public class CSVHandler {

    public static void writeCSV(String path, Map<String, Object> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("Key,Value\n");

            for (var entry : data.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        }
    }

    public static Map<String, Object> readCSV(String path) throws IOException {
        Map<String, Object> map = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                map.put(parts[0], parts[1]);
            }
        }
        return map;
    }
}