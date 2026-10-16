package com.example.ui.screens

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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.outlined.BatteryChargingFull
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DeviceThermostat
import androidx.compose.material.icons.outlined.Memory
import androidx.compose.material.icons.outlined.Monitor
import androidx.compose.material.icons.outlined.Sensors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.ui.components.CircularMetricGauge
import com.example.ui.components.ObsidiaCard
import com.example.ui.components.SparklineWaveChart
import com.example.ui.components.StatusBadge
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.EmeraldSuccessBg
import com.example.ui.theme.MetallicSilver
import com.example.ui.theme.MetallicSilverDark
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianBorderSubtle
import com.example.ui.theme.ObsidianCardElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.ObsidiaViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MonitorScreen(
    viewModel: ObsidiaViewModel,
    modifier: Modifier = Modifier
) {
    val stats by viewModel.realtimeStats.collectAsState()
    val deviceInfo by viewModel.deviceInfo.collectAsState()
    val language by viewModel.language.collectAsState()
    val isIndo = language == AppLanguage.INDONESIAN

    var selectedTab by remember { mutableIntStateOf(0) } // 0: CPU, 1: RAM, 2: Battery, 3: Temp

    val timeFormatted = remember(stats.lastUpdatedMillis) {
        SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(stats.lastUpdatedMillis))
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            // Header: Real-Time Monitor
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Speed,
                        contentDescription = null,
                        tint = CrimsonRed,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Real-Time Monitor",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = if (isIndo) "Data real-time perangkat & game." else "Real-time hardware & game telemetry.",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .background(EmeraldSuccessBg, RoundedCornerShape(12.dp))
                        .border(1.dp, EmeraldSuccess.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "LIVE $timeFormatted",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldSuccess
                    )
                }
            }
        }

        item {
            // Category Tabs: CPU, RAM, Battery, Temp
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(ObsidianCardElevated)
                    .border(1.dp, ObsidianBorder, RoundedCornerShape(12.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("CPU", "RAM", if (isIndo) "Baterai" else "Battery", "Temp").forEachIndexed { index, title ->
                    val isSelected = selectedTab == index
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) CrimsonRed else Color.Transparent)
                            .clickable { selectedTab = index }
                            .padding(vertical = 7.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) TextPrimary else TextSecondary
                        )
                    }
                }
            }
        }

        item {
            // Twin Gauges: CPU & RAM
            ObsidiaCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("monitor_twin_gauges")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // CPU Gauge
                    CircularMetricGauge(
                        valuePercent = stats.cpuUsagePercent,
                        label = "CPU",
                        subLabel = "${stats.cpuFreqGhz} GHz",
                        accentColor = CrimsonRed,
                        modifier = Modifier.testTag("cpu_gauge")
                    )

                    Box(
                        modifier = Modifier
                            .height(80.dp)
                            .width(1.dp)
                            .background(ObsidianBorderSubtle)
                    )

                    // RAM Gauge
                    CircularMetricGauge(
                        valuePercent = stats.ramUsagePercent,
                        label = "RAM",
                        subLabel = "${stats.usedRamGb} GB / ${stats.totalRamGb} GB",
                        accentColor = Color(0xFF00E5FF),
                        modifier = Modifier.testTag("ram_gauge")
                    )
                }
            }
        }

        item {
            // Battery & FPS Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Battery Card
                ObsidiaCard(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("monitor_battery_card")
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = if (isIndo) "Baterai" else "Battery",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextSecondary
                            )
                            Icon(
                                imageVector = Icons.Outlined.BatteryChargingFull,
                                contentDescription = null,
                                tint = EmeraldSuccess,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "${stats.batteryPercent}%",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = TextPrimary
                        )
                        Text(
                            text = "${stats.batteryTempCelsius} °C",
                            fontSize = 12.sp,
                            color = EmeraldSuccess,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // FPS Card
                ObsidiaCard(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("monitor_fps_card")
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "FPS",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextSecondary
                            )
                            Box(
                                modifier = Modifier
                                    .background(Color(0xFF002733), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "Real-time",
                                    fontSize = 10.sp,
                                    color = Color(0xFF00E5FF),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "${stats.displayPacingFps}",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = TextPrimary
                        )
                        Text(
                            text = if (isIndo) "Pacing Cadence Layar" else "Display Pacing Cadence",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }
                }
            }
        }

        item {
            // CPU Usage Historical Wave Chart
            ObsidiaCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("monitor_cpu_chart_card")
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isIndo) "Grafik Beban CPU" else "CPU Usage Wave",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = if (stats.cpuUsagePercent != null) "${stats.cpuUsagePercent}%" else "N/A",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (stats.cpuUsagePercent != null) CrimsonRed else MetallicSilverDark
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    SparklineWaveChart(
                        points = stats.cpuHistory,
                        lineColor = CrimsonRed
                    )
                }
            }
        }

        item {
            // Refresh Rate & Device Temp pills
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ObsidiaCard(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .background(ObsidianCardElevated, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Monitor,
                                contentDescription = null,
                                tint = MetallicSilver,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Refresh Rate",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                            Text(
                                text = "${stats.refreshRateHz} Hz",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                    }
                }

                ObsidiaCard(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .background(ObsidianCardElevated, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.DeviceThermostat,
                                contentDescription = null,
                                tint = CrimsonRed,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isIndo) "Suhu Baterai" else "Battery Temp",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                            Text(
                                text = "${stats.batteryTempCelsius} °C",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                    }
                }
            }
        }

        item {
            // Source Verification Note
            ObsidiaCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("data_source_verification_card"),
                borderColor = ObsidianBorderSubtle,
                backgroundColor = ObsidianCardElevated
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Sensors,
                        contentDescription = null,
                        tint = EmeraldSuccess,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = if (isIndo) "Sumber Data: ${stats.dataSourceLabel}" else "Data Source: ${stats.dataSourceLabel}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                        Text(
                            text = if (isIndo)
                                "Tanpa angka rekayasa. Diperbarui setiap 1000ms secara aman tanpa root."
                            else
                                "No artificial metrics. Updated every 1000ms safely without root.",
                            fontSize = 10.sp,
                            color = MetallicSilverDark
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
