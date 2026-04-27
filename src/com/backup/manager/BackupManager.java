package com.backup.manager;

import com.backup.data.BackupData;
import com.backup.io.*;
import com.backup.version.VersionManager;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class BackupManager {

    private static final String BACKUP_DIR = "backups/";

    public BackupManager() {
        new File(BACKUP_DIR).mkdirs();
    }

    private void createVersion(BackupData backup) throws IOException {

        File dir = new File("versions/");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String filePath = "versions/version_" + backup.getBackupId() + ".dat";

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filePath))) {

            oos.writeObject(backup);
        }
    }

    public void createBackup(String desc, Map<String, Object> data) throws Exception {

        BackupData backup = new BackupData(desc, data);

        String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String base = BACKUP_DIR + "backup_" + time;

        // Binary
        backup.saveToBinaryFile(base + ".dat");

        // CSV
        CSVHandler.writeCSV(base + ".csv", data);

        // JSON
        JSONHandler.writeJSON(base + ".json", data);

        // Version
        VersionManager.saveVersion(backup, backup.getBackupId());

        System.out.println("Backup created: " + base);
    }

    public BackupData restore(String path) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(path))) {
            return (BackupData) ois.readObject();
        }
    }

    public List<Object> listVersions() throws Exception {
        return VersionManager.listVersions();
    }

    public void cleanup(int days) {
        File dir = new File(BACKUP_DIR);

        long cutoff = System.currentTimeMillis() - (days * 86400000L);

        for (File file : dir.listFiles()) {
            if (file.lastModified() < cutoff) {
                file.delete();
            }
        }
    }

    public List<BackupData> listAllVersions()
            throws IOException, ClassNotFoundException {

        List<BackupData> versions = new ArrayList<>();

        File versionDir = new File("versions/");

        if (!versionDir.exists()) return versions;

        File[] files = versionDir.listFiles((dir, name) -> name.endsWith(".dat"));

        if (files != null) {
            for (File file : files) {
                try (ObjectInputStream ois = new ObjectInputStream(
                        new FileInputStream(file))) {

                    versions.add((BackupData) ois.readObject());
                }
            }
        }

        // Sort newest first
        versions.sort((b1, b2) ->
                b2.getBackupTime().compareTo(b1.getBackupTime()));

        return versions;
    }

    public String createBackupWithOutput(String desc, Map<String, Object> data) throws Exception {

        BackupData backup = new BackupData(desc, data);

        String time = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String base = "backups/backup_" + time;

        backup.saveToBinaryFile(base + ".dat");
        backup.saveToCompressedFile(base + ".dat.gz");
        backup.exportToCSV(base + ".csv");

        createVersion(backup);

        System.out.println("\nBackup created successfully!");
        System.out.println("Files created:");
        System.out.println("  • " + base + ".dat");
        System.out.println("  • " + base + ".dat.gz");
        System.out.println("  • " + base + ".csv");

        return backup.getBackupId();
    }

    public int cleanupOldBackupsWithCount(int days) {

        File dir = new File("backups/");
        int count = 0;

        long cutoff = System.currentTimeMillis() - (days * 86400000L);

        for (File f : dir.listFiles()) {
            if (f.lastModified() < cutoff) {
                if (f.delete()) {
                    System.out.println("Deleted old backup: " + f.getName());
                    count++;
                }
            }
        }
        return count;
    }
    public double getTotalSizeMB() {
        File dir = new File("backups/");
        long total = 0;

        for (File f : dir.listFiles()) {
            total += f.length();
        }
        return Math.round((total / 1024.0 / 1024.0) * 10) / 10.0;
    }

    public void printStatistics() {

        File dir = new File("backups/");
        File[] files = dir.listFiles();

        int total = files.length;
        long size = 0;

        int dat = 0, gz = 0, csv = 0;

        for (File f : files) {
            size += f.length();

            if (f.getName().endsWith(".dat")) dat++;
            else if (f.getName().endsWith(".gz")) gz++;
            else if (f.getName().endsWith(".csv")) csv++;
        }

        System.out.println("\n=== BACKUP STATISTICS ===");
        System.out.println("📊 Storage Usage:");
        System.out.println("• Total Backups: " + total);
        System.out.println("• Total Size: " + (size / 1024 / 1024) + " MB");

        System.out.println("\n🗂️ File Types:");
        System.out.println("• .dat files: " + dat);
        System.out.println("• .dat.gz files: " + gz);
        System.out.println("• .csv files: " + csv);
    }

    public BackupData restoreFromBinary(String filePath)
            throws IOException, ClassNotFoundException {

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filePath))) {

            return (BackupData) ois.readObject();
        }
    }
    public BackupData restoreFromCompressed(String filePath)
            throws IOException, ClassNotFoundException {

        try (ObjectInputStream ois = new ObjectInputStream(
                new java.util.zip.GZIPInputStream(
                        new FileInputStream(filePath)))) {

            return (BackupData) ois.readObject();
        }
    }
    public Map<String, Object> importFromCSV(String filePath) throws IOException {
        return com.backup.io.CSVHandler.readCSV(filePath);
    }

}