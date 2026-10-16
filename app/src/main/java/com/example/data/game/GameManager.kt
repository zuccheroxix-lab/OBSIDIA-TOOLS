package com.example.data.game

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import com.example.data.model.GameTarget
import com.example.data.model.GraphicsSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class GameManager(private val context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("obsidia_game_presets", Context.MODE_PRIVATE)

    private val _gameList = MutableStateFlow<List<GameTarget>>(emptyList())
    val gameList: StateFlow<List<GameTarget>> = _gameList.asStateFlow()

    private val catalogTargets = listOf(
        GameTarget(
            id = "free_fire",
            name = "Free Fire",
            packageName = "com.dts.freefireth",
            developer = "Garena",
            isInstalled = false,
            compatibility = "Compatible",
            playCategory = "Battle Royale",
            inGameGuide = "Buka Pengaturan Game > Tampilan:\n• Grafis: Ultra / Standar\n• High FPS: Aktif (On)\n• Resolusi: Tinggi\n• Bayangan: Nonaktif (untuk FPS lebih stabil)"
        ),
        GameTarget(
            id = "pubg_mobile",
            name = "PUBG MOBILE",
            packageName = "com.tencent.ig",
            developer = "Level Infinite",
            isInstalled = false,
            compatibility = "Compatible",
            playCategory = "Battle Royale",
            inGameGuide = "Buka Pengaturan > Grafis & Audio:\n• Grafis: Smooth (Halus)\n• Laju Bingkai: Ekstrem (60 FPS) / 90 FPS\n• Anti-Aliasing: Tutup / 2X\n• Sesuaikan Grafis Otomatis: Nonaktifkan"
        ),
        GameTarget(
            id = "mobile_legends",
            name = "Mobile Legends",
            packageName = "com.mobile.legends",
            developer = "Moonton",
            isInstalled = false,
            compatibility = "Compatible",
            playCategory = "MOBA",
            inGameGuide = "Buka Pengaturan > Dasar:\n• Grafis: Sedang / Tinggi\n• Refresh Rate: Tinggi / Super (Ultra jika didukung)\n• Bayangan: Nonaktif\n• Outline: Nonaktif"
        ),
        GameTarget(
            id = "genshin_impact",
            name = "Genshin Impact",
            packageName = "com.miHoYo.GenshinImpact",
            developer = "HoYoverse",
            isInstalled = false,
            compatibility = "Limited",
            playCategory = "Open World RPG",
            inGameGuide = "Buka Pengaturan > Grafis:\n• FPS Target: 60 FPS\n• Resolusi Render: Sedang\n• Kualitas Bayangan: Rendah\n• Efek Visual: Sedang\n• Motion Blur: Tutup"
        ),
        GameTarget(
            id = "cod_mobile",
            name = "Call of Duty Mobile",
            packageName = "com.activision.callofduty.shooter",
            developer = "Activision",
            isInstalled = false,
            compatibility = "Compatible",
            playCategory = "FPS",
            inGameGuide = "Buka Pengaturan > Audio & Grafis:\n• Kualitas Grafis: Rendah / Sedang\n• Frame Rate: Maksimal / Ultra\n• Kedalaman Bidang (DOF): Nonaktif\n• Bloom & Ragdoll: Nonaktif"
        ),
        GameTarget(
            id = "wild_rift",
            name = "Wild Rift",
            packageName = "com.riotgames.league.wildrift",
            developer = "Riot Games",
            isInstalled = false,
            compatibility = "Compatible",
            playCategory = "MOBA",
            inGameGuide = "Buka Pengaturan > Grafis:\n• 60 FPS / 90 FPS / 120 FPS: Aktif\n• Kualitas Grafis: Sedang\n• Efek Visual: Rendah\n• Resolusi: Tinggi"
        ),
        GameTarget(
            id = "minecraft",
            name = "Minecraft",
            packageName = "com.mojang.minecraftpe",
            developer = "Mojang",
            isInstalled = false,
            compatibility = "Compatible",
            playCategory = "Sandbox",
            inGameGuide = "Buka Pengaturan > Video:\n• Render Distance: 8 - 12 Chunks\n• Fancy Graphics: Matikan jika FPS drop\n• Smooth Lighting: Aktif"
        ),
        GameTarget(
            id = "roblox",
            name = "Roblox",
            packageName = "com.roblox.client",
            developer = "Roblox Corp",
            isInstalled = false,
            compatibility = "Compatible",
            playCategory = "Metaverse",
            inGameGuide = "Buka Pengaturan Roblox saat bermain:\n• Graphics Mode: Manual\n• Graphics Quality: Slider 3 - 5 bar untuk performa optimal"
        )
    )

    suspend fun refreshGames(): List<GameTarget> = withContext(Dispatchers.IO) {
        val pm = context.packageManager
        val combined = mutableListOf<GameTarget>()

        // 1. Process standard catalog and check real installation state
        for (target in catalogTargets) {
            val (installed, verName) = getPackageInstallInfo(pm, target.packageName)
            val savedPreset = loadGamePreset(target.id)
            combined.add(
                target.copy(
                    isInstalled = installed,
                    versionName = verName,
                    customSettings = savedPreset
                )
            )
        }

        // 2. Discover additional real games installed on user's device
        try {
            val mainIntent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
            val activities = pm.queryIntentActivities(mainIntent, 0)
            val existingPackages = catalogTargets.map { it.packageName }.toSet()

            for (resolve in activities) {
                val appInfo = resolve.activityInfo?.applicationInfo ?: continue
                val pkgName = appInfo.packageName

                // Filter out non-games and already cataloged
                if (existingPackages.contains(pkgName)) continue
                if (pkgName.startsWith("com.android.") || pkgName.startsWith("com.google.android.")) continue

                val isGame = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    appInfo.category == ApplicationInfo.CATEGORY_GAME
                } else {
                    (appInfo.flags and ApplicationInfo.FLAG_IS_GAME) != 0
                }

                if (isGame) {
                    val appLabel = pm.getApplicationLabel(appInfo).toString()
                    val verName = getPackageInstallInfo(pm, pkgName).second
                    val gameId = "installed_${pkgName.replace(".", "_")}"
                    val savedPreset = loadGamePreset(gameId)

                    combined.add(
                        GameTarget(
                            id = gameId,
                            name = appLabel,
                            packageName = pkgName,
                            developer = "Installed Game",
                            isInstalled = true,
                            compatibility = "Compatible",
                            playCategory = "Installed",
                            versionName = verName,
                            customSettings = savedPreset,
                            inGameGuide = "Buka Pengaturan Game di $appLabel untuk menyesuaikan opsi grafis dan resolusi sesuai kemampuan perangkat."
                        )
                    )
                }
            }
        } catch (e: Exception) {
            // Ignore querying errors
        }

        _gameList.value = combined
        combined
    }

    private fun getPackageInstallInfo(pm: PackageManager, packageName: String): Pair<Boolean, String?> {
        return try {
            val pInfo = pm.getPackageInfo(packageName, 0)
            Pair(true, pInfo.versionName)
        } catch (e: Exception) {
            val launchIntent = pm.getLaunchIntentForPackage(packageName)
            Pair(launchIntent != null, null)
        }
    }

    fun launchGame(packageName: String): Result<String> {
        val pm = context.packageManager
        val launchIntent = pm.getLaunchIntentForPackage(packageName)
        return if (launchIntent != null) {
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            try {
                context.startActivity(launchIntent)
                Result.success("Game launched successfully")
            } catch (e: Exception) {
                Result.failure(Exception("Gagal membuka game: ${e.message}"))
            }
        } else {
            Result.failure(Exception("Game dengan paket $packageName belum terpasang di perangkat ini."))
        }
    }

    fun saveGamePreset(gameId: String, settings: GraphicsSettings) {
        prefs.edit().apply {
            putString("${gameId}_quality", settings.quality)
            putString("${gameId}_resolution", settings.resolutionScale)
            putString("${gameId}_fps", settings.frameRateTarget)
            putString("${gameId}_texture", settings.textureQuality)
            putString("${gameId}_shadow", settings.shadowQuality)
            putString("${gameId}_effects", settings.effectsQuality)
            putString("${gameId}_aa", settings.antiAliasing)
            apply()
        }
        _gameList.value = _gameList.value.map {
            if (it.id == gameId) it.copy(customSettings = settings) else it
        }
    }

    fun resetAllGamePresets() {
        prefs.edit().clear().apply()
        _gameList.value = _gameList.value.map {
            it.copy(customSettings = GraphicsSettings())
        }
    }

    private fun loadGamePreset(gameId: String): GraphicsSettings {
        val quality = prefs.getString("${gameId}_quality", "High") ?: "High"
        val resolution = prefs.getString("${gameId}_resolution", "100%") ?: "100%"
        val fps = prefs.getString("${gameId}_fps", "60 FPS") ?: "60 FPS"
        val texture = prefs.getString("${gameId}_texture", "High") ?: "High"
        val shadow = prefs.getString("${gameId}_shadow", "Medium") ?: "Medium"
        val effects = prefs.getString("${gameId}_effects", "High") ?: "High"
        val aa = prefs.getString("${gameId}_aa", "2x MSAA") ?: "2x MSAA"

        return GraphicsSettings(
            quality = quality,
            resolutionScale = resolution,
            frameRateTarget = fps,
            textureQuality = texture,
            shadowQuality = shadow,
            effectsQuality = effects,
            antiAliasing = aa
        )
    }
}
