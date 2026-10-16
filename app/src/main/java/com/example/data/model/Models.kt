package com.example.data.model

data class DeviceInfo(
    val model: String,
    val manufacturer: String,
    val brand: String,
    val androidVersion: String,
    val sdkInt: Int,
    val cpuModel: String,
    val cpuCores: Int,
    val totalRamBytes: Long,
    val availableRamBytes: Long,
    val isSupported: Boolean = true
) {
    val totalRamGb: Float get() = totalRamBytes / (1024f * 1024f * 1024f)
    val availableRamGb: Float get() = availableRamBytes / (1024f * 1024f * 1024f)
    val usedRamGb: Float get() = (totalRamGb - availableRamGb).coerceAtLeast(0f)
    val ramUsagePercent: Int get() = if (totalRamBytes > 0) (((totalRamBytes - availableRamBytes).toDouble() / totalRamBytes) * 100).toInt().coerceIn(0, 100) else 0
}

data class BatteryTelemetry(
    val levelPercent: Int = 100,
    val tempCelsius: Float = 30.0f,
    val isCharging: Boolean = false,
    val health: String = "Good",
    val voltageMv: Int = 4000
)

data class RealtimeStats(
    val cpuUsagePercent: Int? = null, // null if restricted by Android SELinux security policy
    val cpuFreqGhz: Float = 2.0f,
    val ramUsagePercent: Int = 0,
    val usedRamGb: Float = 0f,
    val totalRamGb: Float = 0f,
    val availableRamGb: Float = 0f,
    val batteryPercent: Int = 0,
    val batteryTempCelsius: Float = 0f,
    val refreshRateHz: Int = 60,
    val displayPacingFps: Int = 60,
    val cpuHistory: List<Int> = emptyList(),
    val lastUpdatedMillis: Long = System.currentTimeMillis(),
    val dataSourceLabel: String = "Android BatteryManager, MemoryInfo & Display Pacer"
) {
    val isCpuUsageAvailable: Boolean get() = cpuUsagePercent != null && cpuUsagePercent >= 0
}

enum class GraphicsPresetType {
    BATTERY_SAVER,
    BALANCED,
    PERFORMANCE,
    CUSTOM
}

data class GraphicsSettings(
    val quality: String = "High",           // Low, Medium, High, Ultra
    val resolutionScale: String = "100%",    // 75%, 100%, 125%
    val frameRateTarget: String = "60 FPS",  // 30 FPS, 45 FPS, 60 FPS, 90 FPS, 120 FPS
    val textureQuality: String = "High",     // Low, Medium, High
    val shadowQuality: String = "Medium",    // Off, Low, Medium, High
    val effectsQuality: String = "High",     // Low, Medium, High
    val antiAliasing: String = "2x MSAA"     // Off, 2x MSAA, 4x MSAA
)

enum class ApplyState {
    READY,
    APPLYING,
    APPLIED,
    FAILED,
    UNSUPPORTED
}

data class ApplyResult(
    val state: ApplyState = ApplyState.READY,
    val message: String = "",
    val details: String = "",
    val inGameGuide: String = "",
    val appliedTimestamp: Long = 0L,
    val beforeSettings: GraphicsSettings? = null,
    val afterSettings: GraphicsSettings? = null
)

data class GameTarget(
    val id: String,
    val name: String,
    val packageName: String,
    val developer: String,
    val isInstalled: Boolean,
    val compatibility: String, // "Compatible", "Limited", "Unsupported"
    val customSettings: GraphicsSettings = GraphicsSettings(),
    val playCategory: String = "Action",
    val versionName: String? = null,
    val inGameGuide: String = ""
)

enum class AppLanguage {
    INDONESIAN,
    ENGLISH
}

data class AppConfig(
    val language: AppLanguage = AppLanguage.INDONESIAN,
    val themeMode: String = "Dark Obsidian (Default)",
    val refreshRateMs: Long = 1000L,
    val isMonitoringPausedInBackground: Boolean = true
)
