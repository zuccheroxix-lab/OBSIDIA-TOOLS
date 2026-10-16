# OBSIDIA TOOLS 🎮⚡

> **Real Android Game Graphics Controller & Device Performance Monitor**  
> Built with Premium Dark Obsidian Aesthetic • Safe & 100% Non-Root • Zero Risk of Ban

---

## 💎 Overview

**OBSIDIA TOOLS** adalah aplikasi Android native yang dirancang untuk mengoptimalkan pengalaman bermain game pada perangkat Android. Dibangun dengan tema **Dark Obsidian** yang elegan, aksen **Crimson Red**, dan border metalik premium, OBSIDIA TOOLS menyediakan kontrol profil grafis nyata, pemantauan perangkat real-time, manajemen game, dan peluncur game siap pakai tanpa memerlukan hak akses root.

---

## ✨ Fitur Utama

### 1. 🏠 Home Dashboard
- **Identitas Premium**: Logo obsidian heksagonal bercahaya dan header eksklusif.
- **Informasi Perangkat Otentik**: Model smartphone, pabrikan, dan versi Android (API Level).
- **Statistik Cepat**: Status CPU, ketersediaan RAM, serta persentase dan suhu baterai secara langsung.
- **Quick Optimize**: Membersihkan cache aplikasi dan menyiapkan lingkungan memori untuk gaming.

### 2. 🎛️ Graphics Control
- **4 Pilihan Preset Cepat**:
  - 🌿 *Battery Saver* (Hemat baterai, suhu rendah, 30 FPS target)
  - ⚖️ *Balanced* (Seimbang performa & visual, 60 FPS target)
  - ⚡ *Performance* (FPS stabil, latency rendah)
  - ⚙️ *Custom* (Penyesuaian manual menyeluruh)
- **Opsi Konfigurasi Grafis**:
  - Graphics Quality: Low, Medium, High, Ultra
  - Resolution Scale: 75%, 100%, 125%
  - Frame Rate Target: 30, 45, 60, 90, 120 FPS
  - Texture Quality, Shadow Quality, dan Anti-Aliasing (MSAA)
- **Siklus Status Validasi Nyata**:
  - `READY`: Pengaturan siap diterapkan
  - `APPLYING`: Proses validasi terhadap batas refresh rate layar perangkat
  - `APPLIED`: Profil berhasil diverifikasi dan disimpan secara persisten
  - `UNSUPPORTED`: Memberikan peringatan jika target FPS melampaui kemampuan display hardware

### 3. 📊 Real-Time Monitor
- **Twin Circular Gauges**: Pemantauan visual dinamis beban CPU & konsumsi RAM.
- **Battery & Pacing Telemetry**: Level baterai aktual, suhu sensor baterai (°C), dan real-time frame pacing cadence.
- **Historical CPU Wave**: Grafik gelombang riwayat penggunaan CPU secara real-time.
- **Label Transparansi Sumber Data**: Menggunakan API Android resmi (`BatteryManager`, `ActivityManager.MemoryInfo`, `Display.refreshRate`).

### 4. 🎯 Game Management & Launcher
- **Daftar Game Cerdas**: Mendeteksi game populer (Free Fire, PUBG Mobile, Mobile Legends, Genshin Impact, COD Mobile, dll.) dan game yang terinstal di perangkat.
- **Pencarian & Filter**: Filter game terpasang dan pencarian instan.
- **Game Launcher Eksklusif**: Dilengkapi banner visual artwork, tombol *Play Now*, ringkasan profil grafis, dan 4 indikator kesiapan (Graphics, Performance, Touch, Network).

### 5. ⚙️ Settings & System Tools
- **Bahasa**: Mendukung Bahasa Indonesia dan English.
- **Clear Cache**: Membersihkan cache aplikasi secara mandiri dengan laporan byte yang dibebaskan.
- **DPI & Resolusi**: Menampilkan resolusi layar, DPI, dan panduan konfigurasi developer options.
- **Custom DNS**: Informasi panduan Private DNS Android (DoT) untuk gaming rendah latensi.
- **Keamanan & Privasi**: Informasi kepatuhan non-root dan nol pengumpulan data pribadi.

---

## 🛡️ Keamanan & Kepatuhan Non-Root

OBSIDIA TOOLS beroperasi 100% aman dan patuh pada pedoman Google Play & sistem keamanan Android:
- ❌ **Tidak memerlukan Root**: Bekerja pada semua perangkat Android standar tanpa modifikasi OS.
- ❌ **Tidak memodifikasi APK game**: Tidak mengubah file internal yang dilindungi dan aman dari sistem anti-cheat (tidak ada risiko banned).
- ❌ **Tidak meminta izin berlebih**: Hanya menggunakan izin install-time standar (`INTERNET`, `ACCESS_NETWORK_STATE`, `VIBRATE`).
- ❌ **Bukan simulator palsu**: Semua angka metrik berasal dari sensor perangkat resmi.

---

## 🚀 Panduan Build & Instalasi

### Prasyarat
- Android Studio Hedgehog atau yang lebih baru
- JDK 17
- Android SDK 34/36 (Minimum Android 7.0 / SDK 24)

### Build Debug APK
```bash
gradle :app:assembleDebug
```
Output APK: `app/build/outputs/apk/debug/app-debug.apk`

### Build Release APK
```bash
gradle :app:assembleRelease
```
Output APK: `app/build/outputs/apk/release/app-release.apk`

---

## 📦 GitHub Release & Download

- **Repository**: [https://github.com/candrajouva03/OBSIDIA-TOOLS](https://github.com/candrajouva03/OBSIDIA-TOOLS)
- **Releases**: [https://github.com/candrajouva03/OBSIDIA-TOOLS/releases](https://github.com/candrajouva03/OBSIDIA-TOOLS/releases)
- **SHA-256 Checksum (Release APK)**: `7c58d9b45086b2daff9efd183c36e367ca26e1f3a12537d169f549c7cdbb711b`
- **SHA-256 Checksum (Debug APK)**: `98572c4c34b24646d5e03f9e3dc34b762f17302b85c31db90ca2c2981b93356e`

---

## 👨‍💻 Kredit

- **Nama Aplikasi**: OBSIDIA TOOLS
- **Versi**: 1.0.0
- **Dibuat Oleh**: ZUCCHERO XANN
- **Slogan**: *GAME • TOOLS • BETTER PERFORMANCE*
