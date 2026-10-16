package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.OpenInNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.AppLanguage
import com.example.data.model.GameTarget
import com.example.ui.components.ObsidiaCard
import com.example.ui.components.ObsidiaCrimsonButton
import com.example.ui.components.StatusBadge
import com.example.ui.theme.BurgundyDarker
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
fun GameLauncherScreen(
    viewModel: ObsidiaViewModel,
    game: GameTarget,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.closeLauncher()
    }

    val context = LocalContext.current
    val currentSettings by viewModel.currentSettings.collectAsState()
    val language by viewModel.language.collectAsState()
    val isIndo = language == AppLanguage.INDONESIAN

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            // Header: <- Game Launcher
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { viewModel.closeLauncher() },
                    modifier = Modifier.testTag("launcher_back_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = TextPrimary
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Game Launcher",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }
        }

        item {
            // Hero Game Artwork Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, ObsidianBorder, RoundedCornerShape(16.dp))
                    .testTag("launcher_hero_banner")
            ) {
                Image(
                    painter = painterResource(id = R.drawable.game_banner_hero),
                    contentDescription = "Game Banner",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Gradient overlay at bottom
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, BurgundyDarker.copy(alpha = 0.85f))
                            )
                        )
                )

                // Bottom badge in banner
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(CrimsonRed),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = game.name.take(2).uppercase(),
                            color = TextPrimary,
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = game.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = TextPrimary
                        )
                        Text(
                            text = game.developer,
                            fontSize = 12.sp,
                            color = MetallicSilver
                        )
                    }
                }
            }
        }

        item {
            // Play Button & Config Gear
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ObsidiaCrimsonButton(
                    text = if (game.isInstalled) "Play Now" else (if (isIndo) "Jalankan Game" else "Launch Game"),
                    onClick = {
                        val result = viewModel.launchGame(game)
                        if (result.isSuccess) {
                            Toast.makeText(context, "Membuka ${game.name}...", Toast.LENGTH_SHORT).show()
                        } else {
                            try {
                                val marketIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${game.packageName}"))
                                context.startActivity(marketIntent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "${game.name} belum terpasang di perangkat ini.", Toast.LENGTH_LONG).show()
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("launcher_play_button"),
                    icon = Icons.Default.PlayArrow
                )

                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(ObsidianCardElevated)
                        .border(1.dp, ObsidianBorder, RoundedCornerShape(14.dp))
                        .clickable {
                            viewModel.closeLauncher()
                            viewModel.setTab(NavigationTab.GRAPHICS)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Graphics Config",
                        tint = MetallicSilver,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        item {
            // Optimized Settings Card
            ObsidiaCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("launcher_optimized_settings")
            ) {
                Column {
                    Text(
                        text = if (isIndo) "Optimized Settings" else "Optimized Settings",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        SettingPillItem(
                            label = if (isIndo) "Kualitas" else "Quality",
                            value = currentSettings.quality
                        )
                        SettingPillItem(
                            label = "FPS",
                            value = currentSettings.frameRateTarget.replace(" FPS", "")
                        )
                        SettingPillItem(
                            label = if (isIndo) "Resolusi" else "Resolution",
                            value = currentSettings.resolutionScale
                        )
                    }
                }
            }
        }

        item {
            // Quick 4 Status Badges: Graphics, Performance, Touch, Network
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FeatureIndicatorCard(
                    icon = Icons.Default.Tune,
                    label = "Graphics",
                    status = "Optimized",
                    modifier = Modifier.weight(1f)
                )
                FeatureIndicatorCard(
                    icon = Icons.Default.Bolt,
                    label = "Performance",
                    status = "Boosted",
                    modifier = Modifier.weight(1f)
                )
                FeatureIndicatorCard(
                    icon = Icons.Default.TouchApp,
                    label = "Touch",
                    status = "Responsive",
                    modifier = Modifier.weight(1f)
                )
                FeatureIndicatorCard(
                    icon = Icons.Default.Wifi,
                    label = "Network",
                    status = "Low Jitter",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            // Installation & Compatibility Notice
            ObsidiaCard(
                modifier = Modifier.fillMaxWidth(),
                borderColor = ObsidianBorder
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        StatusBadge(
                            text = game.compatibility,
                            color = if (game.compatibility == "Compatible") EmeraldSuccess else Color(0xFFFFB300),
                            bgColor = if (game.compatibility == "Compatible") EmeraldSuccessBg else Color(0xFF332400)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (game.isInstalled) (if (isIndo) "Game Terpasang" else "Installed") else (if (isIndo) "Belum Terpasang" else "Not Installed"),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (game.isInstalled) EmeraldSuccess else MetallicSilver
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (isIndo)
                            "Paket: ${game.packageName}\nOBSIDIA TOOLS menyinkronkan profil alokasi memori dan target pacing sebelum game dimulai."
                        else
                            "Package: ${game.packageName}\nOBSIDIA TOOLS configures memory allocation profile and frame pacing cadence before launch.",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )

                    if (game.inGameGuide.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(ObsidianCardElevated)
                                .border(1.dp, CrimsonRed.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                                .padding(10.dp)
                        ) {
                            Column {
                                Text(
                                    text = if (isIndo) "Panduan Menu In-Game:" else "In-Game Setup Guide:",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CrimsonRed
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = game.inGameGuide,
                                    fontSize = 11.sp,
                                    color = TextPrimary,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SettingPillItem(
    label: String,
    value: String
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(ObsidianCardElevated)
            .border(1.dp, ObsidianBorder, RoundedCornerShape(10.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = label,
                fontSize = 11.sp,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }
    }
}

@Composable
private fun FeatureIndicatorCard(
    icon: ImageVector,
    label: String,
    status: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(ObsidianCardElevated)
            .border(1.dp, ObsidianBorder, RoundedCornerShape(10.dp))
            .padding(vertical = 8.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = CrimsonRed,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 10.sp,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = status,
                fontSize = 9.sp,
                color = EmeraldSuccess
            )
        }
    }
}
