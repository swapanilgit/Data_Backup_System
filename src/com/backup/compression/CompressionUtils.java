package com.backup.compression;

import java.io.*;
import java.util.zip.*;

public class CompressionUtils {

    public static void compress(String input, String output) throws IOException {
        try (
            FileInputStream fis = new FileInputStream(input);
            GZIPOutputStream gzos = new GZIPOutputStream(new FileOutputStream(output))
        ) {
            fis.transferTo(gzos);
        }
    }

    public static void decompress(String input, String output) throws IOException {
        try (
            GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(input));
            FileOutputStream fos = new FileOutputStream(output)
        ) {
            gzis.transferTo(fos);
        }
    }
}