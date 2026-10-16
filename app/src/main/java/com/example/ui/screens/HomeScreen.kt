package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.BatteryChargingFull
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Memory
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.AppLanguage
import com.example.ui.components.ObsidiaCard
import com.example.ui.components.ObsidiaCrimsonButton
import com.example.ui.components.StatusBadge
import com.example.ui.theme.BurgundyDeep
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.EmeraldSuccessBg
import com.example.ui.theme.MetallicSilver
import com.example.ui.theme.MetallicSilverDark
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianCardElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.NavigationTab
import com.example.ui.viewmodel.ObsidiaViewModel

@Composable
fun HomeScreen(
    viewModel: ObsidiaViewModel,
    modifier: Modifier = Modifier
) {
    val deviceInfo by viewModel.deviceInfo.collectAsState()
    val realtimeStats by viewModel.realtimeStats.collectAsState()
    val isOptimizing by viewModel.isOptimizing.collectAsState()
    val optimizeMessage by viewModel.optimizeMessage.collectAsState()
    val language by viewModel.language.collectAsState()
    val isIndo = language == AppLanguage.INDONESIAN

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Top Header: Crown icon, OBSIDIA TOOLS, v1.0.0
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.WorkspacePremium,
                        contentDescription = "Premium Badge",
                        tint = Color(0xFFFFB300),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "OBSIDIA",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = TextPrimary,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "TOOLS",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = CrimsonRed,
                        letterSpacing = 1.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(ObsidianCardElevated)
                        .border(1.dp, ObsidianBorder, RoundedCornerShape(8.dp))
                        .clickable { viewModel.setReleaseDialogOpen(true) }
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "v1.0.0",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MetallicSilver
                    )
                }
            }
        }

        item {
            // Central Obsidian Emblem Logo
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            Brush.radialGradient(
                                colors = listOf(BurgundyDeep.copy(alpha = 0.5f), Color.Transparent)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_obsidia_logo),
                        contentDescription = "Obsidia Logo",
                        modifier = Modifier
                            .size(90.dp)
                            .testTag("home_obsidia_logo"),
                        contentScale = ContentScale.Fit
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (isIndo) "Optimalkan Pengalaman Game Anda" else "Optimize Your Game Experience",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSecondary
                )
            }
        }

        item {
            // Device Info Card
            ObsidiaCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("device_info_card")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .background(ObsidianCardElevated, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.PhoneAndroid,
                                contentDescription = null,
                                tint = MetallicSilver,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "${deviceInfo.brand} ${deviceInfo.model}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "Android ${deviceInfo.androidVersion} (API ${deviceInfo.sdkInt})",
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                        }
                    }

                    StatusBadge(
                        text = if (isIndo) "Didukung" else "Supported",
                        color = EmeraldSuccess,
                        bgColor = EmeraldSuccessBg
                    )
                }
            }
        }

        item {
            // Quick Stat 3-Column Box (CPU, RAM, Battery)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // CPU Box
                ObsidiaCard(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("home_cpu_stat")
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Memory,
                                contentDescription = null,
                                tint = CrimsonRed,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "CPU",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextSecondary
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = deviceInfo.cpuModel,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            maxLines = 1
                        )
                        Text(
                            text = "${deviceInfo.cpuCores} Cores",
                            fontSize = 11.sp,
                            color = MetallicSilverDark
                        )
                    }
                }

                // RAM Box
                ObsidiaCard(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("home_ram_stat")
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Speed,
                                contentDescription = null,
                                tint = Color(0xFF00E5FF),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "RAM",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextSecondary
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "${realtimeStats.availableRamGb} GB",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = if (isIndo) "Tersedia" else "Available",
                            fontSize = 11.sp,
                            color = MetallicSilverDark
                        )
                    }
                }

                // Battery Box
                ObsidiaCard(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("home_battery_stat")
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.BatteryChargingFull,
                                contentDescription = null,
                                tint = EmeraldSuccess,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isIndo) "Baterai" else "Battery",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextSecondary
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "${realtimeStats.batteryPercent}%",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "${realtimeStats.batteryTempCelsius}°C",
                            fontSize = 11.sp,
                            color = MetallicSilverDark
                        )
                    }
                }
            }
        }

        item {
            // Big Red Quick Optimize Action Button
            ObsidiaCrimsonButton(
                text = if (isOptimizing) {
                    if (isIndo) "Mengoptimalkan..." else "Optimizing..."
                } else {
                    if (isIndo) "Quick Optimize >" else "Quick Optimize >"
                },
                onClick = { viewModel.runQuickOptimize() },
                enabled = !isOptimizing,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("quick_optimize_button"),
                icon = if (isOptimizing) null else Icons.Filled.Speed
            )
        }

        if (optimizeMessage != null) {
            item {
                ObsidiaCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("optimize_feedback_card"),
                    borderColor = CrimsonRed.copy(alpha = 0.5f),
                    backgroundColor = BurgundyDeep.copy(alpha = 0.4f)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isOptimizing) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = CrimsonRed,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,
                                contentDescription = null,
                                tint = EmeraldSuccess,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = optimizeMessage ?: "",
                            fontSize = 13.sp,
                            color = TextPrimary,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        item {
            // Quick Feature Shortcuts Section
            Text(
                text = if (isIndo) "Akses Cepat" else "Quick Access",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        item {
            // Graphics Control Shortcut
            ObsidiaCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("shortcut_graphics"),
                onClick = { viewModel.setTab(NavigationTab.GRAPHICS) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(ObsidianCardElevated, RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Tune,
                                contentDescription = null,
                                tint = CrimsonRed,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Graphics Control",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = if (isIndo) "Preset, FPS & Kualitas Visual" else "Preset, FPS & Visual Quality",
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = MetallicSilver
                    )
                }
            }
        }

        item {
            // Real-Time Monitor Shortcut
            ObsidiaCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("shortcut_monitor"),
                onClick = { viewModel.setTab(NavigationTab.MONITOR) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(ObsidianCardElevated, RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Speed,
                                contentDescription = null,
                                tint = Color(0xFF00E5FF),
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Real-Time Monitor",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = if (isIndo) "CPU, RAM, Baterai & Real FPS" else "CPU, RAM, Battery & Real FPS",
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = MetallicSilver
                    )
                }
            }
        }

        item {
            // Game Management Shortcut
            ObsidiaCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("shortcut_games"),
                onClick = { viewModel.setTab(NavigationTab.GAMES) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(ObsidianCardElevated, RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.SportsEsports,
                                contentDescription = null,
                                tint = Color(0xFFA855F7),
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Game Management",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = if (isIndo) "Daftar Game & Launcher Siap Pakai" else "Game List & Ready-to-Play Launcher",
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = MetallicSilver
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
