package com.backup;

import com.backup.manager.BackupManager;
import com.backup.data.BackupData;

import java.util.*;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static BackupManager manager = new BackupManager();

    public static void main(String[] args) {

        while (true) {
            showMenu();
            int choice = Integer.parseInt(sc.nextLine());

            try {
                switch (choice) {
                    case 1 -> createBackup();
                    case 2 -> restoreBinary();
                    case 3 -> restoreCompressed();
                    case 4 -> importCSV();
                    case 5 -> listVersions();
                    case 6 -> cleanup();
                    case 7 -> showStats();
                    case 8 -> System.exit(0);
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n=== DATA BACKUP SYSTEM ===");
        System.out.println("1. Create New Backup");
        System.out.println("2. Restore from Binary");
        System.out.println("3. Restore from Compressed");
        System.out.println("4. Import from CSV");
        System.out.println("5. List All Versions");
        System.out.println("6. Cleanup Old Backups");
        System.out.println("7. Backup Statistics");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    // ================= CREATE =================
    private static void createBackup() throws Exception {

        System.out.println("\n=== CREATE NEW BACKUP ===");
        System.out.print("Enter backup description: ");
        String desc = sc.nextLine();

        Map<String, Object> data = new LinkedHashMap<>();

        while (true) {
            System.out.print("\nEnter key: ");
            String key = sc.nextLine();

            System.out.print("Enter value: ");
            String value = sc.nextLine();

            System.out.print("Enter type (String/Integer/Double/Boolean): ");
            String type = sc.nextLine();

            data.put(key, parseValue(value, type));

            System.out.print("Add more items? (y/n): ");
            if (!sc.nextLine().equalsIgnoreCase("y")) break;
        }

        String id = manager.createBackupWithOutput(desc, data);
        System.out.println("\nBackup ID: " + id);
    }

    private static Object parseValue(String value, String type) {
        try {
            return switch (type) {
                case "Integer" -> Integer.parseInt(value);
                case "Double" -> Double.parseDouble(value);
                case "Boolean" -> Boolean.parseBoolean(value);
                default -> value;
            };
        } catch (Exception e) {
            return value;
        }
    }

    // ================= RESTORE =================
    private static void restoreBinary() throws Exception {
        System.out.print("\nEnter binary file path: ");
        String path = sc.nextLine();

        BackupData backup = manager.restoreFromBinary(path);
        printBackup(backup);
    }

    private static void restoreCompressed() throws Exception {
        System.out.print("\nEnter compressed file path: ");
        String path = sc.nextLine();

        BackupData backup = manager.restoreFromCompressed(path);
        printBackup(backup);
    }

    // ================= CSV =================
    private static void importCSV() throws Exception {
        System.out.print("\nEnter CSV file path: ");
        String path = sc.nextLine();

        Map<String, Object> data = manager.importFromCSV(path);
        System.out.println("Imported Data: " + data);
    }


    // ================= VERSION =================
    private static void listVersions() throws Exception {
        List<BackupData> list = manager.listAllVersions();

        System.out.println("\n=== ALL BACKUP VERSIONS ===\n");

        int i = 1;
        for (BackupData b : list) {
            System.out.println("Version " + i + (i == 1 ? ": (Most Recent)" : ":"));
            System.out.println(b);
            System.out.println();
            i++;
        }

        System.out.println("Total Versions: " + list.size());
        System.out.println("Total Backup Size: " + manager.getTotalSizeMB() + " MB");
    }

    // ================= CLEANUP =================
    private static void cleanup() {
        System.out.print("\nEnter days to keep backups: ");
        int days = Integer.parseInt(sc.nextLine());

        int deleted = manager.cleanupOldBackupsWithCount(days);
        System.out.println("✅ Cleanup completed! " + deleted + " files removed.");
    }

    // ================= STATS =================
    private static void showStats() {
        manager.printStatistics();
    }

    // ================= PRINT =================
    private static void printBackup(BackupData backup) {
        System.out.println("\n✅ RESTORED BACKUP:");
        System.out.println(backup);

        System.out.println("\nData Contents:");
        int i = 1;
        for (var e : backup.getData().entrySet()) {
            System.out.println(i++ + ". " + e.getKey() + " = " + e.getValue()
                    + " (" + e.getValue().getClass().getSimpleName() + ")");
        }
    }
}