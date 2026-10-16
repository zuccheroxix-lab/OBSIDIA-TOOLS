package com.example

import com.example.data.model.DeviceInfo
import com.example.data.model.GameTarget
import com.example.data.model.GraphicsPresetType
import com.example.data.model.GraphicsSettings
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun deviceInfo_ramCalculation_isAccurate() {
        val totalBytes = 4L * 1024L * 1024L * 1024L // 4 GB
        val availBytes = 1L * 1024L * 1024L * 1024L // 1 GB
        val device = DeviceInfo(
            model = "Pixel 7",
            manufacturer = "Google",
            brand = "Google",
            androidVersion = "14",
            sdkInt = 34,
            cpuModel = "Google Tensor G2",
            cpuCores = 8,
            totalRamBytes = totalBytes,
            availableRamBytes = availBytes
        )

        assertEquals(4.0f, device.totalRamGb, 0.01f)
        assertEquals(1.0f, device.availableRamGb, 0.01f)
        assertEquals(3.0f, device.usedRamGb, 0.01f)
        assertEquals(75, device.ramUsagePercent)
    }

    @Test
    fun defaultGraphicsSettings_isValid() {
        val settings = GraphicsSettings()
        assertEquals("High", settings.quality)
        assertEquals("60 FPS", settings.frameRateTarget)
        assertEquals("100%", settings.resolutionScale)
    }

    @Test
    fun gameTarget_initialProperties_areValid() {
        val game = GameTarget(
            id = "ff_test",
            name = "Free Fire",
            packageName = "com.dts.freefireth",
            developer = "Garena",
            isInstalled = false,
            compatibility = "Compatible",
            inGameGuide = "Set Ultra & High FPS"
        )

        assertFalse(game.isInstalled)
        assertEquals("Compatible", game.compatibility)
        assertTrue(game.inGameGuide.isNotEmpty())
    }
}
