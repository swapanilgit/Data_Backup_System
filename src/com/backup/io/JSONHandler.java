package com.backup.io;

import com.google.gson.Gson;

import java.io.*;
import java.util.Map;

public class JSONHandler {
    private static final Gson gson = new Gson();

    public static void writeJSON(String path, Map<String, Object> data) throws IOException {
        try (Writer writer = new FileWriter(path)) {
            gson.toJson(data, writer);
        }
    }

    public static Map readJSON(String path) throws IOException {
        try (Reader reader = new FileReader(path)) {
            return gson.fromJson(reader, Map.class);
        }
    }
}
