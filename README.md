# OBSIDIA TOOLS 🎮⚡

> **Real Android Game Graphics Controller & Device Performance Monitor**  
> Built with Premium Dark Obsidian Aesthetic • Safe & 100% Non-Root • Zero Risk of Ban

---

## 💎 Informasi Aplikasi

- **Nama Aplikasi**: OBSIDIA TOOLS
- **Application ID / Package**: `com.aistudio.obsidiatools.k8z4p`
- **Versi Aplikasi**: `v1.0.1` (VersionCode: `1`, VersionName: `"1.0"`)
- **Platform**: Android 7.0+ (Min SDK 24, Target SDK 36)
- **Repository Resmi**: [zuccheroxix-lab/OBSIDIA-TOOLS](https://github.com/zuccheroxix-lab/OBSIDIA-TOOLS)
- **Halaman Releases**: [Releases zuccheroxix-lab/OBSIDIA-TOOLS](https://github.com/zuccheroxix-lab/OBSIDIA-TOOLS/releases)

---

## 📁 Struktur File Project (GitHub Ready)

```text
/
├── app/                                 # Source code Android (Jetpack Compose, MVVM)
│   ├── src/main/java/com/example/       # UI Screens, ViewModel, Telemetry & Graphics logic
│   └── src/main/res/                    # Assets, Adaptive Icon, Themes, Vectors
├── release/                             # Folder Binary APK Hasil Build Nyata
│   ├── app-debug.apk                    # APK Debug (25 MB, Signed)
│   └── app-release.apk                  # APK Release (19 MB, Signed & Optimized)
├── .github/workflows/
│   └── android-release.yml              # CI/CD otomatis untuk build & publish release
├── build.gradle.kts                     # Root build configuration
├── settings.gradle.kts                  # Project settings
└── README.md                            # Panduan & Dokumentasi
```

---

## 📦 Binary APK Hasil Build (Tersedia di `/release/`)

Binary APK berikut telah dikompilasi secara nyata dari source code OBSIDIA TOOLS:

| Varian Build | File Output Nyata | Ukuran | Validasi Status | SHA-256 Checksum |
| :--- | :--- | :--- | :--- | :--- |
| **RELEASE APK** | `release/app-release.apk` | **19 MB** | Valid, Signed & R8 ProGuard Optimized | `3121219bcc78389834e9eb20f18b897c35eb88c13dc8a8f74c138a1538aa0a96` |
| **DEBUG APK** | `release/app-debug.apk` | **25 MB** | Valid, Signed (Debug Key) | `19e6291e418cf1829848b107e9c4bbeac37d05211f58a879e0b14084aa6d9511` |

> File APK juga tersimpan di path standar Gradle:
> - `app/build/outputs/apk/release/app-release.apk`
> - `app/build/outputs/apk/debug/app-debug.apk`

---

## 📲 Cara Install APK di Perangkat Android

1. **Unduh APK**:
   - Ambil file `app-release.apk` (atau `app-debug.apk`) dari folder `release/` atau dari halaman GitHub Releases.
2. **Kirim ke HP**:
   - Pindahkan file `.apk` ke ponsel Android Anda melalui kabel USB, WhatsApp, Telegram, atau Google Drive.
3. **Izinkan Instalasi dari Sumber Tak Dikenal**:
   - Buka file `.apk` di ponsel.
   - Jika muncul peringatan keamanan *"Install unknown apps / Sumber tidak dikenal"*, pilih **Settings** lalu aktifkan izin untuk File Manager / Browser yang Anda gunakan.
4. **Selesaikan Instalasi**:
   - Ketuk **Install** → Selesai!
   - Buka aplikasi **OBSIDIA TOOLS** dari drawer menu smartphone Anda.

---

## 🛠️ Cara Build Ulang Project

Jika Anda ingin mengompilasi ulang APK sendiri dari source code:

### Prasyarat
- JDK 17
- Android SDK 34/36

### 1. Build Debug APK
```bash
./gradlew assembleDebug
```
Hasil build: `app/build/outputs/apk/debug/app-debug.apk`

### 2. Build Release APK
Pastikan keystore `my-upload-key.jks` tersedia di root project (atau buat dengan `keytool`), lalu jalankan:
```bash
STORE_PASSWORD=android KEY_PASSWORD=android ./gradlew assembleRelease
```
Hasil build: `app/build/outputs/apk/release/app-release.apk`

### 3. Build & Salin ke Folder `/release/`
```bash
mkdir -p release
cp app/build/outputs/apk/debug/app-debug.apk release/app-debug.apk
cp app/build/outputs/apk/release/app-release.apk release/app-release.apk
```

---

## 🚀 Publikasi Otomatis via GitHub Actions

Workflow otomatis telah dikonfigurasi di `.github/workflows/android-release.yml`.

### Cara Memicu Rilis Otomatis:
1. **Via Tombol di GitHub (Paling Mudah)**:
   - Buka [zuccheroxix-lab/OBSIDIA-TOOLS/actions](https://github.com/zuccheroxix-lab/OBSIDIA-TOOLS/actions).
   - Pilih workflow **"Android Release Build & Publish"**.
   - Klik **"Run workflow"** → Masukkan tag (contoh: `v1.0.1`) → Klik tombol hijau.
2. **Via Git Tag di Terminal**:
   ```bash
   git tag v1.0.1
   git push origin v1.0.1
   ```

### ⚠️ Izin Wajib di GitHub Repository:
Agar GitHub Actions di repository Anda diizinkan untuk membuat Release dan mengunggah aset APK:
1. Buka repositori `zuccheroxix-lab/OBSIDIA-TOOLS` di browser.
2. Masuk ke **Settings** → **Actions** → **General**.
3. Gulir ke bagian **Workflow permissions**.
4. Pilih **"Read and write permissions"** lalu klik **Save**.

### 🔐 Catatan Repository Private:
- Jika status repositori `zuccheroxix-lab/OBSIDIA-TOOLS` adalah **Private**, maka halaman Releases dan file APK hanya dapat diakses oleh akun Anda atau kolaborator yang login.
- Agar publik luas dapat mengunduh APK tanpa login, ubah status repositori menjadi **Public** melalui *Settings > General > Change repository visibility*.

---

## 👨‍💻 Kredit

- **Nama Aplikasi**: OBSIDIA TOOLS
- **Versi**: 1.0.1
- **Dibuat Oleh**: ZUCCHERO XANN
- **Lisensi**: MIT License
