package com.backup.version;

import java.io.*;
import java.util.*;

public class VersionManager {

    private static final String VERSION_DIR = "versions/";

    public static void saveVersion(Object obj, String id) throws IOException {
        new File(VERSION_DIR).mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(VERSION_DIR + id + ".dat"))) {
            oos.writeObject(obj);
        }
    }

    public static List<Object> listVersions() throws Exception {
        List<Object> list = new ArrayList<>();

        File dir = new File(VERSION_DIR);
        for (File file : dir.listFiles()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(file))) {
                list.add(ois.readObject());
            }
        }
        return list;
    }
}