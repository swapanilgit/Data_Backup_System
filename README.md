# 📦 Data Backup System (Java)

A complete **Data Backup System** built using Java that supports serialization, multiple file formats, version management, compression, and backup utilities.

---

## 🚀 Features

* ✅ Object Serialization (Binary `.dat`)
* ✅ Compressed Backups (`.dat.gz`)
* ✅ CSV Export & Import
* ✅ JSON Support (Gson)
* ✅ Version Management
* ✅ Backup Cleanup (auto delete old files)
* ✅ Backup Statistics
* ✅ Try-with-resources (safe file handling)

---

## 📁 Project Structure

```
src/com/backup/
│
├── Main.java
├── data/
│   └── BackupData.java
├── manager/
│   └── BackupManager.java
├── io/
│   ├── CSVHandler.java
│   ├── JSONHandler.java
│   └── FileUtils.java
├── compression/
│   └── CompressionUtils.java
├── version/
│   └── VersionManager.java
```

---

## ⚙️ Requirements

* Java 8 or higher
* Gson library

---

## 📦 Setup Instructions

### 1. Download Gson

Download:

```
gson-2.9.1.jar
```

Place inside:

```
lib/
```

---

### 2. Compile

**Windows:**

```
javac -cp "lib/gson-2.9.1.jar" -d . src/com/backup/**/*.java
```

**Linux/Mac:**

```
javac -cp "lib/gson-2.9.1.jar" -d . src/com/backup/**/*.java
```

---

### 3. Run

**Windows:**

```
java -cp ".;lib/gson-2.9.1.jar" com.backup.Main
```

**Linux/Mac:**

```
java -cp ".:lib/gson-2.9.1.jar" com.backup.Main
```

---

## 🖥️ Application Menu

```
1. Create New Backup
2. Restore from Binary
3. Restore from Compressed
4. Import from CSV
5. List All Versions
6. Cleanup Old Backups
7. Backup Statistics
8. Exit
```

---

## 📊 Example Features

### ✔ Create Backup

* Stores data in:

    * `.dat`
    * `.dat.gz`
    * `.csv`

### ✔ Restore Backup

* From binary and compressed files

### ✔ Version Management

* Keeps history of backups

### ✔ Statistics

* Total size
* File types
* Backup count

---

## 🧠 Concepts Used

* File I/O (Java)
* Serialization & Deserialization
* BufferedReader / BufferedWriter
* Exception Handling
* Collections (Map, List)
* GZIP Compression
* Modular Architecture

---

## 📌 Future Improvements

* GUI (JavaFX / Swing)
* Encryption support
* Cloud backup (Firebase / AWS)
* Auto scheduler (cron-like)

---

## 👨‍💻 Author

    Swapanil Gupta

---


