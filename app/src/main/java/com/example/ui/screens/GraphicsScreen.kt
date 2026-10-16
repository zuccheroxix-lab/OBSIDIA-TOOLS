package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.ApplyState
import com.example.data.model.GraphicsPresetType
import com.example.ui.components.ObsidiaCard
import com.example.ui.components.ObsidiaCrimsonButton
import com.example.ui.components.ObsidiaOutlinedBtn
import com.example.ui.components.StatusBadge
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.AmberWarningBg
import com.example.ui.theme.BurgundyDarker
import com.example.ui.theme.BurgundyDeep
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.EmeraldSuccessBg
import com.example.ui.theme.MetallicSilver
import com.example.ui.theme.MetallicSilverDark
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianBorderSubtle
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.ObsidianCardElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.NavigationTab
import com.example.ui.viewmodel.ObsidiaViewModel

@Composable
fun GraphicsScreen(
    viewModel: ObsidiaViewModel,
    modifier: Modifier = Modifier
) {
    val currentPreset by viewModel.currentPreset.collectAsState()
    val currentSettings by viewModel.currentSettings.collectAsState()
    val applyResult by viewModel.applyResult.collectAsState()
    val realtimeStats by viewModel.realtimeStats.collectAsState()
    val language by viewModel.language.collectAsState()
    val isIndo = language == AppLanguage.INDONESIAN

    var selectedSegment by remember { mutableIntStateOf(0) } // 0: Preset, 1: Manual, 2: Advanced

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            // Header: <- Graphics Control, subtitle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { viewModel.setTab(NavigationTab.HOME) },
                    modifier = Modifier.testTag("graphics_back_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = TextPrimary
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = "Graphics Control",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = if (isIndo) "Atur grafis game sesuai perangkatmu." else "Configure game graphics for your device.",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }
        }

        item {
            // Segment Switcher: Preset, Manual, Advanced
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(ObsidianCardElevated)
                    .border(1.dp, ObsidianBorder, RoundedCornerShape(12.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf(
                    if (isIndo) "Preset" else "Preset",
                    if (isIndo) "Manual" else "Manual",
                    if (isIndo) "Advanced" else "Advanced"
                ).forEachIndexed { index, title ->
                    val isSelected = selectedSegment == index
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) CrimsonRed else Color.Transparent)
                            .clickable { selectedSegment = index }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) TextPrimary else TextSecondary
                        )
                    }
                }
            }
        }

        if (selectedSegment == 0 || selectedSegment == 1) {
            item {
                Text(
                    text = if (isIndo) "Pilihan Preset" else "Preset Selection",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }

            // Preset Cards
            item {
                PresetItemCard(
                    title = if (isIndo) "Battery Saver" else "Battery Saver",
                    subtitle = if (isIndo) "Hemat baterai, suhu rendah (30 FPS)" else "Low battery drain, cool temp (30 FPS)",
                    icon = Icons.Default.Eco,
                    iconTint = EmeraldSuccess,
                    isSelected = currentPreset == GraphicsPresetType.BATTERY_SAVER,
                    onClick = { viewModel.selectPreset(GraphicsPresetType.BATTERY_SAVER) },
                    testTag = "preset_battery_saver"
                )
            }

            item {
                PresetItemCard(
                    title = if (isIndo) "Balanced" else "Balanced",
                    subtitle = if (isIndo) "Seimbang performa & kualitas (60 FPS)" else "Balanced performance & visuals (60 FPS)",
                    icon = Icons.Default.Scale,
                    iconTint = Color(0xFF00E5FF),
                    isSelected = currentPreset == GraphicsPresetType.BALANCED,
                    onClick = { viewModel.selectPreset(GraphicsPresetType.BALANCED) },
                    testTag = "preset_balanced"
                )
            }

            item {
                PresetItemCard(
                    title = if (isIndo) "Performance" else "Performance",
                    subtitle = if (isIndo) "FPS lebih stabil, latency rendah" else "Smoother FPS, low latency",
                    icon = Icons.Default.Bolt,
                    iconTint = CrimsonRed,
                    isSelected = currentPreset == GraphicsPresetType.PERFORMANCE,
                    onClick = { viewModel.selectPreset(GraphicsPresetType.PERFORMANCE) },
                    testTag = "preset_performance"
                )
            }

            item {
                PresetItemCard(
                    title = if (isIndo) "Custom" else "Custom",
                    subtitle = if (isIndo) "Atur sendiri sesuai kebutuhan hardware" else "Fine-tune manually for your hardware",
                    icon = Icons.Default.Settings,
                    iconTint = Color(0xFFA855F7),
                    isSelected = currentPreset == GraphicsPresetType.CUSTOM,
                    onClick = { viewModel.selectPreset(GraphicsPresetType.CUSTOM) },
                    testTag = "preset_custom"
                )
            }
        }

        // Detailed manual options
        item {
            Text(
                text = if (isIndo) "Pengaturan Grafis" else "Graphics Settings",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.padding(top = 6.dp)
            )
        }

        item {
            // Quality Selector
            SettingDropdownRow(
                label = if (isIndo) "Graphics Quality" else "Graphics Quality",
                currentValue = currentSettings.quality,
                options = listOf("Low", "Medium", "High", "Ultra"),
                onSelect = { viewModel.updateSetting { s -> s.copy(quality = it) } },
                testTag = "select_graphics_quality"
            )
        }

        item {
            // Resolution Scale
            SettingDropdownRow(
                label = if (isIndo) "Resolution Scale" else "Resolution Scale",
                currentValue = currentSettings.resolutionScale,
                options = listOf("75%", "100%", "125%"),
                onSelect = { viewModel.updateSetting { s -> s.copy(resolutionScale = it) } },
                testTag = "select_resolution_scale"
            )
        }

        item {
            // Frame Rate Target
            SettingDropdownRow(
                label = if (isIndo) "Frame Rate Target" else "Frame Rate Target",
                currentValue = currentSettings.frameRateTarget,
                options = listOf("30 FPS", "45 FPS", "60 FPS", "90 FPS", "120 FPS"),
                onSelect = { viewModel.updateSetting { s -> s.copy(frameRateTarget = it) } },
                testTag = "select_framerate_target"
            )
        }

        item {
            // Texture Quality
            SettingDropdownRow(
                label = if (isIndo) "Texture Quality" else "Texture Quality",
                currentValue = currentSettings.textureQuality,
                options = listOf("Low", "Medium", "High"),
                onSelect = { viewModel.updateSetting { s -> s.copy(textureQuality = it) } },
                testTag = "select_texture_quality"
            )
        }

        if (selectedSegment == 2) {
            item {
                // Shadow Quality (in Advanced)
                SettingDropdownRow(
                    label = if (isIndo) "Shadow Quality" else "Shadow Quality",
                    currentValue = currentSettings.shadowQuality,
                    options = listOf("Off", "Low", "Medium", "High"),
                    onSelect = { viewModel.updateSetting { s -> s.copy(shadowQuality = it) } },
                    testTag = "select_shadow_quality"
                )
            }

            item {
                // Anti-Aliasing (in Advanced)
                SettingDropdownRow(
                    label = if (isIndo) "Anti-Aliasing" else "Anti-Aliasing",
                    currentValue = currentSettings.antiAliasing,
                    options = listOf("Off", "2x MSAA", "4x MSAA"),
                    onSelect = { viewModel.updateSetting { s -> s.copy(antiAliasing = it) } },
                    testTag = "select_anti_aliasing"
                )
            }
        }

        // Apply Status Box & Comparison
        item {
            ApplyStatusCard(
                result = applyResult,
                maxHz = realtimeStats.refreshRateHz,
                isIndo = isIndo
            )
        }

        // Action Buttons: Apply Settings (Big Crimson) + Reset to Default
        item {
            ObsidiaCrimsonButton(
                text = if (applyResult.state == ApplyState.APPLYING) {
                    if (isIndo) "Menerapkan Profil..." else "Applying Profile..."
                } else {
                    if (isIndo) "-> Apply Settings" else "-> Apply Settings"
                },
                onClick = { viewModel.applyGraphicsSettings() },
                enabled = applyResult.state != ApplyState.APPLYING,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("apply_graphics_button")
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ObsidiaOutlinedBtn(
                    text = if (isIndo) "Reset to Default" else "Reset to Default",
                    onClick = { viewModel.resetGraphicsToDefault() },
                    icon = Icons.Default.RestartAlt,
                    modifier = Modifier.weight(1f),
                    testTag = "reset_graphics_button"
                )

                ObsidiaOutlinedBtn(
                    text = if (isIndo) "Save Preset" else "Save Preset",
                    onClick = { viewModel.applyGraphicsSettings() },
                    icon = Icons.Default.Save,
                    modifier = Modifier.weight(1f),
                    testTag = "save_preset_button"
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun PresetItemCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconTint: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    ObsidiaCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag),
        borderColor = if (isSelected) CrimsonRed else ObsidianBorder,
        backgroundColor = if (isSelected) BurgundyDeep.copy(alpha = 0.35f) else ObsidianCard,
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
                        tint = iconTint,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = subtitle,
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }
            }

            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(CrimsonRed, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = TextPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingDropdownRow(
    label: String,
    currentValue: String,
    options: List<String>,
    onSelect: (String) -> Unit,
    testTag: String
) {
    var expanded by remember { mutableStateOf(false) }

    ObsidiaCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )

            Box {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(ObsidianCardElevated)
                        .border(1.dp, ObsidianBorder, RoundedCornerShape(8.dp))
                        .clickable { expanded = true }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "$currentValue ▾",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(ObsidianCardElevated)
                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = option,
                                    color = if (option == currentValue) CrimsonRed else TextPrimary,
                                    fontWeight = if (option == currentValue) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            onClick = {
                                onSelect(option)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ApplyStatusCard(
    result: com.example.data.model.ApplyResult,
    maxHz: Int,
    isIndo: Boolean
) {
    val statusColor = when (result.state) {
        ApplyState.READY -> MetallicSilver
        ApplyState.APPLYING -> Color(0xFF00E5FF)
        ApplyState.APPLIED -> EmeraldSuccess
        ApplyState.FAILED -> CrimsonRed
        ApplyState.UNSUPPORTED -> AmberWarning
    }

    val statusBg = when (result.state) {
        ApplyState.READY -> ObsidianCardElevated
        ApplyState.APPLYING -> Color(0xFF002733)
        ApplyState.APPLIED -> EmeraldSuccessBg
        ApplyState.FAILED -> BurgundyDarker
        ApplyState.UNSUPPORTED -> AmberWarningBg
    }

    ObsidiaCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("apply_status_card"),
        borderColor = statusColor.copy(alpha = 0.5f),
        backgroundColor = statusBg
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    when (result.state) {
                        ApplyState.APPLYING -> CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = statusColor,
                            strokeWidth = 2.dp
                        )
                        ApplyState.APPLIED -> Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = null,
                            tint = statusColor,
                            modifier = Modifier.size(18.dp)
                        )
                        ApplyState.UNSUPPORTED -> Icon(
                            imageVector = Icons.Outlined.ErrorOutline,
                            contentDescription = null,
                            tint = statusColor,
                            modifier = Modifier.size(18.dp)
                        )
                        else -> Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = null,
                            tint = statusColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "STATUS: ${result.state.name}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = statusColor,
                        letterSpacing = 1.sp
                    )
                }

                Text(
                    text = "Screen: ${maxHz}Hz",
                    fontSize = 11.sp,
                    color = MetallicSilverDark,
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (result.message.isNotEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = result.message,
                    fontSize = 13.sp,
                    color = TextPrimary,
                    fontWeight = FontWeight.Medium
                )
            }

            if (result.details.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = result.details,
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }

            if (result.beforeSettings != null && result.afterSettings != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(ObsidianCard)
                        .border(1.dp, ObsidianBorderSubtle, RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Sebelumnya:", fontSize = 10.sp, color = TextMuted)
                        Text(text = "${result.beforeSettings.quality} (${result.beforeSettings.frameRateTarget})", fontSize = 11.sp, color = TextSecondary, fontWeight = FontWeight.Bold)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "Diterapkan Sekarang:", fontSize = 10.sp, color = TextMuted)
                        Text(text = "${result.afterSettings.quality} (${result.afterSettings.frameRateTarget})", fontSize = 11.sp, color = CrimsonRed, fontWeight = FontWeight.Bold)
                    }
                }
            }

            if (result.inGameGuide.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(ObsidianCard)
                        .border(1.dp, CrimsonRed.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Column {
                        Text(
                            text = if (isIndo) "Panduan Menu In-Game (Resmi):" else "In-Game Menu Setup Guide:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = CrimsonRed
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = result.inGameGuide,
                            fontSize = 11.sp,
                            color = TextPrimary,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            // Non-root legal boundary disclosure
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isIndo)
                    "Keamanan Non-Root: Profil grafis disimpan secara legal & aman tanpa mengubah APK atau melewati anti-cheat game."
                else
                    "Non-Root Safe: Profile saved legitimately without altering binary game files or bypassing anti-cheat systems.",
                fontSize = 10.sp,
                color = MetallicSilverDark
            )
        }
    }
}
