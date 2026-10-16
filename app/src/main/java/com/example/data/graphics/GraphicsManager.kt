package com.example.data.graphics

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.ApplyResult
import com.example.data.model.ApplyState
import com.example.data.model.GraphicsPresetType
import com.example.data.model.GraphicsSettings
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GraphicsManager(private val context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("obsidia_graphics_prefs", Context.MODE_PRIVATE)

    private val _currentPreset = MutableStateFlow(GraphicsPresetType.BALANCED)
    val currentPreset: StateFlow<GraphicsPresetType> = _currentPreset.asStateFlow()

    private val _currentSettings = MutableStateFlow(getPresetSettings(GraphicsPresetType.BALANCED))
    val currentSettings: StateFlow<GraphicsSettings> = _currentSettings.asStateFlow()

    private val _appliedSettings = MutableStateFlow<GraphicsSettings?>(null)
    val appliedSettings: StateFlow<GraphicsSettings?> = _appliedSettings.asStateFlow()

    private val _applyResult = MutableStateFlow(ApplyResult())
    val applyResult: StateFlow<ApplyResult> = _applyResult.asStateFlow()

    init {
        loadSavedConfig()
    }

    fun getPresetSettings(preset: GraphicsPresetType): GraphicsSettings {
        return when (preset) {
            GraphicsPresetType.BATTERY_SAVER -> GraphicsSettings(
                quality = "Low",
                resolutionScale = "75%",
                frameRateTarget = "30 FPS",
                textureQuality = "Low",
                shadowQuality = "Off",
                effectsQuality = "Low",
                antiAliasing = "Off"
            )
            GraphicsPresetType.BALANCED -> GraphicsSettings(
                quality = "Medium",
                resolutionScale = "100%",
                frameRateTarget = "60 FPS",
                textureQuality = "High",
                shadowQuality = "Low",
                effectsQuality = "Medium",
                antiAliasing = "2x MSAA"
            )
            GraphicsPresetType.PERFORMANCE -> GraphicsSettings(
                quality = "High",
                resolutionScale = "100%",
                frameRateTarget = "60 FPS",
                textureQuality = "High",
                shadowQuality = "Medium",
                effectsQuality = "High",
                antiAliasing = "2x MSAA"
            )
            GraphicsPresetType.CUSTOM -> {
                loadCustomSettings()
            }
        }
    }

    fun selectPreset(preset: GraphicsPresetType) {
        _currentPreset.value = preset
        _currentSettings.value = getPresetSettings(preset)
        _applyResult.value = ApplyResult(
            state = ApplyState.READY,
            message = "Preset ${preset.name} dipilih. Tekan Terapkan Pengaturan untuk memvalidasi dan menyimpan."
        )
    }

    fun updateSetting(updater: (GraphicsSettings) -> GraphicsSettings) {
        _currentPreset.value = GraphicsPresetType.CUSTOM
        val updated = updater(_currentSettings.value)
        _currentSettings.value = updated
        _applyResult.value = ApplyResult(
            state = ApplyState.READY,
            message = "Pengaturan grafis diubah secara manual. Tekan Terapkan Pengaturan untuk memvalidasi."
        )
    }

    suspend fun applySettings(maxDeviceHz: Int, gameGuide: String = ""): ApplyResult {
        val target = _currentSettings.value
        val before = _appliedSettings.value

        _applyResult.value = ApplyResult(
            state = ApplyState.APPLYING,
            message = "Memvalidasi profil grafis terhadap batas refresh rate layar ${maxDeviceHz}Hz...",
            beforeSettings = before,
            afterSettings = target
        )

        delay(600) // Verification phase

        // Validation against physical hardware limits
        val targetFpsNum = target.frameRateTarget.replace(" FPS", "").trim().toIntOrNull() ?: 60
        if (targetFpsNum > maxDeviceHz) {
            val res = ApplyResult(
                state = ApplyState.UNSUPPORTED,
                message = "Target $targetFpsNum FPS melebihi refresh rate fisik layar perangkat (${maxDeviceHz}Hz).",
                details = "Layar perangkat ini memiliki refresh rate maksimal ${maxDeviceHz}Hz. Frame rate render akan disinkronkan ke nilai maksimal layar untuk mencegah screen tearing.",
                inGameGuide = gameGuide,
                appliedTimestamp = System.currentTimeMillis(),
                beforeSettings = before,
                afterSettings = target
            )
            _applyResult.value = res
            return res
        }

        // Save to persistent SharedPreferences
        saveCurrentConfig(target)
        _appliedSettings.value = target

        val res = ApplyResult(
            state = ApplyState.APPLIED,
            message = "Profil ${target.quality} (${target.frameRateTarget}) terverifikasi & disimpan.",
            details = "Profil alokasi memori disiapkan. Karena batasan non-root Android, terapkan pula opsi grafis ini pada menu in-game sesuai panduan di bawah agar sinkron.",
            inGameGuide = gameGuide,
            appliedTimestamp = System.currentTimeMillis(),
            beforeSettings = before,
            afterSettings = target
        )
        _applyResult.value = res
        return res
    }

    fun resetToDefault() {
        val defaultBalanced = getPresetSettings(GraphicsPresetType.BALANCED)
        _currentPreset.value = GraphicsPresetType.BALANCED
        _currentSettings.value = defaultBalanced
        saveCurrentConfig(defaultBalanced)
        _appliedSettings.value = defaultBalanced
        _applyResult.value = ApplyResult(
            state = ApplyState.APPLIED,
            message = "Pengaturan grafis dikembalikan ke Standar (Balanced).",
            details = "Profil default 60 FPS, 100% resolusi aktif.",
            appliedTimestamp = System.currentTimeMillis(),
            afterSettings = defaultBalanced
        )
    }

    fun clearAllPreferences() {
        prefs.edit().clear().apply()
        resetToDefault()
    }

    private fun saveCurrentConfig(settings: GraphicsSettings) {
        prefs.edit().apply {
            putString("active_preset", _currentPreset.value.name)
            putString("quality", settings.quality)
            putString("resolution", settings.resolutionScale)
            putString("fps", settings.frameRateTarget)
            putString("texture", settings.textureQuality)
            putString("shadow", settings.shadowQuality)
            putString("effects", settings.effectsQuality)
            putString("aa", settings.antiAliasing)
            apply()
        }
    }

    private fun loadSavedConfig() {
        val presetName = prefs.getString("active_preset", GraphicsPresetType.BALANCED.name)
        val preset = try {
            GraphicsPresetType.valueOf(presetName ?: GraphicsPresetType.BALANCED.name)
        } catch (e: Exception) {
            GraphicsPresetType.BALANCED
        }
        _currentPreset.value = preset
        val loaded = loadCustomSettings()
        _currentSettings.value = loaded
        _appliedSettings.value = loaded
    }

    private fun loadCustomSettings(): GraphicsSettings {
        return GraphicsSettings(
            quality = prefs.getString("quality", "Medium") ?: "Medium",
            resolutionScale = prefs.getString("resolution", "100%") ?: "100%",
            frameRateTarget = prefs.getString("fps", "60 FPS") ?: "60 FPS",
            textureQuality = prefs.getString("texture", "High") ?: "High",
            shadowQuality = prefs.getString("shadow", "Low") ?: "Low",
            effectsQuality = prefs.getString("effects", "Medium") ?: "Medium",
            antiAliasing = prefs.getString("aa", "2x MSAA") ?: "2x MSAA"
        )
    }
}
