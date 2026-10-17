package com.example.ui.screens

import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CleaningServices
import androidx.compose.material.icons.outlined.Dns
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material.icons.outlined.StayCurrentPortrait
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.ui.components.ObsidiaCard
import com.example.ui.components.ObsidiaCrimsonButton
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.MetallicSilver
import com.example.ui.theme.MetallicSilverDark
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianCardElevated
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.ObsidiaViewModel

@Composable
fun SettingsScreen(
    viewModel: ObsidiaViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.language.collectAsState()
    val cacheSize by viewModel.cacheSizeBytes.collectAsState()
    val isIndo = language == AppLanguage.INDONESIAN
    val context = LocalContext.current

    var showDpiDialog by remember { mutableStateOf(false) }
    var showDnsDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showResetConfirmDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = null,
                    tint = CrimsonRed,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Settings",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = if (isIndo) "Sesuaikan aplikasi sesuai kebutuhan." else "Adjust application settings and preferences.",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }
        }

        item {
            // Tema
            SettingsRowCard(
                icon = Icons.Outlined.Palette,
                title = if (isIndo) "Tema" else "Theme",
                valueText = "Dark Obsidian (Default)",
                onClick = {
                    Toast.makeText(context, "Obsidian Dark Palette is locked for gaming performance", Toast.LENGTH_SHORT).show()
                },
                testTag = "settings_theme_row"
            )
        }

        item {
            // Bahasa
            SettingsRowCard(
                icon = Icons.Outlined.Language,
                title = if (isIndo) "Bahasa" else "Language",
                valueText = if (isIndo) "Indonesia" else "English",
                onClick = {
                    val nextLang = if (isIndo) AppLanguage.ENGLISH else AppLanguage.INDONESIAN
                    viewModel.setLanguage(nextLang)
                    Toast.makeText(context, if (nextLang == AppLanguage.INDONESIAN) "Bahasa diubah ke Indonesia" else "Language set to English", Toast.LENGTH_SHORT).show()
                },
                testTag = "settings_language_row"
            )
        }

        item {
            // Penyimpanan (Clear Cache)
            SettingsRowCard(
                icon = Icons.Outlined.CleaningServices,
                title = if (isIndo) "Penyimpanan" else "Storage",
                valueText = "Clear Cache (${cacheSize / 1024} KB)",
                onClick = {
                    viewModel.clearAppCache()
                    Toast.makeText(context, "Cache aplikasi berhasil dibersihkan!", Toast.LENGTH_SHORT).show()
                },
                testTag = "settings_clear_cache_row"
            )
        }

        item {
            // DPI & Resolusi
            SettingsRowCard(
                icon = Icons.Outlined.StayCurrentPortrait,
                title = if (isIndo) "DPI & Resolusi" else "DPI & Resolution",
                valueText = if (isIndo) "Atur Manual / Info" else "Manual Guide / Info",
                onClick = { showDpiDialog = true },
                testTag = "settings_dpi_row"
            )
        }

        item {
            // Custom DNS
            SettingsRowCard(
                icon = Icons.Outlined.Dns,
                title = "Custom DNS",
                valueText = if (isIndo) "Atur DNS Pribadi" else "Private DNS Guide",
                onClick = { showDnsDialog = true },
                testTag = "settings_dns_row"
            )
        }

        item {
            // Keamanan & Privasi
            SettingsRowCard(
                icon = Icons.Outlined.Security,
                title = if (isIndo) "Keamanan & Privasi" else "Security & Privacy",
                valueText = if (isIndo) "Izin & Data" else "Permissions & Data",
                onClick = { showPrivacyDialog = true },
                testTag = "settings_privacy_row"
            )
        }

        item {
            // Tentang Aplikasi
            SettingsRowCard(
                icon = Icons.Outlined.Info,
                title = if (isIndo) "Tentang Aplikasi" else "About App",
                valueText = "v1.0.1",
                onClick = { showAboutDialog = true },
                testTag = "settings_about_row"
            )
        }

        item {
            // Reset Pengaturan
            SettingsRowCard(
                icon = Icons.Outlined.CleaningServices,
                title = if (isIndo) "Reset Pengaturan" else "Reset Preferences",
                valueText = if (isIndo) "Kembalikan ke Default" else "Restore Defaults",
                onClick = { showResetConfirmDialog = true },
                testTag = "settings_reset_row"
            )
        }

        item {
            // Download & Release Button
            SettingsRowCard(
                icon = Icons.Outlined.Download,
                title = if (isIndo) "Release & Unduh APK" else "Release & APK Download",
                valueText = "GitHub Releases",
                onClick = { viewModel.setReleaseDialogOpen(true) },
                testTag = "settings_release_row"
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Dialogs
    if (showDpiDialog) {
        val displayInfo = viewModel.telemetryManager.getDisplayMetricsInfo()
        AlertDialog(
            onDismissRequest = { showDpiDialog = false },
            confirmButton = {
                TextButton(onClick = { showDpiDialog = false }) {
                    Text("OK", color = CrimsonRed, fontWeight = FontWeight.Bold)
                }
            },
            title = {
                Text("DPI & Screen Resolution", color = TextPrimary, fontWeight = FontWeight.Bold)
            },
            text = {
                Column {
                    Text(
                        text = "Ukuran Layar Terdeteksi:\n$displayInfo",
                        color = TextPrimary,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Catatan Non-Root:\nPerubahan DPI sistem secara universal dibatasi oleh Android OS untuk menjaga keamanan UI sistem. Anda dapat menyesuaikan skala tampilan melalui: Pengaturan Sistem > Opsi Pengembang > Lebar Terkecil (Smallest Width).",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }
            },
            containerColor = ObsidianCardElevated
        )
    }

    if (showDnsDialog) {
        AlertDialog(
            onDismissRequest = { showDnsDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDnsDialog = false
                        try {
                            val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "Buka Pengaturan > Jaringan & Internet > DNS Pribadi", Toast.LENGTH_LONG).show()
                        }
                    }
                ) {
                    Text("Buka Pengaturan DNS", color = CrimsonRed, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDnsDialog = false }) {
                    Text("Tutup", color = TextSecondary)
                }
            },
            title = {
                Text("Custom DNS (Private DNS)", color = TextPrimary, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    text = "Android 9+ mendukung DNS Pribadi (DoT) langsung dari sistem tanpa aplikasi latar belakang yang boros baterai.\n\nContoh DNS Game Rendah Latensi:\n• Cloudflare: 1dot1dot1dot1.cloudflare-dns.com\n• AdGuard Gaming: dns.adguard.com\n• Google DNS: dns.google",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
            },
            containerColor = ObsidianCardElevated
        )
    }

    if (showPrivacyDialog) {
        AlertDialog(
            onDismissRequest = { showPrivacyDialog = false },
            confirmButton = {
                TextButton(onClick = { showPrivacyDialog = false }) {
                    Text("Mengerti", color = CrimsonRed, fontWeight = FontWeight.Bold)
                }
            },
            title = {
                Text("Keamanan & Privasi Non-Root", color = TextPrimary, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    text = "OBSIDIA TOOLS dirancang 100% aman dan patuh pada pedoman Google Play & integritas sistem Android:\n\n" +
                            "• Tanpa Akses Root\n" +
                            "• Tanpa Modifikasi File APK Game atau Obfuscated Assets\n" +
                            "• Tanpa Spyware atau Pelacakan Data Pengguna\n" +
                            "• Hanya Membaca Sensor Resmi (BatteryManager, MemoryInfo, Display Refresh Rate)\n" +
                            "• Aman dari Risiko Banned Akun Game (Anti-Cheat Friendly)",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            },
            containerColor = ObsidianCardElevated
        )
    }

    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = { showAboutDialog = false },
            confirmButton = {
                TextButton(onClick = { showAboutDialog = false }) {
                    Text("Tutup", color = CrimsonRed, fontWeight = FontWeight.Bold)
                }
            },
            title = {
                Text("Tentang OBSIDIA TOOLS", color = TextPrimary, fontWeight = FontWeight.Bold)
            },
            text = {
                Column {
                    Text(
                        text = "OBSIDIA TOOLS v1.0.1",
                        color = CrimsonRed,
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Real Android Game Graphics Controller & Performance Monitor",
                        color = TextPrimary,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Built with Passion\nBy ZUCCHERO XANN",
                        color = MetallicSilver,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Fitur Utama:\n• Kontrol Preset Grafis Nyata\n• Real-Time Battery & Hardware Telemetry\n• Game Launcher & Per-Game Profiling\n• Non-Root, Ringan & Legal",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }
            },
            containerColor = ObsidianCardElevated
        )
    }

    if (showResetConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showResetConfirmDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showResetConfirmDialog = false
                        viewModel.resetAllAppPreferences()
                        Toast.makeText(context, if (isIndo) "Pengaturan berhasil di-reset!" else "Preferences restored to defaults!", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text(if (isIndo) "Reset Sekarang" else "Reset Now", color = CrimsonRed, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetConfirmDialog = false }) {
                    Text(if (isIndo) "Batal" else "Cancel", color = TextSecondary)
                }
            },
            title = {
                Text(
                    text = if (isIndo) "Reset Seluruh Pengaturan?" else "Reset All Preferences?",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = if (isIndo)
                        "Tindakan ini akan mengembalikan semua preset grafis, pengaturan game, dan preferensi aplikasi ke standar awal."
                    else
                        "This action will reset all graphics profiles, game presets, and application settings back to defaults.",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
            },
            containerColor = ObsidianCardElevated
        )
    }
}

@Composable
private fun SettingsRowCard(
    icon: ImageVector,
    title: String,
    valueText: String,
    onClick: () -> Unit,
    testTag: String
) {
    ObsidiaCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .background(ObsidianCardElevated, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MetallicSilver,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = valueText,
                    fontSize = 13.sp,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = MetallicSilverDark,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
