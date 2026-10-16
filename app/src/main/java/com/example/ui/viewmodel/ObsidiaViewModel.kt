package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.game.GameManager
import com.example.data.graphics.GraphicsManager
import com.example.data.model.AppLanguage
import com.example.data.model.ApplyResult
import com.example.data.model.DeviceInfo
import com.example.data.model.GameTarget
import com.example.data.model.GraphicsPresetType
import com.example.data.model.GraphicsSettings
import com.example.data.model.RealtimeStats
import com.example.data.telemetry.TelemetryManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

enum class NavigationTab {
    HOME,
    GRAPHICS,
    MONITOR,
    GAMES,
    SETTINGS
}

class ObsidiaViewModel(application: Application) : AndroidViewModel(application) {

    val telemetryManager = TelemetryManager(application)
    val gameManager = GameManager(application)
    val graphicsManager = GraphicsManager(application)

    private val _deviceInfo = MutableStateFlow(telemetryManager.getDeviceInfo())
    val deviceInfo: StateFlow<DeviceInfo> = _deviceInfo.asStateFlow()

    val realtimeStats: StateFlow<RealtimeStats> = telemetryManager.realtimeStats
    val currentPreset: StateFlow<GraphicsPresetType> = graphicsManager.currentPreset
    val currentSettings: StateFlow<GraphicsSettings> = graphicsManager.currentSettings
    val appliedSettings: StateFlow<GraphicsSettings?> = graphicsManager.appliedSettings
    val applyResult: StateFlow<ApplyResult> = graphicsManager.applyResult
    val gameList: StateFlow<List<GameTarget>> = gameManager.gameList

    private val _selectedGame = MutableStateFlow<GameTarget?>(null)
    val selectedGame: StateFlow<GameTarget?> = _selectedGame.asStateFlow()

    private val _currentTab = MutableStateFlow(NavigationTab.HOME)
    val currentTab: StateFlow<NavigationTab> = _currentTab.asStateFlow()

    private val _isLauncherOpen = MutableStateFlow(false)
    val isLauncherOpen: StateFlow<Boolean> = _isLauncherOpen.asStateFlow()

    private val _isReleaseDialogOpen = MutableStateFlow(false)
    val isReleaseDialogOpen: StateFlow<Boolean> = _isReleaseDialogOpen.asStateFlow()

    private val _isOptimizing = MutableStateFlow(false)
    val isOptimizing: StateFlow<Boolean> = _isOptimizing.asStateFlow()

    private val _optimizeMessage = MutableStateFlow<String?>(null)
    val optimizeMessage: StateFlow<String?> = _optimizeMessage.asStateFlow()

    private val _language = MutableStateFlow(AppLanguage.INDONESIAN)
    val language: StateFlow<AppLanguage> = _language.asStateFlow()

    private val _cacheSizeBytes = MutableStateFlow(0L)
    val cacheSizeBytes: StateFlow<Long> = _cacheSizeBytes.asStateFlow()

    private var monitorJob: Job? = null

    init {
        viewModelScope.launch {
            val games = gameManager.refreshGames()
            if (games.isNotEmpty()) {
                _selectedGame.value = games.first()
            }
            _cacheSizeBytes.value = telemetryManager.calculateCacheSizeBytes()
        }
        startMonitoring()
    }

    fun startMonitoring() {
        telemetryManager.startFramePacer()
        if (monitorJob?.isActive == true) return
        monitorJob = viewModelScope.launch {
            while (isActive) {
                telemetryManager.sampleRealtimeTelemetry()
                delay(1000L)
            }
        }
    }

    fun pauseMonitoring() {
        telemetryManager.stopFramePacer()
        monitorJob?.cancel()
        monitorJob = null
    }

    fun setTab(tab: NavigationTab) {
        _currentTab.value = tab
    }

    fun selectGame(game: GameTarget) {
        _selectedGame.value = game
    }

    fun openLauncherForGame(game: GameTarget) {
        _selectedGame.value = game
        _isLauncherOpen.value = true
    }

    fun closeLauncher() {
        _isLauncherOpen.value = false
    }

    fun setReleaseDialogOpen(open: Boolean) {
        _isReleaseDialogOpen.value = open
    }

    fun setLanguage(lang: AppLanguage) {
        _language.value = lang
    }

    fun selectPreset(preset: GraphicsPresetType) {
        graphicsManager.selectPreset(preset)
    }

    fun updateSetting(updater: (GraphicsSettings) -> GraphicsSettings) {
        graphicsManager.updateSetting(updater)
    }

    fun applyGraphicsSettings() {
        viewModelScope.launch {
            val maxHz = realtimeStats.value.refreshRateHz.coerceAtLeast(60)
            val guide = _selectedGame.value?.inGameGuide ?: ""
            graphicsManager.applySettings(maxHz, guide)
            _selectedGame.value?.let { current ->
                gameManager.saveGamePreset(current.id, graphicsManager.currentSettings.value)
            }
        }
    }

    fun resetGraphicsToDefault() {
        graphicsManager.resetToDefault()
        _selectedGame.value?.let { current ->
            gameManager.saveGamePreset(current.id, graphicsManager.currentSettings.value)
        }
    }

    fun resetAllAppPreferences() {
        graphicsManager.clearAllPreferences()
        gameManager.resetAllGamePresets()
        _selectedGame.value = gameList.value.firstOrNull()
        _optimizeMessage.value = "Seluruh preferensi dan preset game berhasil di-reset ke standar pabrik."
    }

    fun runQuickOptimize() {
        viewModelScope.launch {
            _isOptimizing.value = true
            _optimizeMessage.value = "Memeriksa suhu baterai dan batas termal hardware..."
            delay(400)

            val currentTemp = realtimeStats.value.batteryTempCelsius
            val thermalWarning = if (currentTemp >= 42.0f) {
                "⚠️ Suhu tinggi ($currentTemp°C)! Disarankan istirahatkan ponsel."
            } else {
                "Suhu aman ($currentTemp°C)."
            }

            _optimizeMessage.value = "Menghapus cache aplikasi & merefresh pacer display..."
            delay(500)
            val cleared = telemetryManager.clearApplicationCache()
            _cacheSizeBytes.value = telemetryManager.calculateCacheSizeBytes()
            System.gc()

            _optimizeMessage.value = "Optimalisasi Selesai! Cache dibersihkan (${cleared / 1024} KB). $thermalWarning Profil siap dipakai."
            _isOptimizing.value = false
        }
    }

    fun clearAppCache() {
        val cleared = telemetryManager.clearApplicationCache()
        _cacheSizeBytes.value = telemetryManager.calculateCacheSizeBytes()
        _optimizeMessage.value = "Cache aplikasi sebesar ${cleared / 1024} KB berhasil dibersihkan."
    }

    fun dismissOptimizeMessage() {
        _optimizeMessage.value = null
    }

    fun launchGame(game: GameTarget): Result<String> {
        return gameManager.launchGame(game.packageName)
    }

    override fun onCleared() {
        super.onCleared()
        pauseMonitoring()
    }
}
